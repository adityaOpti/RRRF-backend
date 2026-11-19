package com.probity.echawdi.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.probity.echawdi.dao.Form7ADao;
import com.probity.echawdi.dao.Form7Dao;

public interface SurveyHissaAndKhataNo {

	List<Form7Dao> getHissaNo(String mhrDbName, String mhrSchemaName, String mhrhost, String cCode, String pin,
			Character marked);

	List<Form7ADao> getPotHissa(String mhrDbName, String mhrSchemaName, String mhrhost, String cCode, String pin,
			String hissaNo);

	List<Form7ADao> getKhataNo(String mhrDbName, String mhrSchemaName, String mhrhost, String cCode, String pin,
			String hissaNo, String khataNo);

}
