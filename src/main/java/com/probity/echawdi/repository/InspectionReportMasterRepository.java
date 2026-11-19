package com.probity.echawdi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.probity.echawdi.entity.InspectionReportMaster;

public interface InspectionReportMasterRepository extends JpaRepository<InspectionReportMaster,Long> {
	boolean existsByCcode(String ccode);

	//InspectionReportMaster  findByCcode(String ccode);
	
    Optional<InspectionReportMaster> findByCcode(String ccode);
}
