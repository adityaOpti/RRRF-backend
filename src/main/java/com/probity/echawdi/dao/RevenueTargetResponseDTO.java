package com.probity.echawdi.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueTargetResponseDTO {

	 	private Double totalJmSankirn;
	    private Double totalGpSankirn;
	    private Double totalZpSankirn;
	    private Double grandTotal;
	    private String annualVillageTarget;	
}
