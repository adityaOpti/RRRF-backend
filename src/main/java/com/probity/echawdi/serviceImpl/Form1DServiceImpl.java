/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.Form1DDao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.entity.Form1D;
import com.probity.echawdi.exception.FieldValidationException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form1DRepository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.service.Form1DService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Bhagyashri Lolage
 *
 */

@Component
public class  Form1DServiceImpl implements Form1DService{

	@Autowired
	private Form1DRepository form1DRepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;

//	@Override
//	public List<Form1DDao> getForm1DArea(String cCode, String pin, String hissaNo,HttpServletRequest request,Locale locale) {
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//		List<Object[]> form1DArea = form1DRepository.getForm1DArea(
//				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//				cCode,pin,hissaNo);
//		DatabaseContextHolder.restoreCtx();
//		if(Collections.isEmpty(form1DArea)) {
//			throw new ResourceNotFoundException("error.form.area",messageSource,locale);
//		}
//		else {
//			if(form1DArea!=null) {
//				List<Form1DDao> lstForm1D = form1DArea.stream().map((object) -> {
//					Form1DDao form1DDao=new Form1DDao();
//					form1DDao.setcCode(object[0].toString());
//					form1DDao.setPin(object[1].toString());
//					form1DDao.setHissaNo(object[2].toString());
//					form1DDao.setTotalAreaH(object[3].toString());
//					form1DDao.setAssessment(object[4].toString());
//					form1DDao.setIrrigatedArea(object[5].toString());
//					form1DDao.setUnirrigatedArea(object[6].toString());
//					return form1DDao;
//				}). collect(Collectors.toList());
//				return (lstForm1D);
//			}
//			return null;
//		}
//	}
	
	@Override
	public Response getAllForm1DData(String cCode, String pin,HttpServletRequest request,Locale locale) {
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
		List<Object[]> form1D =new ArrayList<>();
		if(pin.isEmpty()) {
			form1D = form1DRepository.findByCcode(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode,'Y');
		}else {
			form1D = form1DRepository.findByCcodeAndPin(
					request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
					request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
					cCode,pin,'Y');
		}
		DatabaseContextHolder.restoreCtx();
		if(form1D.isEmpty()) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}
		Response form1DResponse =new  Response();
		form1DResponse.setForm1DData(form1D.stream().map((object) -> {
			Form1DDao form1DDao = new Form1DDao();
			form1DDao.setcCode(object[0].toString());
			form1DDao.setPin(object[1].toString());
			form1DDao.setHissaNo(object[2].toString());
			form1DDao.setTotalAreaH(object[3].toString());
			form1DDao.setAssessment(object[4].toString());
			if(object[5]!=null) {
			form1DDao.setId(Long.valueOf(object[5].toString()));
			}
			form1DDao.setRemarks(object[6]==null ? "" : object[6].toString());
			form1DDao.setIrrigatedArea(object[7].toString());
			form1DDao.setUnirrigatedArea(object[8].toString());
			form1DDao.setRules(object[9]==null ? "" :object[9].toString());
			return form1DDao;
		}).sorted(Comparator.comparing(Form1DDao:: getIntPin)).collect(Collectors.toList()));
		return form1DResponse;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}

	@Override
	public Response getForm1DReport(String cCode,HttpServletRequest request,Locale locale) {
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
		List<Object[]> form1DReport = form1DRepository.getForm1DReport(
				  request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				  request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				  cCode,'Y');
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(form1DReport)) {
			throw new ResourceNotFoundException("error.form.reportdetails",messageSource,locale);
		}
		else {
			Response form1DResponse =new  Response();
			form1DResponse.setForm1DData(form1DReport.stream().map((object) -> {
				Form1DDao form1DDao = new Form1DDao();
				form1DDao.setcCode(object[0].toString());
				form1DDao.setPin(object[1].toString());
				form1DDao.setHissaNo(object[2].toString());
				form1DDao.setTotalAreaH(object[3].toString());
				form1DDao.setAssessment(object[4].toString());
				if(object[5]!=null) {
				form1DDao.setId(Long.valueOf(object[5].toString()));
				}
				form1DDao.setRemarks(object[6]==null ? "" : object[6].toString());
				form1DDao.setIrrigatedArea(object[7].toString());
				form1DDao.setUnirrigatedArea(object[8].toString());
				form1DDao.setRules(object[9]==null ? "" :object[9].toString());
				return form1DDao;
			}).sorted(Comparator.comparing(Form1DDao:: getIntPin)).collect(Collectors.toList()));
			return  form1DResponse;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}
	
	@Override
	public Form1DDao getForm1DById(long id,HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form1D form1D =form1DRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form1D", id));
		DatabaseContextHolder.restoreCtx();
		return toDao(form1D);
	}

	@Override
	public MessageResponse save(Form1DDao form1DDao,HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		if(form1DDao.getcCode() != null) {
			Form1D form1D = toEntity(form1DDao);
			if(form1DDao.getHissaNo() != null) {
				String [] hissaNoo = form1DDao.getHissaNo().split("/");
				if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
					String  tHissaNo = form1DDao.getHissaNo();
					for (int i = hissaNoo.length; i <= 8; i++) {
						tHissaNo += " /";
					}
					hissaNoo = tHissaNo.split("/");
				}
				List<String> lstHissNo = Arrays.asList(hissaNoo);
				//System.out.println("lstHissNo--->"+lstHissNo);
				form1D.setPin1(lstHissNo.get(0));
				form1D.setPin2(lstHissNo.get(1));
				form1D.setPin3(lstHissNo.get(2));
				form1D.setPin4(lstHissNo.get(3));
				form1D.setPin5(lstHissNo.get(4));
				form1D.setPin6(lstHissNo.get(5));
				form1D.setPin7(lstHissNo.get(6));
				form1D.setPin8(lstHissNo.get(7));

			}
			form1DRepository.save(form1D);
			DatabaseContextHolder.restoreCtx();
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.savedata", null, locale)).build();	
		}
		throw new FieldValidationException("error.form.errorwhilesave");
	}



	@Override 
	public void updateForm1D(Form1DDao form1DDao,HttpServletRequest request,Locale locale){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form1D form1D= new Form1D();
		if(form1DDao.getId()!=null) {
		 form1D = form1DRepository.findById(form1DDao.getId()).
				orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form1D", form1DDao.getId() ));
		}
		BeanUtils.copyProperties(form1DDao, form1D);
		if(form1DDao.getHissaNo() != null) {
			String [] hissaNoo = form1DDao.getHissaNo().split("/");
			if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
				String  tHissaNo = form1DDao.getHissaNo();
				for (int i = hissaNoo.length; i <= 8; i++) {
					tHissaNo += " /";
				}
				hissaNoo = tHissaNo.split("/");
			}
			List<String> lstHissNo = Arrays.asList(hissaNoo);
			//System.out.println("lstHissNo--->"+lstHissNo);
			form1D.setPin1(lstHissNo.get(0));
			form1D.setPin2(lstHissNo.get(1));
			form1D.setPin3(lstHissNo.get(2));
			form1D.setPin4(lstHissNo.get(3));
			form1D.setPin5(lstHissNo.get(4));
			form1D.setPin6(lstHissNo.get(5));
			form1D.setPin7(lstHissNo.get(6));
			form1D.setPin8(lstHissNo.get(7));
		}
		form1DRepository.save(form1D);
		DatabaseContextHolder.restoreCtx();
	}


	@Override
	public void deleteForm1DById(long id,HttpServletRequest request,Locale locale){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form1D form1D =form1DRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form1D", id));
		form1D.setActiveFlag('N');
		form1DRepository.save(form1D);
		DatabaseContextHolder.restoreCtx();
	}




	private Form1DDao toDao(Form1D form1D){
		Form1DDao form1DDao = new Form1DDao();
		BeanUtils.copyProperties(form1D, form1DDao);
		return form1DDao;
	}

	private Form1D toEntity(Form1DDao form1dDao){
		Form1D form1D = new Form1D();
		BeanUtils.copyProperties(form1dDao, form1D);
		return form1D;
	}
}