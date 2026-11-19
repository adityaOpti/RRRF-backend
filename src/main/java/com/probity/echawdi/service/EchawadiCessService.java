package com.probity.echawdi.service;

import java.util.Locale;

import org.springframework.stereotype.Service;

import com.probity.echawdi.dao.EchawadiCessDao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

@Service
public interface EchawadiCessService {

	Response getAllEchawadiCessData(int pageNo,int pageSize, String sortBy, String sortDir,Locale locale );
	
	EchawadiCessDao getEchawadiCessById(long id, Locale locale);

	MessageResponse save(EchawadiCessDao echawadiCessDao,Locale locale);

	void updateEchawadiCess(EchawadiCessDao echawadiCessDao,Locale locale);

	void deleteEchawadiCessById(long id,Locale locale);


}