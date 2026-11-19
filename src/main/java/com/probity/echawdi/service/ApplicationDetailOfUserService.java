package com.probity.echawdi.service;

import com.probity.echawdi.payload.ResponseForSevenB;

public interface ApplicationDetailOfUserService {

	ResponseForSevenB getEpeekPahaniResult(String mhrDbName, String mhrSchemaName, String mhrhost, String cCode,
			int yearCulti, int numericpin, int seasonCode);
}

