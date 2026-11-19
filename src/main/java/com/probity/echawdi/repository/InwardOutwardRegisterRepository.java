package com.probity.echawdi.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.InwardOutwardRegister;

@Repository
public interface InwardOutwardRegisterRepository
        extends JpaRepository<InwardOutwardRegister, Long> {
	
	  List<InwardOutwardRegister> findBySevarthId(String sevarthId);
	  
	  

		List<InwardOutwardRegister>
		findBySevarthIdOrderByCreatedOnDesc(String sevarthId);


}