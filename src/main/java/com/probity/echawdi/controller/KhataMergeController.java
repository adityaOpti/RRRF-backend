package com.probity.echawdi.controller;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.service.KhataMergeService;

@RestController
@RequestMapping("/api/khatamerge")
public class KhataMergeController {

	@Autowired
	private KhataMergeService khataMergeService;

	@GetMapping("/getKhataMergeDetails")
	@ResponseStatus(HttpStatus.OK)
	public KhataMergeDao getKhataMerge(		
			@RequestParam String cCode,@RequestParam String revenueYear,Locale locale,HttpServletRequest request){
		return  khataMergeService.getKhataMergeDtl(cCode,revenueYear,locale,request);
	}

	@PostMapping("/saveMergeDtl")
	@ResponseStatus(HttpStatus.OK)
	public KhataMergeDao saveKhataMerge(@RequestBody KhataMergeDao KhataMergeDao,Locale locale,HttpServletRequest request){
		return  khataMergeService.saveKhataMerge(KhataMergeDao,locale,request);
	}

	@DeleteMapping("/discardMergeDtl")
	@ResponseStatus(HttpStatus.IM_USED)
	public KhataMergeDao deleteMergeDtl(@RequestParam long parentId,Locale locale,HttpServletRequest request){
		try {
			return khataMergeService.deleteMergeDtlByParentId(parentId, locale,request);
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.deleterecord");
		}
	}

	@GetMapping("/getReportKhataMergeDetails")
	@ResponseStatus(HttpStatus.OK)
	public KhataMergeDao getKhataMergeReport(		
			@RequestParam String cCode,Locale locale,HttpServletRequest request){
		return  khataMergeService.getKhataMergeDtlReport(cCode,locale,request);
	}
}