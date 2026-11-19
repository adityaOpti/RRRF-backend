package com.probity.echawdi.service;


import com.probity.echawdi.dao.ApiResponseDecryptExternal;
import com.probity.echawdi.dao.ImageDataForSatBara;

public interface ExternalApiService {
	
	
	ApiResponseDecryptExternal<ImageDataForSatBara> callExternalApi(String lgd_code, 
																		String pin,
																		String pin1,
																		String pin2,
																		String pin3,
																		String pin4,
																		String pin5,
																		String pin6,
																		String pin7,
																		String pin8
																		);
	
	
	
	ApiResponseDecryptExternal<ImageDataForSatBara> callExternalApiFerfar(String lgd_code, 
			String mut_no
			);
}