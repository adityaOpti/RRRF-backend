/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.Form7ADao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.entity.Form7A;
import com.probity.echawdi.exception.FieldValidationException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form7ARepository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.service.Form7AService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Bhagyashri Lolage
 *
 */

@Component
public class Form7AServiceImpl  implements Form7AService{

	@Autowired
	private Form7ARepository form7ARepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;

	@Override
	public List<Form7ADao> getForm7AEferfarDetials(String cCode, String pin, String hissaNo,HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("servarthId"));
		Response response =new  Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km=new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));
		
		
		String ccodes=null;
		for(KhataMergeDao r : response.getKhataMergeData()) {
			if(r.getcCode().equals(cCode)) {
			ccodes=r.getcCode();
			break;
			}else{
				ccodes="0";
				}}
		
		if(ccodes.equals(cCode)) {	
		List<Object[]> form7ADtl = form7ARepository.getForm7AEferfarDetials(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				cCode,pin,hissaNo
				);
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(form7ADtl)) {
			throw new ResourceNotFoundException("error.form.eferfar",messageSource,locale);
		}
		else {
			List<Form7ADao> lstForm7A = form7ADtl.stream().map((object) -> {
				Form7ADao form7ADao=new Form7ADao();
				form7ADao.setcCode(object[0].toString());
				form7ADao.setPin(object[1].toString());
				form7ADao.setLandlordName(object[2].toString());
				form7ADao.setTotalArea(object[3].toString());
				form7ADao.setAssessment(object[4].toString());
				form7ADao.setHissaNo(object[5].toString());
				form7ADao.setKhataNo(object[6].toString());
				return form7ADao;
			}). collect(Collectors.toList());
			return lstForm7A;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}



	@Override
	public Response getAllForm7AData(String cCode, String pin,HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("servarthId"));
		Response response =new  Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km=new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));
		
		
		String ccodes=null;
		for(KhataMergeDao r : response.getKhataMergeData()) {
			if(r.getcCode().equals(cCode)) {
			ccodes=r.getcCode();
			break;
			}else{
				ccodes="0";
				}}
		
		if(ccodes.equals(cCode)) {	
		List<Object[]> listOfform7A = form7ARepository.getAllForm7AData(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode,pin,'Y'
				);
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(listOfform7A)) {
      		throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
      	}
		Response form7AResponse =new  Response();
		form7AResponse.setForm7AData(listOfform7A.stream().map((object) -> {
			Form7ADao form7ADao = new Form7ADao();
			form7ADao.setLastYearSerialNo(object[0] ==null ? "" : object[0].toString());
			form7ADao.setNameOfTenant(object[1] ==null ? "" : object[1].toString());
			form7ADao.setLandlordName(object[2] ==null ? "" : object[2].toString());
			form7ADao.setPin(object[3].toString());
			form7ADao.setTotalArea(object[4] ==null ? "" : object[4].toString());
			form7ADao.setAssessment(object[5] ==null ? "" : object[5].toString());
			form7ADao.setRent(object[6] ==null ? "" : object[6].toString());
			form7ADao.setSerialNoInMutationReg(object[7]==null ? "" : object[7].toString());
			form7ADao.setHissaNo(object[8].toString());
			form7ADao.setRemarks(object[9] == null ? "" :object[9].toString());
			form7ADao.setId(Long.valueOf(object[10].toString()));
			form7ADao.setArea(object[11] ==null ? "" : object[11].toString());
			form7ADao.setKhataNo(object[12] == null ? "" : object[12].toString());
			return form7ADao;
		}).collect(Collectors.toList()));
		return form7AResponse;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}

	@Override
	public Response getForm7AReportDetails(String cCode,HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("servarthId"));
		Response response =new  Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km=new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));
		
		
		String ccodes=null;
		for(KhataMergeDao r : response.getKhataMergeData()) {
			if(r.getcCode().equals(cCode)) {
			ccodes=r.getcCode();
			break;
			}else{
				ccodes="0";
				}}
		
		if(ccodes.equals(cCode)) {	
		List<Object[]> listOfform7A = form7ARepository.getForm7AReportDetails(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode,'Y'
				);
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(listOfform7A)) {
      		throw new ResourceNotFoundException("error.form.reportdetails",messageSource,locale);
      	}
		Response form7AResponse =new  Response();
	    form7AResponse.setForm7AData(listOfform7A.stream().map((object) -> {
	    	Form7ADao form7ADao = new Form7ADao();
	    	form7ADao.setLastYearSerialNo(object[0] ==null ? "" : object[0].toString());
			form7ADao.setNameOfTenant(object[1] ==null ? "" : object[1].toString());
			form7ADao.setLandlordName(object[2] ==null ? "" : object[2].toString());
			form7ADao.setPin(object[3].toString());
			form7ADao.setTotalArea(object[4] ==null ? "" : object[4].toString());
			form7ADao.setAssessment(object[5] ==null ? "" : object[5].toString());
			form7ADao.setRent(object[6] ==null ? "" : object[6].toString());
			form7ADao.setSerialNoInMutationReg(object[7]==null ? "" : object[7].toString());
			form7ADao.setHissaNo(object[8].toString());
			form7ADao.setRemarks(object[9] == null ? "" :object[9].toString());
			form7ADao.setId(Long.valueOf(object[10].toString()));
			form7ADao.setArea(object[11] ==null ? "" : object[11].toString());
			form7ADao.setKhataNo(object[12] == null ? "" : object[12].toString());
	    	
	    	
		 return form7ADao;
	     }).collect(Collectors.toList()));
      	return form7AResponse;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
     }


	public MessageResponse save(Form7ADao form7ADao,HttpServletRequest request,Locale locale) {
		if(form7ADao.getcCode() != null) {
			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
			Form7A form7A = toEntity(form7ADao);
			String lastYearSerialNo= form7ADao.getLastYearSerialNo();
			form7A.setLastYearSerialNo(lastYearSerialNo);
			if(form7ADao.getHissaNo() != null) {

				String [] hissaNoo = form7ADao.getHissaNo().split("/");
				if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
					String  tHissaNo = form7ADao.getHissaNo();
					for (int i = hissaNoo.length; i <= 8; i++) {
						tHissaNo += " /";
					}
					hissaNoo = tHissaNo.split("/");
				}
				List<String> lstHissNo = Arrays.asList(hissaNoo);
				//System.out.println("lstHissNo--->"+lstHissNo);
				form7A.setPin1(lstHissNo.get(0));
				form7A.setPin2(lstHissNo.get(1));
				form7A.setPin3(lstHissNo.get(2));
				form7A.setPin4(lstHissNo.get(3));
				form7A.setPin5(lstHissNo.get(4));
				form7A.setPin6(lstHissNo.get(5));
				form7A.setPin7(lstHissNo.get(6));
				form7A.setPin8(lstHissNo.get(7));
				form7ARepository.save(form7A);
				DatabaseContextHolder.restoreCtx();
			}
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.savedata", null, locale)).build();	
		}
		throw new FieldValidationException("error.form.errorwhilesave");
	}


	public void updateForm7A(Form7ADao form7ADao,HttpServletRequest request,Locale locale){
		
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form7A form7A = form7ARepository.findById(form7ADao.getId()).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form7A", form7ADao.getId()));
		DatabaseContextHolder.restoreCtx();
		BeanUtils.copyProperties(form7ADao, form7A);
		
		if(form7ADao.getHissaNo() != null) {
			String [] hissaNoo = form7ADao.getHissaNo().split("/");
			if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
				String  tHissaNo = form7ADao.getHissaNo();
				for (int i = hissaNoo.length; i <= 8; i++) {
					tHissaNo += " /";
				}
				hissaNoo = tHissaNo.split("/");
			}
			List<String> lstHissNo = Arrays.asList(hissaNoo);
			//System.out.println("lstHissNo--->"+lstHissNo);
			form7A.setPin1(lstHissNo.get(0));
			form7A.setPin2(lstHissNo.get(1));
			form7A.setPin3(lstHissNo.get(2));
			form7A.setPin4(lstHissNo.get(3));
			form7A.setPin5(lstHissNo.get(4));
			form7A.setPin6(lstHissNo.get(5));
			form7A.setPin7(lstHissNo.get(6));
			form7A.setPin8(lstHissNo.get(7));
		}
		form7ARepository.save(form7A);
	}

	public void deleteForm7AById(long id,HttpServletRequest request,Locale locale){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form7A form7A =form7ARepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form7A", id));
		form7A.setActiveFlag('N');
		form7ARepository.save(form7A);
		DatabaseContextHolder.restoreCtx();
	}

	private Form7A toEntity(Form7ADao form7ADao){
		Form7A form7A = new Form7A();
		BeanUtils.copyProperties(form7ADao, form7A,"id");
		return form7A;
	}



	@Override
	public List<Form7ADao> getForm7AEferfarKhataDetials(String cCode, String pin, String hissaNo, String khataNo,
			HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("servarthId"));
		Response response =new  Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km=new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));
		
		
		String ccodes=null;
		for(KhataMergeDao r : response.getKhataMergeData()) {
			if(r.getcCode().equals(cCode)) {
			ccodes=r.getcCode();
			break;
			}else{
				ccodes="0";
				}}
		
		if(ccodes.equals(cCode)) {	
		List<Object[]> form7ADtl = form7ARepository.getForm7AEferfarKhataDetials(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				cCode,pin,hissaNo,khataNo);
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(form7ADtl)) {
			throw new ResourceNotFoundException("error.form.eferfar",messageSource,locale);
		}
		else {
			List<Form7ADao> lstForm7A = form7ADtl.stream().map((object) -> {
				Form7ADao form7ADao=new Form7ADao();
				form7ADao.setcCode(object[0].toString());
				form7ADao.setPin(object[1].toString());
				form7ADao.setLandlordName(object[2].toString());
				form7ADao.setTotalArea(object[3].toString());
				form7ADao.setAssessment(object[4].toString());
				form7ADao.setHissaNo(object[5].toString());
				form7ADao.setKhataNo(object[6].toString());
				return form7ADao;
			}). collect(Collectors.toList());
			return lstForm7A;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}
}