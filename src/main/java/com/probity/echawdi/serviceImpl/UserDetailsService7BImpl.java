
package com.probity.echawdi.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.probity.echawdi.entity.UserRegistration7B;
import com.probity.echawdi.repository.UserRegistrationRepository;

@Service
public class UserDetailsService7BImpl implements UserDetailsService {

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserRegistration7B user = userRegistrationRepository
                .findByMobileNumber(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "7B user not found"
                        ));

        return UserDetails7BImpl.build(user);
    }
}

