package com.probity.echawdi.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.Form7ADao;
import com.probity.echawdi.dao.Form7Dao;
import com.probity.echawdi.payload.ResponseForSevenB;
import com.probity.echawdi.service.ApplicationDetailOfUserService;
import com.probity.echawdi.service.SurveyHissaAndKhataNo;



@RestController
@RequestMapping("/new/application")
public class ApplicationDetailsOfUserController {

	@Autowired
	private ApplicationDetailOfUserService applicationDetailOfUserService;

	@Autowired
	private SurveyHissaAndKhataNo hissaAndKhataNo;
	
	
	//for saving the document and Application Id
//	 @Autowired
//	 private ApplicantDetailsService applicantDetailsService;

	 
	 
	 
	 
	@GetMapping("/getHissaNo")
	@ResponseStatus(HttpStatus.OK)
	public List<Form7Dao> generateHissaNo(@RequestParam String mhrDbName, @RequestParam String mhrSchemaName,
			@RequestParam String mhrhost, @RequestParam String cCode, @RequestParam String pin,
			@RequestParam Character marked) {
		return hissaAndKhataNo.getHissaNo(mhrDbName, mhrSchemaName, mhrhost, cCode, pin, marked);
	}

	@GetMapping("/getPotHissa") // form7
	@ResponseStatus(HttpStatus.OK)
	public List<Form7ADao> getForm7AEferfarDetials(@RequestParam String mhrDbName, @RequestParam String mhrSchemaName,
			@RequestParam String mhrhost, @RequestParam String cCode, @RequestParam String pin,
			@RequestParam String hissaNo, HttpServletRequest request, Locale locale) {
		return hissaAndKhataNo.getPotHissa(mhrDbName, mhrSchemaName, mhrhost, cCode, pin, hissaNo);
	}

	@GetMapping("/getKhataNo") // form7
	@ResponseStatus(HttpStatus.OK)
	public List<Form7ADao> getForm7AEferfarKhataDetials(@RequestParam String mhrDbName,
			@RequestParam String mhrSchemaName, @RequestParam String mhrhost, @RequestParam String cCode,
			@RequestParam String pin, @RequestParam String hissaNo, @RequestParam String khataNo,
			HttpServletRequest request, Locale locale) {
		return hissaAndKhataNo.getKhataNo(mhrDbName, mhrSchemaName, mhrhost, cCode, pin, hissaNo, khataNo);
	}

	@GetMapping("/getEpeekData")
	@ResponseStatus(HttpStatus.OK)
	public ResponseForSevenB getEpeekData(@RequestParam String mhrDbName, @RequestParam String mhrSchemaName,
			@RequestParam String mhrhost, @RequestParam String cCode, @RequestParam int yearCulti,
			@RequestParam int numericpin, @RequestParam int seasonCode) {
		return applicationDetailOfUserService.getEpeekPahaniResult(mhrDbName, mhrSchemaName, mhrhost, cCode, yearCulti,
				numericpin, seasonCode);
	}
	
	
/*	@PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String saveApplicantDetails(
	    @RequestParam("applicantDetails") String applicantDetailsJson,  // JSON as String
	    @RequestParam("document") MultipartFile document,
	    HttpServletRequest request) {

	    try {
	    	
	    	 System.out.println("I am Here above --------");
		        
	        // Convert JSON string to Java object
	        ObjectMapper objectMapper = new ObjectMapper();
	        ApplicantDetailsRequest applicantDetails = objectMapper.readValue(applicantDetailsJson, ApplicantDetailsRequest.class);
	        
	        
	        // Convert String to LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate kabjaDate = LocalDate.parse(applicantDetails.getDateOfKabja(), formatter);
	        
	        System.out.println("Parsed dateOfKabja: " + applicantDetails.getDateOfKabja());


	        System.out.println("I am Here middel --------");
	        
	        // Save the applicant details
	      
			String applicatioId = applicantDetailsService.saveApplicantDetails(
	            applicantDetails.getSurveyNo(),
	            applicantDetails.getCcode(),
	            applicantDetails.getDistrictCode(),
	            applicantDetails.getTalukaCode(),
	            applicantDetails.getSubSurveyNo(),
	            applicantDetails.getKhataNo(),
	            applicantDetails.getRevenueYear(),
	            applicantDetails.getApplicantName(),
	            //applicantDetails.getDateOfKabja(),
	            kabjaDate,
	            applicantDetails.getSeasonCode(),
	            applicantDetails.getLandOwnerInfos(),
	            request,
	            document
	            
		        
	        );
	        System.out.println("I am Here below --------");

	        return applicatioId;
	    } catch (IOException e) {
	        return "Error processing JSON: " + e.getMessage();
	    } catch (Exception e) {
	        return "Error: " + e.getMessage();
	    }
	}
*/
	
	


	
}
