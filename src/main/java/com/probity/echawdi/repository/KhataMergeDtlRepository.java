package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.KhataMergeDtl;

@Repository
public interface KhataMergeDtlRepository extends JpaRepository<KhataMergeDtl,Long> {

	List<KhataMergeDtl> findByParentId(Long parentId);

	@Query("select k from KhataMergeDtl k where "
			+ "trim(cCode)=?1 "
			+ "and activeFlag=?2 "
			+ "and revenueYear=?3 "
			+ "and khataNo not in(select khataNo from LandRevenueDemand l where trim(l.cCode)=?1 and l.revenueYear=?3 and l.activeFlag in('Y','M'))")
	List<KhataMergeDtl> getMergeDtlDetails(String cCode,Character activeFlag,String revenueYear);
}