package com.probity.echawdi.dao;

import lombok.Data;

@Data
public class VillageCropAreaDTO {
	
	
	    private Long id;
	    private Double version;
	    private Long createdUserId;
	    private Double irrigationArea;
	    private Double koradwahuArea;
	    private Double otherArea;
	    private Double totalCropArea;
	    private Character activeFlag;
	    
	    private String ccode; // 👈 Add this if it makes sense
	    
	    private String cropName;   // ✅ used for lookup or creation
	    
	    private Long cropId; 

	   
	    
	    private Long adarshaId;
	


}
