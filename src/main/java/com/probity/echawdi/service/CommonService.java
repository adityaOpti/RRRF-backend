package com.probity.echawdi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CommonService {

	ResponseEntity<?> dataImport(String districtCode);
	
}
