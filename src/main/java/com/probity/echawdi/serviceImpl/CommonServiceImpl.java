package com.probity.echawdi.serviceImpl;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.probity.echawdi.repository.CommonRepository;
import com.probity.echawdi.service.CommonService;

@Component
public class CommonServiceImpl implements CommonService{

	@Inject
	private CommonRepository commonRepository;
	
	@Override
	public ResponseEntity<?> dataImport(String districtCode){
		String msg=commonRepository.insertInForm7("mhrorpun","mhrorpun_bar","loalhost");
		return ResponseEntity.ok(msg);
	}
}