package com.probity.echawdi.controller;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.probity.echawdi.dao.ApiResponse;
import com.probity.echawdi.service.LgdMapperService;


@RestController
@RequestMapping("/api/lgd")
public class LgdMapperController {

	@Autowired
	private LgdMapperService lgdMapperService;
	
		@PutMapping("/updateDyslrFlag") 
		public ResponseEntity<ApiResponse> updateDyslrFlag(HttpServletRequest request) {
	
			ApiResponse response = lgdMapperService.updateDyslrFlag(request);
	
			return ResponseEntity.ok(response);
		}
	}

