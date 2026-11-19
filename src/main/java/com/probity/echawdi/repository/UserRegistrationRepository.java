
package com.probity.echawdi.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.probity.echawdi.entity.UserRegistration7B;

public interface UserRegistrationRepository
        extends JpaRepository<UserRegistration7B, Long> {

//    Optional<UserRegistration7B> findByUsername(String username);
	Optional<UserRegistration7B> findByMobileNumber(String mobileNumber);
//	Object findByMobileNumber(String username);
	

boolean existsByEmailIgnoreCase(String email);

boolean existsByMobileNumber(String mobileNumber);

boolean existsByAadhar(String aadhar);

@Query(
    value = "SELECT nextval('public.user_registration_id_seq')",
    nativeQuery = true
)
Long nextUserIdSequence();
}
