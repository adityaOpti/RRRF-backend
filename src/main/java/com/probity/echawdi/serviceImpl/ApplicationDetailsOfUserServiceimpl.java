package com.probity.echawdi.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.EpeekResultDao;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.ResponseForSevenB;
import com.probity.echawdi.repository.ApplicationDetailsOfUserRepository;
import com.probity.echawdi.service.ApplicationDetailOfUserService;
import com.probity.echawdi.utils.CommonConstants;

@Service
public class ApplicationDetailsOfUserServiceimpl implements ApplicationDetailOfUserService {

	@Autowired
	private ApplicationDetailsOfUserRepository applicationRepository;

	@Override
	public ResponseForSevenB getEpeekPahaniResult(String mhrDbName, String mhrSchemaName, String mhrhost, String cCode,
			int yearCulti, int numericpin, int seasonCode) {
		
		System.out.println("------------------------dbname" + mhrDbName);
		System.out.println("------------------------schema" + mhrSchemaName);
		System.out.println("------------------------dbname" + mhrhost);
		
		List<Object[]> pahaniResult = applicationRepository.getEpeekPahaniResult(mhrhost, mhrDbName, mhrSchemaName,
				CommonConstants.mhrorUser, CommonConstants.mhrorPassword, cCode, numericpin, seasonCode, yearCulti);
		
		DatabaseContextHolder.restoreCtx();
		
		if (pahaniResult.isEmpty()) {
			throw new ResourceNotFoundException("error.form.epeekResult");
		} else {

			ResponseForSevenB epeekPahaniResult = new ResponseForSevenB();
			epeekPahaniResult.setEpeekResultData(pahaniResult.stream().map((object) -> {
				
				EpeekResultDao epeek = new EpeekResultDao();
				epeek.setcCode(object[0].toString());
				epeek.setKhataNo(Integer.valueOf(object[1].toString()));
				epeek.setEpeekPahaniArea(Float.valueOf(object[2].toString()));
				epeek.setSeasonCode(Integer.valueOf(object[3].toString()));
				epeek.setHissaNo(object[4].toString());
				return epeek;
				
			}).collect(Collectors.toList()));
			
			return epeekPahaniResult;

		}
	}

}