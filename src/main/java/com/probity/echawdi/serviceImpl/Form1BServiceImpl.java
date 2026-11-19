/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.math.BigDecimal;
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
import com.probity.echawdi.dao.Form1BDao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.entity.Form1B;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form1BRepository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.service.Form1BService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Bhagyashri Lolage
 *
 */

@Component
public class  Form1BServiceImpl implements Form1BService{

	@Autowired
	private Form1BRepository form1BRepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;

//	@Override
//	public Response getForm1BAssesment(String cCode, String pin, String hissaNo,HttpServletRequest request,Locale locale) {
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//		List<Object[]> form1BDtl = form1BRepository.getForm1BAssesment(
//				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//				cCode,pin,hissaNo);
//		DatabaseContextHolder.restoreCtx();
//		if(form1BDtl.isEmpty()) {
//			throw new ResourceNotFoundException("error.form.assesment",messageSource,locale);
//		}
//		else {
//			Response form1BResponse =new  Response();
//			form1BResponse.setForm1BData(form1BDtl.stream().map((object) -> {
//				Form1BDao form1BDao = new Form1BDao();
////				272500130318050000, , 3.7100, 9.87, 0, 0
//				form1BDao.setcCode(object[0].toString());
//				form1BDao.setPin(object[1].toString());
//				form1BDao.setPin1(object[2].toString());
//				form1BDao.setPin2(object[3].toString());
//				form1BDao.setPin3(object[4].toString());
//				form1BDao.setPin4(object[5].toString());
//				form1BDao.setPin5(object[6].toString());
//				form1BDao.setPin6(object[7].toString());
//				form1BDao.setPin7(object[8].toString());
//				form1BDao.setPin8(object[9].toString());
//				form1BDao.setTotalAreaH(object[10].toString());
//				form1BDao.setHissaNo(object[11].toString());
//				form1BDao.setAssessment(object[12].toString());
//				form1BDao.setTotalAreaR(object[13].toString());
//				form1BDao.setTotalAreaSqm(object[14].toString());
//				return form1BDao;
//			}).collect(Collectors.toList()));
//			return form1BResponse;
//		}
//	}

	@Override
	public Response getAllForm1BData(String cCode, String pin,HttpServletRequest request,Locale locale) {
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
		List<Object[]> listOfForm1B =new ArrayList<>();
		if(pin.isEmpty()) {
			listOfForm1B= form1BRepository.findByccode(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
					request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
					cCode,'Y');
		}else {
			listOfForm1B= form1BRepository.findAllByPin(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
					request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
					cCode,pin,'Y');
		}
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(listOfForm1B)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}
		Response form1BResponse =new  Response();
		form1BResponse.setForm1BData(listOfForm1B.stream().map((object) -> {
			Form1BDao form1BDao = new Form1BDao();
			form1BDao.setcCode(object[0].toString());
			form1BDao.setPin(object[1].toString());
			form1BDao.setHissaNo(object[2].toString());
			form1BDao.setTotalAreaH(object[3].toString());
			form1BDao.setAssessment(object[4].toString());
			if(object[5] !=null) {
				form1BDao.setId(Long.valueOf(object[5].toString()));
			}
			if(object[6] !=null) {
				form1BDao.setUnassessedArea(BigDecimal.valueOf(Double.valueOf(object[6].toString())));
			}
			form1BDao.setPublicRightsOfWayAndEasements(object[7]==null ? "" :object[7].toString());
			form1BDao.setRemarks(object[8] == null ? "" : object[8].toString());
			if(object[9]!=null) {
			form1BDao.setAssessedArea(BigDecimal.valueOf(Double.valueOf(object[9].toString())));
			}
			return form1BDao;
		}).sorted(Comparator.comparing(Form1BDao:: getIntPin)).collect(Collectors.toList()));
		return form1BResponse;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}

	@Override
	public Response getForm1BReport(String cCode,HttpServletRequest request,Locale locale) {
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
		List<Object[]> form1BReport = form1BRepository.findReport(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode,'Y');
		DatabaseContextHolder.restoreCtx();
		Response form1BResponse =new  Response();
		if(Collections.isEmpty(form1BReport)) {
			throw new ResourceNotFoundException("error.form.reportdetails",messageSource,locale);
		}
		else {
			form1BResponse.setForm1BData(form1BReport.stream().map((object) -> {
				Form1BDao form1BDao = new Form1BDao();
				form1BDao.setcCode(object[0].toString());
				form1BDao.setPin(object[1].toString());
				form1BDao.setHissaNo(object[2].toString());
				form1BDao.setTotalAreaH(object[3].toString());
				form1BDao.setAssessment(object[4].toString());
				if(object[5] !=null) {
					form1BDao.setId(Long.valueOf(object[5].toString()));
				}
				if(object[6] !=null) {
					form1BDao.setUnassessedArea(BigDecimal.valueOf(Double.valueOf(object[6].toString())));
				}
				form1BDao.setPublicRightsOfWayAndEasements(object[7]==null ? "" :object[7].toString());
				form1BDao.setRemarks(object[8] == null ? "" : object[8].toString());
				if(object[9]!=null) {
					form1BDao.setAssessedArea(BigDecimal.valueOf(Double.valueOf(object[9].toString())));
					}
				return form1BDao;
			}).sorted(Comparator.comparing(Form1BDao:: getIntPin)).collect(Collectors.toList()));
			return  form1BResponse;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}
	
	@Override
	public Form1BDao getForm1BById(long id,HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form1B form1B =form1BRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form1B", id));
		DatabaseContextHolder.restoreCtx();
		return toDao(form1B);
	}

	@Override
	public void deleteForm1BById(long id,HttpServletRequest request,Locale locale){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form1B form1B =form1BRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form1B", id));
		form1B.setActiveFlag('N');
		form1BRepository.save(form1B);
		DatabaseContextHolder.restoreCtx();
	}

//	@Override
//	public MessageResponse save(Form1BDao form1BDao,HttpServletRequest request,Locale locale) {
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//		if(form1BDao.getcCode() != null) {
//			Form1B form1B = toEntity(form1BDao);
//
//			if(form1BDao.getHissaNo()!= null) {
//
//				String [] hissaNoo = form1BDao.getHissaNo().split("/");
//				if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
//					String  tHissaNo = form1BDao.getHissaNo();
//					for (int i = hissaNoo.length; i <= 8; i++) {
//						tHissaNo += " /";
//					}
//					hissaNoo = tHissaNo.split("/");
//				}
//				List<String> lstHissNo = Arrays.asList(hissaNoo);         
//				form1B.setPin1(lstHissNo.get(0));
//				form1B.setPin2(lstHissNo.get(1));
//				form1B.setPin3(lstHissNo.get(2));
//				form1B.setPin4(lstHissNo.get(3));
//				form1B.setPin5(lstHissNo.get(4));
//				form1B.setPin6(lstHissNo.get(5));
//				form1B.setPin7(lstHissNo.get(6));
//				form1B.setPin8(lstHissNo.get(7));
//			}
//			form1BRepository.save(form1B);
//			DatabaseContextHolder.restoreCtx();
//			return MessageResponse.builder().status("SUCCESS").message(
//					messageSource.getMessage("error.form.savedata", null, locale)).build();	
//		}
//		throw new FieldValidationException("error.form.errorwhilesave");
//	}

	@Override 
	public void updateForm1B(Form1BDao form1BDao,HttpServletRequest request,Locale locale)
	{
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form1B form1B =new Form1B();
		if(form1BDao.getId()!=null) {
		form1B = form1BRepository.findById(form1BDao.getId())
				.orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form1B", form1BDao.getId() ));
		}
		BeanUtils.copyProperties(form1BDao, form1B);
		if(form1BDao.getHissaNo() != null) {
			String [] hissaNoo = form1BDao.getHissaNo().split("/");
			if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {

				String  tHissaNo = form1BDao.getHissaNo();
				for (int i = hissaNoo.length; i <= 8; i++) {
					tHissaNo += " /";
				}
				hissaNoo = tHissaNo.split("/");
			}
			List<String> lstHissNo = Arrays.asList(hissaNoo);
			form1B.setPin1(lstHissNo.get(0));
			form1B.setPin2(lstHissNo.get(1));
			form1B.setPin3(lstHissNo.get(2));
			form1B.setPin4(lstHissNo.get(3));
			form1B.setPin5(lstHissNo.get(4));
			form1B.setPin6(lstHissNo.get(5));
			form1B.setPin7(lstHissNo.get(6));
			form1B.setPin8(lstHissNo.get(7));
		}
		form1BRepository.save(form1B);
		DatabaseContextHolder.restoreCtx();
	}

	private Form1BDao toDao(Form1B form1B){
		Form1BDao form1BDao = new Form1BDao();
		BeanUtils.copyProperties(form1B, form1BDao);
		return form1BDao;
	}

}