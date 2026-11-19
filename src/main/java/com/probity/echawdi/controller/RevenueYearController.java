/**

 * This Controller is Describes data persistence and retrieval of echawdi revenueYear
 *  It Includes CRUD OPERATIONS ON echawdi revenueYear
 */
package com.probity.echawdi.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.RevenueYearDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.RevenueYearService;

/**
 * @author Bhagyashri Lolage
 *
 */

@Validated
@RestController
@PreAuthorize("hasRole('ROLE_TALATHI')")
@RequestMapping("/api/revenueYear")
public class RevenueYearController {
	
	@Autowired
	private RevenueYearService revenueYearService;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/getRevenueYearData")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllrevenueYears(HttpServletRequest request,Locale locale){
        return revenueYearService.getRevenueYearData(request,locale);
    }
	
//	@GetMapping("/getRevenueYearDataById")
//	@ResponseStatus(HttpStatus.OK)
//	public RevenueYearDao  getrevenueYearById(@RequestParam long id,Locale locale){
//		return revenueYearService.getRevenueYearById(id, locale);
//	}
//
    @PostMapping("/saveRevenueYear")
    @ResponseStatus(HttpStatus.CREATED)
    public void createrevenueYear(@Valid @RequestBody RevenueYearDao revenueYearDao,Locale locale,HttpServletRequest request){
         	revenueYearService.save(revenueYearDao,locale,request);
    }
//
//    @PutMapping("/editRevenueYear")
//    @ResponseStatus(HttpStatus.OK)
//    public MessageResponse updaterevenueYear(@Valid @RequestBody RevenueYearDao revenueYearDao,Locale locale){
//    try {
//    	revenueYearService.updateRevenueYear(revenueYearDao, locale);
// 		return MessageResponse.builder().status("SUCCESS").message(
//				messageSource.getMessage("error.form1a.successmessage", null, locale)).build();
//	}
//  	catch (Exception ex) {
//         throw new ExchangeException("error.form1a.editrecord");
//    }
//  }
//
    @DeleteMapping("/discardRevenueYear")
    @ResponseStatus(HttpStatus.IM_USED)
    public MessageResponse deleterevenueYear(@RequestParam long id,Locale locale,HttpServletRequest request){
	   try {
			revenueYearService.deleteRevenueYearById(id, locale,request);	
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form1a.deletemessage", null, locale)).build();	
		}
		catch (Exception ex) {
			 throw new ExchangeException("error.form1a.deleterecord");
        }
    }
    
    
}