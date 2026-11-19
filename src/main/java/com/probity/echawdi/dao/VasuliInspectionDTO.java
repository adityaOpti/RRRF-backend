
package com.probity.echawdi.dao;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VasuliInspectionDTO {

	  // Common column: ccode
    private String ccode;

    // Bindumala
    private Double binJmDemand;
    private Double binGpDemand;
    private Double binZpDemand;
    private Double binTotalDemand;

    // Akrushik
    private Double akJmDemand;
    private Double akGpDemand;
    private Double akZpDemand;
    private Double akTotalDemand;

    // Sankirn
    private Double sanJmDemand;
    private Double sanGpDemand;
    private Double sanZpDemand;
    private Double sanTotalDemand;

    // Overall Total
    private Double totalDemand;

    
    // Bindumala collected
    private Double binJmCollected;
    private Double binGpCollected;
    private Double binZpCollected;
    private Double binTotalCollected;

    // Akrushik collected
    private Double akJmCollected;
    private Double akGpCollected;
    private Double akZpCollected;
    private Double akTotalCollected;

    // Sankirn collected
    private Double sanJmCollected;
    private Double sanGpCollected;
    private Double sanZpCollected;
    private Double sanTotalCollected;

    // Overall collected
    private Double totalCollected;
    
    
    // EGS
    private Long prevEgsDemand;
    private Long prevEgsCollected;
    private Long currEgsDemand;
    private Long currEgsCollected;
    private Long totalEgsDemand;
    private Long totalEgsCollected;

    // --- Education Cess ---
    private Long prevEduCessDemand;
    private Long prevEduCessCollected;
    private Long currEduCessDemand;
    private Long currEduCessCollected;
    private Long totalEduCessDemand;
    private Long totalEduCessCollected;
    



    
    
}
