package com.probity.echawdi.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.config.WithDatabase;
import com.probity.echawdi.dao.NirankDao;
import com.probity.echawdi.entity.Nirank;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.NirankRepository;
import com.probity.echawdi.repository.VillageFormMasterRepository;
import com.probity.echawdi.service.NirankService;
import com.probity.echawdi.utils.DataSourceType;


@RestController
@PreAuthorize("hasRole('ROLE_TALATHI')")
@RequestMapping("/api/restservice")
public class NirankController {

	@Autowired
	private NirankService nirankService;
	@Autowired
	private VillageFormMasterRepository villageFormMasterRepository;
	@Autowired
	private NirankRepository nirankRepository;
	@Autowired
	private MessageSource messageSource;

	@WithDatabase(DataSourceType.echmst)
    @PostMapping("/persistNirankandCompleted")
    public MessageResponse persistNirank(@RequestBody NirankDao nirankDao,HttpServletRequest request,Locale locale) {
    	return nirankService.save(nirankDao,request,locale);
    }

	@PreAuthorize("hasAnyAuthority('ROLE_TALATHI','ROLE_TAH')")
	@WithDatabase(DataSourceType.echmst)
	@GetMapping("/getAllVillageForm")
	public Response getAllVillageForm() {
		Response response =new Response();
		response.setVillageFormMaster(villageFormMasterRepository.findAll());
		return response;
	}

	
	@WithDatabase(DataSourceType.echmst)
	@PostMapping("/persistNirankandCompletedAll")
	public MessageResponse persistNiranks(@RequestBody Response requestBody,HttpServletRequest request,Locale locale) {
		return nirankService.saveAll(requestBody,request,locale);
	}
	
	@WithDatabase(DataSourceType.echmst)
    @GetMapping("/getNirankandCompleted")
    public Response getNirankAndCompleted(@RequestParam String cCode,@RequestParam String revenueYear,HttpServletRequest request,Locale locale) {
    	return nirankService.getNirankAndCompleted(cCode,revenueYear,request,locale);
    }
	
	
	@WithDatabase(DataSourceType.echmst)
    @GetMapping("/getNirankforReport")
    public Response getNirankForDashBoard(@RequestParam String districtCode,@RequestParam String talukaCode,HttpServletRequest request,Locale locale) {
    	return nirankService.getNirnkForDashBoard(districtCode,talukaCode,request,locale);
    }
    
	@WithDatabase(DataSourceType.echmst)
	@PutMapping("/editNirankandCompleted")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse editNirankandCompleted(@Valid @RequestBody NirankDao nirankDao,Locale locale,HttpServletRequest request){
    		try {
    			nirankService.editNirankandCompleted(nirankDao,locale,request);
    			return MessageResponse.builder().status("SUCCESS").message(
        				messageSource.getMessage("error.form.successmessage", null, locale)).build();
        	}
          	catch (Exception ex) {
    	         throw new ExchangeException("error.form.editrecord");
    	}
    }
	
	 @DeleteMapping("/discardNirankandCompleted")
	    @ResponseStatus(HttpStatus.IM_USED)
	    public MessageResponse deleteNirankAndCompleted(HttpServletRequest request,@RequestParam long id,Locale locale){
	    	   try {
	    		   nirankService.deleteNirankAndCompleted(request,id, locale);
	    		   return MessageResponse.builder().status("SUCCESS").message(
	       				messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
	   		}
	       	catch (Exception ex) {
	    	         throw new ExchangeException("error.form.deleterecord");
	    	}
	    }
	
	 @GetMapping("/getNirankForFourForm")
		public List<Nirank> getNirankcount(@RequestParam String cCode){
		 
			List<Object[]> result=new ArrayList<>();
			  result=nirankRepository.findingNirank(cCode);
			 
			  return result.stream().map(obj->{
					
					Nirank nrd=new Nirank();
					nrd.setcCode(obj[0].toString());
					nrd.setLgdCode(obj[1].toString());
					nrd.setFormId((obj[2].toString()));
					nrd.setIsNirank(Character.valueOf(obj[3].toString().charAt(0)));
					nrd.setIsCompleted(Character.valueOf(obj[4].toString().charAt(0)));
					nrd.setActiveFlag(Character.valueOf(obj[5].toString().charAt(0)));
					nrd.setDistrictCode(obj[6].toString());
					nrd.setTalukaCode(obj[7].toString());
					nrd.setVillage_code(obj[8].toString());
					nrd.setVillage_name(obj[9].toString());
					return nrd ;
				}).collect(Collectors.toList());	}
	 
	 
	 @GetMapping("/getNirankForKhataMerge")
		public List<Nirank> getNirankForKhataMerge(@RequestParam String cCode){
		 
			List<Object[]> result=new ArrayList<>();
			  result=nirankRepository.findingNirankForKhataMerge(cCode);
			 
			  return result.stream().map(obj->{
					
					Nirank nrd=new Nirank();
					nrd.setcCode(obj[0].toString());
					nrd.setLgdCode(obj[1].toString());
					nrd.setFormId((obj[2].toString()));
					nrd.setIsNirank(Character.valueOf(obj[3].toString().charAt(0)));
					nrd.setIsCompleted(Character.valueOf(obj[4].toString().charAt(0)));
					nrd.setActiveFlag(Character.valueOf(obj[5].toString().charAt(0)));
					nrd.setDistrictCode(obj[6].toString());
					nrd.setTalukaCode(obj[7].toString());
					nrd.setVillage_code(obj[8].toString());
					nrd.setVillage_name(obj[9].toString());
					return nrd ;
				}).collect(Collectors.toList());	
			 }
	 
	 
	 
//		Nirank old=nirankRepository.findBycCode(nirank.getcCode());
//    	if(old!=null) {
//    		old.setFormId(nirank.getFormId());
//    	}else {
//    		old=new Nirank();
//    		BeanUtils.copyProperties(nirank, old);
//    	}
//		if(old.getFormId().isEmpty()) {
//    		nirankRepository.delete(old);
//    	}else {
//    			nirankRepository.save(old);
//    	}
//    	return nirankRepository.findBycCodeIn(nirank.getcCodes());
//    }
}