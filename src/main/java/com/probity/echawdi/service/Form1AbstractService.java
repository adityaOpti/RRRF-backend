/**
 * 
 */
package com.probity.echawdi.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.probity.echawdi.dao.Form1AbstractDao;
import com.probity.echawdi.dao.Form1AbstractDyslrDao;

/**
 * @author Bhagyashri Lolage
 *
 */
public interface Form1AbstractService {

	//List<Form1AbstractDao> getForm1AbstractDetails(Integer districtCode,Integer talukaCode,String cCode,Locale locale);

	//MessageResponse save(Form1AbstractDao form1AbstractDao, Locale locale);

	void updateForm1Abstract(@Valid Form1AbstractDao form1AbstractDao, Locale locale,HttpServletRequest request);

	void deleteForm1AbstractById(long id, Locale locale,HttpServletRequest request);

	//****DYSLR****
	void updateForm1AbstractDyslr(@Valid Form1AbstractDyslrDao form1AbstractDyslrDao, Locale locale,HttpServletRequest request);
    Form1AbstractDyslrDao getForm1AbstractDyslrData(String cCode, Locale locale, HttpServletRequest request);
//	Form1AbstractDyslrDao getForm1AbstractDyslrReport(Integer districtCode, Integer talukaCode, String cCode,Locale locale,HttpServletRequest request);


	Form1AbstractDao getForm1AbstractData(String cCode, Locale locale,HttpServletRequest request);

	Form1AbstractDao getForm1AbstractReport(Integer districtCode, Integer talukaCode, String cCode,Locale locale,HttpServletRequest request);
}
