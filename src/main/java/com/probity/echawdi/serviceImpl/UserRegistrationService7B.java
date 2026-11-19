package com.probity.echawdi.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.probity.echawdi.entity.UserRegistration7B;
import com.probity.echawdi.repository.UserRegistrationRepository;
import java.util.Locale;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.probity.echawdi.dao.SignupRequest;
import com.probity.echawdi.entity.UserRegistration7B;
import com.probity.echawdi.exception.DuplicateRecordException;

@Service
public class UserRegistrationService7B {

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public UserRegistration7B authenticate(
            String username,
            String requestPassword) {

        UserRegistration7B user = userRegistrationRepository
                .findByMobileNumber(username)
                .orElseThrow(() ->
                        new BadCredentialsException(
                                "Invalid username or password"));

        if (!passwordEncoder.matches(
                requestPassword,
                user.getPassword())) {

            throw new BadCredentialsException(
                    "Invalid username or password");
        }

        return user;
    }
    
    @Transactional
    public String signup(SignupRequest request) {

        // 1. Normalize email
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        String email = request.getEmail().trim().toLowerCase(Locale.ROOT);

        // 2. Check duplicate email
        if (userRegistrationRepository.existsByEmailIgnoreCase(email)) {
            throw new DuplicateRecordException(
                    "Email is already registered");
        }

        // 3. Check duplicate mobile
        String mobile = request.getMobileNumber();

        if (mobile != null && !mobile.trim().isEmpty()
                && userRegistrationRepository.existsByMobileNumber(mobile)) {
            throw new DuplicateRecordException(
                    "Mobile number is already registered");
        }

        // 4. Check duplicate Aadhaar
        String aadhar = request.getAadhar();

        if (aadhar != null && !aadhar.trim().isEmpty()
                && userRegistrationRepository.existsByAadhar(aadhar)) {
            throw new DuplicateRecordException(
                    "Aadhaar is already registered");
        }

        // 5. Validate owner type and determine registration name
        String ownerType = request.getOwnerType();

        if (ownerType == null || ownerType.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner type is required");
        }

        ownerType = ownerType.trim().toUpperCase(Locale.ROOT);

        String finalName;

        if ("SELF".equals(ownerType)) {
            if (request.getName() == null || request.getName().trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Name is required for SELF registration");
            }

            finalName = request.getName().trim();

        } else if ("ORGANIZATION".equals(ownerType)) {
            if (request.getOrganizationName() == null
                    || request.getOrganizationName().trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Organization name is required for ORGANIZATION registration");
            }

            finalName = request.getOrganizationName().trim();

        } else {
            throw new IllegalArgumentException(
                    "Invalid ownerType. Allowed values: SELF, ORGANIZATION");
        }

        // 6. Generate the 7B user ID
        Long sequenceValue = userRegistrationRepository.nextUserIdSequence();

        if (sequenceValue == null || sequenceValue < 0
                || sequenceValue > 9_999_999_999_999L) {
            throw new IllegalStateException("Unable to generate user ID");
        }

        String userId = "7B" + String.format("%013d", sequenceValue);

        // 7. Create registration record
        UserRegistration7B user = new UserRegistration7B();

        user.setUserId(userId);
        user.setName(finalName);
        user.setOwnerType(ownerType);
        user.setCcode(request.getCCode());
        user.setEmail(email);

        // Never save the plain-text password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setMobileNumber(mobile);
        user.setAadhar(aadhar);
        user.setCurrentAddress(request.getCurrentAddress());
        user.setAddressOfLand(request.getAddressOfLand());
        user.setGender(request.getGender());
        user.setDob(request.getDob());

        user.setDistrictCode(request.getDistrictCode());
        user.setDistrictNameMarathi(request.getDistrictNameMarathi());
        user.setTalukaCode(request.getTalukaCode());
        user.setTalukaNameMarathi(request.getTalukaNameMarathi());

        // 8. Save the registration
        userRegistrationRepository.saveAndFlush(user);

        return userId;
    }
}

