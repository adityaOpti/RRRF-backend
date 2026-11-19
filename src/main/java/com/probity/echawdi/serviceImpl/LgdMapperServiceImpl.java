package com.probity.echawdi.serviceImpl;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.probity.echawdi.config.WithDatabase;
import com.probity.echawdi.dao.ApiResponse;
import com.probity.echawdi.repository.LgdMapperMisRepository;
import com.probity.echawdi.service.LgdMapperService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceType;



@Service
public class LgdMapperServiceImpl implements LgdMapperService {

	@Autowired
	private LgdMapperMisRepository repository;
	
	
	

	@Override
	@Transactional
	@WithDatabase(DataSourceType.echmst)
	public ApiResponse<?> updateDyslrFlag(HttpServletRequest request) {

		System.out.println("I am here into updatedyslrFlag method---------------: ");
		
		int updatedCount = repository.updateDyslrFlag(request.getHeader("echHost"), request.getHeader("echDbName"),
				request.getHeader("echSchemaName"), CommonConstants.echUser, CommonConstants.echPassword);
	    System.out.println("LGD UPDATED COUNT ------------------------------------ = " + updatedCount);


		return ApiResponse.builder().status(200).success(true).message(updatedCount + " records updated successfully.")
				.updatedCount(updatedCount).build();

	}
}

