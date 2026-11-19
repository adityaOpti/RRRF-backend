package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.probity.echawdi.entity.ReceiptDtlLog;

public interface ReceiptDtlLogRepository extends JpaRepository<ReceiptDtlLog, Long> {
	List<ReceiptDtlLog> findByChallanId(Long id);

	List<ReceiptDtlLog> findByKhataNo(String khataNo);

	List<ReceiptDtlLog> findByMakhtaKhataNo(String makhtaKhata);
	
//	 ReceiptDtlLog findByLandRevenueDemandId(Long landRevenueDemandId);
}
