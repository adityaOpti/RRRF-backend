package com.probity.echawdi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.probity.echawdi.entity.RemarkType;

public interface RemarkTypeRepository extends JpaRepository<RemarkType, Long> {
    RemarkType findByRemarkTypeName(String remarkTypeName);
}
