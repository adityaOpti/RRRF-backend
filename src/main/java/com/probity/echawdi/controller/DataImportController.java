package com.probity.echawdi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.service.CommonService;

@RestController
@RequestMapping("/auth")
public class DataImportController {

	@Autowired
	private CommonService commonService;
	
	@GetMapping("/dataImport/{districtCode}")
	public ResponseEntity<?> dataImport(@PathVariable("districtCode") String districtCode){
		return commonService.dataImport(districtCode);
	}
}