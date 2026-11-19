package com.probity.echawdi.repository;


import java.util.List;

public interface DivisionDistrictTalukaCountRepositoryCustom {

	/*  getDivisionCountUpdatedMISPre */
    List<Object[]> getTotalByDivisionCodeUpdatedMISPre(
            String divisionCode,
            String yearName);
    
    
    List<Object[]> getTotalByAllDivisionCodeUpdatedMISPre(
            String yearName);
    
    /* --------- getDivisionCountUpdatedMISPre End  */
 
    
    List<Object[]> getByDivisionCodeUpdatedMisPre(
            String divisionCode,
            String yearName); 
    
    List<Object[]> getByAllDivisionCodeUpdatedMIsPre(String yearName);
    
    List<Object[]> getByTalukaNameAndCodePre(
            String divisionCode,
            String districtCode,
            String yearName);
    
    
   
    
    
    
}