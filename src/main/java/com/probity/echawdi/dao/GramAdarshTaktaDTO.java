package com.probity.echawdi.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class GramAdarshTaktaDTO {

	private Double areaBelow2023 ;
	 
    private Double   areaAbove2023 ;
    private Long KhataCountBelow2023;
    private Long KhataCountAbove2023;
	 
    private Long khataCountWithName;
    
    private Double potKharabArea;
	 
    private Double akrushakArea;
    
    private Double vanJaminArea;
    
    private Double villageSite;
    
    
    private Double bidumalaNiyatCurrent;
    private Double akrushakCurrent;
    private Double sankirnCurrent;
    private Double educationalCessCurrent;
    private Double employeeGuaranteeSchemeCurrent;
    private Double totalAmountCurrent;

    private Double bidumalaNiyatPrevious;
    private Double akrushakPrevious;
    private Double sankirnPrevious;
    private Double educationalCessPrevious;
    private Double employeeGuaranteeSchemePrevious;
    private Double totalAmountPrevious;
    
    
    private Double bindumalaArea;
    private Double dumalaArea;
    private Double totalArea;

    
    //JalSinchan 
    private Integer inUsedWell;
    private Integer notInUsedWell;
			
    //getMahasulMafOrKamakariJaminiData
    private Double kamAkariArea;
    

    private Integer kuran;
    private Double sumOfBgayatArea;    public boolean isAllFieldsZero() {
        return (areaBelow2023 == null || areaBelow2023 == 0.0) &&
               (areaAbove2023 == null || areaAbove2023 == 0.0) &&
               (KhataCountBelow2023 == null || KhataCountBelow2023 == 0) &&
               (KhataCountAbove2023 == null || KhataCountAbove2023 == 0) &&
               (khataCountWithName == null || khataCountWithName == 0) &&
               (potKharabArea == null || potKharabArea == 0.0) &&
               (akrushakArea == null || akrushakArea == 0.0) &&
               (vanJaminArea == null || vanJaminArea == 0.0) &&
               (villageSite == null || villageSite == 0.0) &&
               (bidumalaNiyatCurrent == null || bidumalaNiyatCurrent == 0.0) &&
               (akrushakCurrent == null || akrushakCurrent == 0.0) &&
               (sankirnCurrent == null || sankirnCurrent == 0.0) &&
               (educationalCessCurrent == null || educationalCessCurrent == 0.0) &&
               (educationalCessCurrent == null || educationalCessCurrent == 0.0) &&
               (totalAmountCurrent == null || totalAmountCurrent == 0.0) &&
               (bidumalaNiyatPrevious == null || bidumalaNiyatPrevious == 0.0) &&
               (akrushakPrevious == null || akrushakPrevious == 0.0) &&
               (sankirnPrevious == null || sankirnPrevious == 0.0) &&
               (educationalCessPrevious == null || educationalCessPrevious == 0.0) &&
               (employeeGuaranteeSchemePrevious == null || employeeGuaranteeSchemePrevious == 0.0) &&
               (totalAmountPrevious == null || totalAmountPrevious == 0.0) &&
               
               (bindumalaArea == null || bindumalaArea == 0.0) &&
               (dumalaArea == null || dumalaArea == 0.0) &&
               (totalArea == null || totalArea == 0.0)  &&
               (inUsedWell == null || inUsedWell == 0) &&
               (notInUsedWell == null || notInUsedWell == 0) &&
               
               (kamAkariArea == null || kamAkariArea == 0.0) 
               
               
               ;
               
    }

  
	
}
