package com.probity.echawdi.service;

import javax.servlet.http.HttpServletRequest;

import com.probity.echawdi.dao.ApiResponse;

public interface LgdMapperService {
	
	public ApiResponse<?> updateDyslrFlag(HttpServletRequest request);


}
