package com.probity.echawdi.controller;

/**
 * @author Ashish Dahale
 *
 */

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.Form7Dao;
import com.probity.echawdi.dao.Form7KhataDao;
import com.probity.echawdi.dao.VillageDao;
import com.probity.echawdi.entity.MVillageMapper;
import com.probity.echawdi.entity.Nirank;
import com.probity.echawdi.entity.VillageFormMaster;
import com.probity.echawdi.repository.LgdMapperRepository;
import com.probity.echawdi.repository.MVillageMapperRepository;
import com.probity.echawdi.repository.NirankRepository;
import com.probity.echawdi.repository.VillageFormMasterRepository;
import com.probity.echawdi.service.Form7Service;
import com.probity.echawdi.service.VillageService;

/**
 * @author Ashish Dahale
 *
 */

@RestController
@RequestMapping("/api/restservice")
public class AppController {

	@Autowired
	private VillageService villageService;
	@Autowired
	private VillageFormMasterRepository villageFormMasterRepository;
	@Autowired
	private NirankRepository nirankRepository;
	@Autowired
	private LgdMapperRepository lgdMapperRepository;
	@Autowired
	private MVillageMapperRepository mVillageMapperRepository;
	
	
	
	@Autowired
	private Form7Service form7Service;

	@GetMapping("/getVillageListByUser")
	@ResponseStatus(HttpStatus.OK)
	public List<VillageDao> getVillageListByUser(@RequestParam String username,HttpServletRequest request,Locale locale) {
		return villageService.getVillageListByUserName(username,request,locale);
	}
    
    @GetMapping("/getHissaByVillageAndSurveyNo")
    @ResponseStatus(HttpStatus.OK)
    public List<Form7Dao> generateHissaNo(HttpServletRequest request,@RequestParam String cCode,@RequestParam String pin,@RequestParam Character marked,Locale locale) {
    			return form7Service.findHissaNoByVillageAndSurveyNo(request,cCode, pin,marked,locale);
    }
    
    @GetMapping("/getForm1DetailsBySurveyAndHissa")
    @ResponseStatus(HttpStatus.OK)
    public List<Form7Dao> getForm1DetailsBySurveyAndHissa(HttpServletRequest request,@RequestParam String cCode,@RequestParam String pin,@RequestParam String hissaNo,Locale locale){
					return form7Service.getForm1Details(request,cCode,pin,hissaNo, locale);
    }
    
    @GetMapping("/getKhataOwnerName")
    @ResponseStatus(HttpStatus.OK)
    public Form7KhataDao getKhataOwnerName(@RequestParam String khataNo,@RequestParam String cCode,HttpServletRequest request,Locale locale){
  	  return form7Service.getKhataOwnerName(request,khataNo,cCode,locale);
    }
    
    @GetMapping("/getVillageFormData")
    public List<VillageFormMaster> getNirankFormDetails(String cCode) {
    	return villageFormMasterRepository.findAll();
    }

    @PostMapping("/persistNirank")
    public List<Nirank> persistNirank(@RequestBody Nirank nirank,HttpServletRequest request) {
    	
		Nirank old=nirankRepository.findBycCode(nirank.getcCode());
    	if(old!=null) {
    		old.setFormId(nirank.getFormId());
    	}else {
    		old=new Nirank();
    		BeanUtils.copyProperties(nirank, old);
    	}
		if(old.getFormId().isEmpty()) {
    		nirankRepository.delete(old);
    	}else {
    			nirankRepository.save(old);
    	}
    	return nirankRepository.findBycCodeIn(nirank.getcCodes());
    }
}