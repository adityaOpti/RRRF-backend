package com.probity.echawdi.dao;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null values from the response
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FerarInspectionResponse {
    private Long id;
    private String remarkType;
    private String documentPath;
    private String remark;
    
    
    private Integer  mutNo;
	
    private Long typeOfRemark;
    
    private Long ferfarType;
}
