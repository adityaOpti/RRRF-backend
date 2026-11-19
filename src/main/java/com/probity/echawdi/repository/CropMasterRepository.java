package com.probity.echawdi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.CropMaster;

@Repository
public interface CropMasterRepository extends JpaRepository<CropMaster, Long> {
    CropMaster findByCropName(String cropName); // Optional if you want to look up by name
}
