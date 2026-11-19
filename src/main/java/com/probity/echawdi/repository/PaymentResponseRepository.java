package com.probity.echawdi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.PaymentResponse;

@Repository
public interface PaymentResponseRepository  extends JpaRepository<PaymentResponse, String>{
	
}