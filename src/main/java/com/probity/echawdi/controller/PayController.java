package com.probity.echawdi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.probity.echawdi.entity.PaymentResponse;
import com.probity.echawdi.repository.PaymentResponseRepository;

@Controller
@RequestMapping("/auth/payment")
public class PayController {
	
	@Autowired
	private PaymentResponseRepository paymentResponseRepository;
	
	@RequestMapping(path="/grassForm" , method = RequestMethod.GET)
	public String grassForm() {
		return "paymentGateway";
	}
	
	@RequestMapping(path="/grassResponse" , method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String grassResponse(@RequestBody MultiValueMap<String, String> formData) {
	   
		Map<String, List<String>> parameterMap = formData;
	    
//	    String dateTime =  parameterMap.get("TRANSCOMPLETIONDATETIME").toString();
//	    char[] digits1 = dateTime.toCharArray();
//	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//	    String[] dateT1=parameterMap.get("TRANSCOMPLETIONDATETIME").toString().split("[");
//	    //System.out.println(dateT1);
//	    //System.out.println(dateT1[0]);
//	    //System.out.println(dateT1[1]);
//	    String[] dateT2=dateT1[1].split("]");
//	    //System.out.println(dateT2);
//	    //System.out.println(dateT2[0]);
//	    //System.out.println(dateT2[1]);
//	    LocalDateTime dateTime = LocalDateTime.parse(dateT2[0], formatter);
	    
		PaymentResponse paymentResponse =new PaymentResponse();
	    paymentResponse.setGRN(parameterMap.get("GRN").toString());
		paymentResponse.setPARTYNAME(parameterMap.get("PARTYNAME").toString());
		paymentResponse.setTAXID(parameterMap.get("TAXID").toString());
		paymentResponse.setDEPARTMENT_ID(parameterMap.get("DEPARTMENT_ID").toString());
		paymentResponse.setSTATUS(parameterMap.get("STATUS").toString());
		paymentResponse.setBANKNAME(parameterMap.get("BANKNAME").toString());
		paymentResponse.setBANKCODE(parameterMap.get("BANKCODE").toString());
		paymentResponse.setPRN(parameterMap.get("PRN").toString());
		paymentResponse.setBANKCIN(parameterMap.get("BANKCIN").toString());
//		paymentResponse.setAMOUNT((BigDecimal) parameterMap.get("AMOUNT"));
//		paymentResponse.setENTRY_DATE(LocalDate.parse(parameterMap.get("ENTRY_DATE").toString()));
//		paymentResponse.setTRANSCOMPLETIONDATETIME(dateTime);
		paymentResponseRepository.save(paymentResponse);
		
		return "paymentResponse";
	}
}