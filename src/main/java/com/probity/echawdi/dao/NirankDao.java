/**
 * 
 */
package com.probity.echawdi.dao;




import java.util.List;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NirankDao {
	
	    public Long id;

	    @NotNull(message = "{cCode.notempty}")
	    public String cCode;
		
	    @NotNull(message = "{districtCode.notempty}")
	    public String districtCode;
		
	    @NotNull(message = "{talukaCode.notempty}")
	    public String talukaCode;
		
	    @NotNull(message = "{formId.notempty}")
		public String formId;
		
	    
		@NotNull(message = "{revenueYear.notempty}")
		public String revenueYear;

	    @NotNull(message = "{formNames.notempty}")
		public String formNames;
		
		public Character isNirank;
		
		public Character remarks;
		
		public Character isCompleted;
		
		
		@Transient
		public List<String> cCodes;
		
	
    
	public String getcCode() {
		return cCode;
	}
	public void setcCode(String cCode) {
		this.cCode = cCode;
	}
}