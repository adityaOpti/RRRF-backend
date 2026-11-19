package com.probity.echawdi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.FerfarData;

@Repository
public interface FerfarDataRepository extends JpaRepository<FerfarData, Long> {
}
