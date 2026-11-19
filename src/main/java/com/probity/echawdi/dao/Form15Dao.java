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
 * @author Bhagyashri Lolage
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Form15Dao {
	
private Long id;
	
	public Integer districtCode;
	
	
	public Integer talukaCode;
	
	
	private String cCode; 
	
	@NotNull(message="{communicationReceivedFrom.notempty}")
	private String communicationReceivedFrom;
	
	@NotNull(message="{noOfCommunication.notempty}")
	private String noOfCommunication;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfReceipt;
	
	@NotNull(message="{subjectActionToTake.notempty}")
	private String subjectActionToTake;
	
	
	private String communicationDispatchedToWhom;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate  dateOfDispatch;
	
	@NotNull(message="{noInList.notempty}")
	private String  noInList;
	
	@NotNull(message="{designation.notempty}")
	private String designation;
	
	@NotNull(message="{sajjaCode.notempty}")
	private String sajjaCode;
	
	private String  remarks;
	
	@NotNull
	private String userId;
}
