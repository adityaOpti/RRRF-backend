/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.Form7BDao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.entity.Form7B;
import com.probity.echawdi.exception.FieldValidationException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form7BRepository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.service.Form7BService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Bhagyashri Lolage 
 *
 */
@Component
public class Form7BServiceImpl implements Form7BService {
	
	@Autowired
	private Form7BRepository form7BRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;
	
	@Override
	public Response getAllForm7BData(String cCode, String pin,Locale locale,HttpServletRequest request) {
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
		List<Form7B> listOfform7B= form7BRepository.findBycCodeAndPinAndActiveFlag(cCode,pin,'Y');
      	DatabaseContextHolder.restoreCtx();
      	if(Collections.isEmpty(listOfform7B)) {
      		throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
      	}
      	Response form7BResponse =new  Response();
      	form7BResponse.setForm7BData(listOfform7B.stream().map(form7BR -> toDao(form7BR)).collect(Collectors.toList()));
      	return form7BResponse;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
     }
	

	
	public MessageResponse save(Form7BDao Form7BDao,Locale locale,HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		 if(Form7BDao.getcCode() != null) {
				Form7B Form7B = toEntity(Form7BDao);
				if(Form7BDao.getHissaNo() != null) {
					
					String [] hissaNoo = Form7BDao.getHissaNo().split("/");
					if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
						String  tHissaNo = Form7BDao.getHissaNo();
						for (int i = hissaNoo.length; i <= 8; i++) {
							tHissaNo += " /";
						}
						hissaNoo = tHissaNo.split("/");
					}
					List<String> lstHissNo = Arrays.asList(hissaNoo);
					//System.out.println("lstHissNo--->"+lstHissNo);
					
					Form7B.setPin1(lstHissNo.get(0));
					Form7B.setPin2(lstHissNo.get(1));
					Form7B.setPin3(lstHissNo.get(2));
					Form7B.setPin4(lstHissNo.get(3));
					Form7B.setPin5(lstHissNo.get(4));
					Form7B.setPin6(lstHissNo.get(5));
					Form7B.setPin7(lstHissNo.get(6));
					Form7B.setPin8(lstHissNo.get(7));
					
				}
				form7BRepository.save(Form7B);

		DatabaseContextHolder.restoreCtx();
		return MessageResponse.builder().status("SUCCESS").message(
				messageSource.getMessage("error.form.savedata", null, locale)).build();	
    }
    throw new FieldValidationException("error.form.errorwhilesave");
}
	
	
	
	@Override 
	public void updateForm7B(Form7BDao form7BDao,Locale locale,HttpServletRequest request){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form7B form7B = form7BRepository.findById(form7BDao.getId()).
				        orElseThrow(() ->  new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form7B", form7BDao.getId(), null));
		BeanUtils.copyProperties(form7BDao, form7B);
		if(form7BDao.getHissaNo() != null) {
			String [] hissaNoo = form7BDao.getHissaNo().split("/");
			if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
				String  tHissaNo = form7BDao.getHissaNo();
				for (int i = hissaNoo.length; i <= 8; i++) {
					tHissaNo += " /";
				}
				hissaNoo = tHissaNo.split("/");
			}
			List<String> lstHissNo = Arrays.asList(hissaNoo);
			//System.out.println("lstHissNo--->"+lstHissNo);
			form7B.setPin1(lstHissNo.get(0));
			form7B.setPin2(lstHissNo.get(1));
			form7B.setPin3(lstHissNo.get(2));
			form7B.setPin4(lstHissNo.get(3));
			form7B.setPin5(lstHissNo.get(4));
			form7B.setPin6(lstHissNo.get(5));
			form7B.setPin7(lstHissNo.get(6));
			form7B.setPin8(lstHissNo.get(7));
			}
		form7BRepository.save(form7B);
		DatabaseContextHolder.restoreCtx();
	}
	
	
	@Override
	public void deleteForm7BById(long id,Locale locale,HttpServletRequest request){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		 Form7B form7B =form7BRepository.findById(id).
				 		orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form7B", id));
		 form7B.setActiveFlag('N');
		 form7BRepository.save(form7B);
		 DatabaseContextHolder.restoreCtx();
	 }
	

	
	private Form7BDao toDao(Form7B form7B){
		Form7BDao form7BDao = new Form7BDao();
		BeanUtils.copyProperties(form7B, form7BDao);
		form7BDao.setFullName(form7BDao.getFnamePersonInPossession() +" "+form7BDao.getMnamePersonInPossession() +" "+form7BDao.getLnamePersonInPossession() );
		String hissaNo = Stream.of(
				form7B.getPin1().trim().isEmpty() ? null : form7B.getPin1(),  
						form7B.getPin2().trim().isEmpty() ? null : "/"+form7B.getPin2(), 
								form7B.getPin3().trim().isEmpty() ? null : "/"+form7B.getPin3(),
										form7B.getPin4().trim().isEmpty() ? null : "/"+form7B.getPin4(),
												form7B.getPin5().trim().isEmpty() ? null : "/"+form7B.getPin5(),
														form7B.getPin6().trim().isEmpty() ? null : "/"+form7B.getPin6(),
																form7B.getPin7().trim().isEmpty() ? null : "/"+form7B.getPin7(),
																		form7B.getPin8().trim().isEmpty() ? null : "/"+form7B.getPin8())
				.filter(s -> s != null)
				.collect(Collectors.joining());
		form7BDao.setHissaNo(hissaNo.isEmpty() ? "" : hissaNo);
				return form7BDao;
}
	
	private Form7B toEntity(Form7BDao Form7BDao){
		Form7B Form7B = new Form7B();
		BeanUtils.copyProperties(Form7BDao, Form7B);
		return Form7B;
	}


	@Override
	public Response getAllForm7BReportDetails(String cCode,Locale locale,HttpServletRequest request) {
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
		List<Form7B> listOfform7B = form7BRepository.findBycCodeAndActiveFlagOrderByPinAsc(cCode,'Y');
      	DatabaseContextHolder.restoreCtx();
      	if(Collections.isEmpty(listOfform7B)) {
      		throw new ResourceNotFoundException("error.form.reportdetails",messageSource,locale);
      	}
      	Response form7BResponse =new  Response();
      	form7BResponse.setForm7BData(listOfform7B.stream().map(form7BR -> toDao(form7BR)).collect(Collectors.toList()));
      	return form7BResponse;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
     }


//native
	@Override
	public Response getKhataNodetails(String cCode, String pin,String hissaNo,Locale locale,HttpServletRequest request) {
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
		List<Object[]> form7BDtl=form7BRepository.getKhataNodetails(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				cCode,pin,hissaNo);
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(form7BDtl)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}
		else {
			Response form7BResponse =new  Response();
			if(form7BDtl!=null) {
				form7BResponse.setForm7BData(form7BDtl.stream().map((object) -> {
					Form7BDao form7BDao=new Form7BDao();
					form7BDao.setKhataNo(object[0].toString());
					form7BDao.setPin(object[1].toString());
					form7BDao.setHissaNo(object[2].toString());
					return form7BDao;
					}). collect(Collectors.toList()));
					return form7BResponse;
				}
				return null;
			}}else {
				throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
			}
			
	}
	
	

}
