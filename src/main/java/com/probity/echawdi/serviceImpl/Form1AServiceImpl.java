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
import com.probity.echawdi.dao.Form1ADao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.entity.Form1A;
import com.probity.echawdi.exception.FieldValidationException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form1ARepository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.service.Form1AService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Bhagyashri Dahale
 *
 */

@Component
public class  Form1AServiceImpl implements Form1AService{

	@Autowired
	private Form1ARepository form1ARepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;

	@Override
	public Response getAllForm1AData(String cCode,HttpServletRequest request,Locale locale) {
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
		List<Form1A> form1A = form1ARepository.findBycCodeAndActiveFlag(cCode,'Y');
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(form1A)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}
		Response form1AResponse =new  Response();
		form1AResponse.setForm1AData(form1A.stream().map(object -> toDao(object)).collect(Collectors.toList()));
		return form1AResponse;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}

	@Override
	public Response getForm1AReport(String cCode,HttpServletRequest request,Locale locale) {
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
		List<Form1A> form1A = form1ARepository.findBycCodeAndActiveFlag(cCode,'Y');
		DatabaseContextHolder.restoreCtx();	
		if(Collections.isEmpty(form1A)) {
			throw new ResourceNotFoundException("error.form.reportdetails",messageSource,locale);
		}else {
			Response form1AResponse =new  Response();
			form1AResponse.setForm1AData(form1A.stream().map(object -> toDao(object)).collect(Collectors.toList()));
			return form1AResponse;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}

	@Override
	public MessageResponse save(Form1ADao form1ADao,HttpServletRequest request,Locale locale) {

		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		if(form1ADao.getcCode() != null) {
			Form1A form1A = toEntity(form1ADao);

			if(form1ADao.getHissaNo() != null) {
				String [] hissaNoo = form1ADao.getHissaNo().split("/");
				if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
					String  tHissaNo = form1ADao.getHissaNo();
					for (int i = hissaNoo.length; i <= 8; i++) {
						tHissaNo += " /";
					}
					hissaNoo = tHissaNo.split("/");
				}
				List<String> lstHissNo = Arrays.asList(hissaNoo);
				//System.out.println("lstHissNo--->"+lstHissNo);
				form1A.setPin1(lstHissNo.get(0));
				form1A.setPin2(lstHissNo.get(1));
				form1A.setPin3(lstHissNo.get(2));
				form1A.setPin4(lstHissNo.get(3));
				form1A.setPin5(lstHissNo.get(4));
				form1A.setPin6(lstHissNo.get(5));
				form1A.setPin7(lstHissNo.get(6));
				form1A.setPin8(lstHissNo.get(7));
			}
			form1ARepository.save(form1A);
			DatabaseContextHolder.restoreCtx();
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.savedata", null, locale)).build();	
		}
		throw new FieldValidationException("error.form.errorwhilesave");
	}

	@Override 
	public void updateForm1A(Form1ADao form1ADao,HttpServletRequest request,Locale locale){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form1A form1A = form1ARepository.findById(form1ADao.getId()).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form1A", form1ADao.getId()));
		BeanUtils.copyProperties(form1ADao, form1A);
		if(form1ADao.getHissaNo()!= null) {
			String [] hissaNoo = form1ADao.getHissaNo().split("/");
			if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
				String  tHissaNo = form1ADao.getHissaNo();
				for (int i = hissaNoo.length; i <= 8; i++) {
					tHissaNo += " /";
				}
				hissaNoo = tHissaNo.split("/");
			}
			List<String> lstHissNo = Arrays.asList(hissaNoo);
			//System.out.println("lstHissNo--->"+lstHissNo);
			form1A.setPin1(lstHissNo.get(0));
			form1A.setPin2(lstHissNo.get(1));
			form1A.setPin3(lstHissNo.get(2));
			form1A.setPin4(lstHissNo.get(3));
			form1A.setPin5(lstHissNo.get(4));
			form1A.setPin6(lstHissNo.get(5));
			form1A.setPin7(lstHissNo.get(6));
			form1A.setPin8(lstHissNo.get(7));
		}
		form1ARepository.save(form1A);
		DatabaseContextHolder.restoreCtx();
	}

	@Override
	public void deleteForm1AById(long id,HttpServletRequest request,Locale locale){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form1A form1A =form1ARepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form1A", id));
		form1A.setActiveFlag('N');
		form1ARepository.save(form1A);
		DatabaseContextHolder.restoreCtx();
	}

	@Override
	public Form1ADao getForm1AById(long id,HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form1A form1A =form1ARepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form1A", id));
		DatabaseContextHolder.restoreCtx();
		return toDao(form1A);
	}

	private Form1ADao toDao(Form1A form1A){
		Form1ADao form1ADao = new Form1ADao();
		BeanUtils.copyProperties(form1A, form1ADao);
		String hissaNo = Stream.of(
									form1A.getPin1().trim().isEmpty() ? null : form1A.getPin1(),  
									form1A.getPin2().trim().isEmpty() ? null : "/"+form1A.getPin2(), 
									form1A.getPin3().trim().isEmpty() ? null : "/"+form1A.getPin3(),
									form1A.getPin4().trim().isEmpty() ? null : "/"+form1A.getPin4(),
									form1A.getPin5().trim().isEmpty() ? null : "/"+form1A.getPin5(),
									form1A.getPin6().trim().isEmpty() ? null : "/"+form1A.getPin6(),
									form1A.getPin7().trim().isEmpty() ? null : "/"+form1A.getPin7(),
									form1A.getPin8().trim().isEmpty() ? null : "/"+form1A.getPin8())
									.filter(s -> s != null)
									.collect(Collectors.joining());
									form1ADao.setHissaNo(hissaNo.isEmpty() ? "" : hissaNo);
									return form1ADao;
		}
	private Form1A toEntity(Form1ADao form1ADao){
		Form1A form1A = new Form1A();
		BeanUtils.copyProperties(form1ADao, form1A,"id");
		return form1A;
	}
}


//List<Object[]> form1A = form1ARepository.getAllForm1AData(
//request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
//cCode,pin,'Y');
//form1AResponse.setForm1AData(form1A.stream().map((object) -> {
//Form1ADao form1ADao = new Form1ADao();
//form1ADao.setcCode(object[0].toString());
//form1ADao.setPin(object[1].toString());
//form1ADao.setHissaNo(object[2].toString());
////      	form1ADao.setKhataNo(object[3].toString());
//form1ADao.setTypeOfLand(object[4].toString());
//form1ADao.setWhetherVillageOrProtectedOrReservedForest(object[5].toString());
//form1ADao.setArea(BigDecimal.valueOf(Double.valueOf(object[6].toString())));
//form1ADao.setRightsRecordedByTheForestOfficer(object[7].toString());
//form1ADao.setRemarks(object[8].toString());
//form1ADao.setId(Long.parseLong(object[9].toString()));
//form1ADao.setUom(object[10].toString());
//return form1ADao;
//}).collect(Collectors.toList()));

//List<Object[]> form1A = form1ARepository.getForm1AReport(
//request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
//cCode,'Y');
//
//if(Collections.isEmpty(form1A)) {
//throw new ResourceNotFoundException("error.form.reportdetails",messageSource,locale);
//}else {
//Response form1AResponse =new  Response();
//form1AResponse.setForm1AData(form1A.stream().map((object) -> {
//Form1ADao form1ADao = new Form1ADao();
//form1ADao.setcCode(object[0].toString());
//form1ADao.setPin(object[1].toString());
//form1ADao.setHissaNo(object[2].toString());
////      	form1ADao.setKhataNo(object[3].toString());
//form1ADao.setTypeOfLand(object[4].toString());
//form1ADao.setWhetherVillageOrProtectedOrReservedForest(object[5].toString());
//form1ADao.setArea(BigDecimal.valueOf(Double.valueOf(object[6].toString())));
//form1ADao.setRightsRecordedByTheForestOfficer(object[7].toString());
//form1ADao.setRemarks(object[8].toString());
//form1ADao.setId(Long.parseLong(object[9].toString()));
//form1ADao.setUom(object[10].toString());
//return form1ADao;
//}).collect(Collectors.toList()));
//return form1AResponse;
//}