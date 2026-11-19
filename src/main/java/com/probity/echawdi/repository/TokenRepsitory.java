package com.probity.echawdi.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.probity.echawdi.entity.Token;

public interface TokenRepsitory extends JpaRepository<Token, Long>{

	
	
	
		//public Optional<Token> findByToken(String token);
	
		Optional<Token> findByAccessToken(String accessToken);
		
		
		//eisehi check krneke liye concurent login ke liye kiya tha bhaii tushar ne.
		//baki iska koii kam nhi hai agr use nhi kiya hai toh, nhi to error dega pkka.
		//Optional<Token>	findByUsernameAndLoggedInFalseAndRevokedFalse(String username);
		
		public void deleteByServarthId(String token);
		
		public boolean existsByServarthId(String ServarthId);
		
		//public boolean existsByToken(String token);
		
		public boolean existsByAccessToken(String token);
		
		
		public Optional<Token> findByRefreshToken(String refreshToken);
	
	
	

	/*Optional<Token> findByToken(String token);

    boolean existsByToken(String token);

    
    void deleteByToken(String token);

   
    //void deleteByServarthId( String servarthId);
    
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM token WHERE servarth_id = :servarthId", nativeQuery = true)
    void deleteByServarthId(@Param("servarthId") String servarthId);

    boolean existsByServarthId(String servarthId);*/
	
	
	
	
	    
	 

}