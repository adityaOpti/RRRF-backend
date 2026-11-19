/**
 * 
 */
package com.probity.echawdi.dao;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rushikesh Durgude
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Form18Dao {
	
	public Long id;
	@NotNull(message = "{districtCode.notempty}")
	public Integer districtCode;
	
	@NotNull(message = "{talukaCode.notempty}")
	public Integer talukaCode;
    
    @NotNull(message = "{cCode.notempty}")
    public String cCode;
   
    @NotNull(message = "{communicationReceivedFrom.notempty}")
	public String communicationReceivedFrom;
	
    @NotNull(message = "{noOfCommunication.notempty}")
    public String noOfCommunication;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate dateOfReceipt;
	
	 @NotNull(message = "{subjectActionTaken.notempty}")
	public String  subjectActionTaken;
	
	@NotNull(message = "{communicationDispatchedToWhom.notempty}")
	public String communicationDispatchedToWhom;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public String  dateOfDispatch;
	
	@NotNull(message = "{noInList.notempty}")
	public String  noInList;
    
	@NotNull(message = "{remarks.notempty}")
    public String  remarks;
    
    
    public String getcCode() {
		return cCode;
	}
	  public void setcCode(String cCode) {
		this.cCode = cCode;
	}

}
