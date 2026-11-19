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
import com.probity.echawdi.dao.Form3Dao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.entity.Form3;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form3Repository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.service.Form3Service;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

@Component
public class Form3ServiceImpl implements Form3Service{

	@Autowired
	private Form3Repository form3Repository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;

	@Override
	public Response getForm3cDataN(String cCode, String pin,HttpServletRequest request,Locale locale) {
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
		List<Object[]> lstForm3Data=new ArrayList<>();
		if(pin.isEmpty()) {
		lstForm3Data= form3Repository.getForm3DataN(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode,'Y');
		}else {
			lstForm3Data= form3Repository.getForm3DataNWithPin(
					request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
					request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
					cCode,pin,'Y');
		}
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(lstForm3Data)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}
		Response form3Response =new  Response();
		form3Response.setForm3Data(lstForm3Data.stream().map((object) -> {
			Form3Dao form3Dao = new Form3Dao();
			form3Dao.setClasses(object[0]== null ? "" : object[0].toString());
			form3Dao.setKindAndHowLongContinuable(object[1]== null ? "" : object[1].toString());
			if (object[2]!=null){
				form3Dao.setRunningNoEachClass(Long.valueOf(object[2]== null ? "" :object[2].toString()));
			}
			form3Dao.setPin(object[3] == null ? "" : object[3].toString());
			form3Dao.setHissaNo(object[4]== null ? "" :object[4].toString());
			form3Dao.setNetCultiAreaH(object[5]== null ? "" :object[5].toString());
			form3Dao.setAssessment(object[6]== null ? "" :object[6].toString());
			form3Dao.setNaAreaH(object[7]== null ? "" :object[7].toString());
			form3Dao.setNaAssessment(object[8]== null ? "" :object[8].toString());
			form3Dao.setSanadNo(object[9] == null ? "" : object[9].toString());
			form3Dao.setNoInRegisterOfLands(object[10]== null ? "" : object[10].toString());
			form3Dao.setJuniSalami(object[11]== null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(object[11].toString())));
			form3Dao.setRate(object[12] == null ? "" : object[12].toString());
			form3Dao.setEchAssessment(object[13] == null ? "" : object[13].toString());
			form3Dao.setTotal(object[14] == null ? "" : object[14].toString());
			form3Dao.setNetPending(object[15]== null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(object[15].toString())));
			form3Dao.setRemarks(object[16] == null ? "" :object[16].toString());
			if(object[17] !=null) {
				form3Dao.setId(Long.valueOf(object[17]== null ? "" :object[17].toString()));
			}
			form3Dao.setcCode(object[18]== null ? "" :object[18].toString());
			form3Dao.setPotKharabArea(object[19]== null ? "" :object[19].toString());
			form3Dao.setTotalArea(object[20]== null ? "" :object[20].toString());
			
			return form3Dao;
		}).sorted(Comparator.comparing(Form3Dao:: getIntPin)).collect(Collectors.toList()));

		Integer serialNo=form3Repository.getSerialNo(request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,form3Response.getForm3Data().get(0).getcCode());
		if(serialNo > 0) {
			form3Response.setSerialNoForForm3(Long.valueOf(++serialNo));
		}else {
			form3Response.setSerialNoForForm3(1L);
		}
		return form3Response;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}
	@Override 
	public void updateForm3(Form3Dao form3Dao,HttpServletRequest request,Locale locale)
	{
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form3 form3 =new Form3();
		if(form3Dao.getId()!=null) {
			form3 = form3Repository.findById(form3Dao.getId())
					.orElseThrow(() ->  new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form1E", form3Dao.getId()));
		}
		BeanUtils.copyProperties(form3Dao, form3,"id");
		final String cls=form3.getClasses().toString();
		form3.setKindAndHowLongContinuable(CommonConstants.types.get(cls));
		if(form3Dao.getHissaNo() != null) {
			String [] hissaNoo = form3Dao.getHissaNo().split("/");
			if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
				String  tHissaNo = form3Dao.getHissaNo();
				for (int i = hissaNoo.length; i <= 8; i++) {
					tHissaNo += " /";
				}
				hissaNoo = tHissaNo.split("/");
			}
			List<String> lstHissNo = Arrays.asList(hissaNoo);
			//System.out.println("lstHissNo--->"+lstHissNo);
			form3.setPin1(lstHissNo.get(0));
			form3.setPin2(lstHissNo.get(1));
			form3.setPin3(lstHissNo.get(2));
			form3.setPin4(lstHissNo.get(3));
			form3.setPin5(lstHissNo.get(4));
			form3.setPin6(lstHissNo.get(5));
			form3.setPin7(lstHissNo.get(6));
			form3.setPin8(lstHissNo.get(7));
		}
		form3Repository.save(form3);
		DatabaseContextHolder.restoreCtx();
	}

	@Override
	public void deleteForm3ById(long id,HttpServletRequest request,Locale locale){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form3 form3 =form3Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form3", id));
		form3.setActiveFlag('N');
		form3Repository.save(form3);
		DatabaseContextHolder.restoreCtx();
	}
	@Override
	public Response getForm3ReportDetails(String cCode,HttpServletRequest request,Locale locale) {
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
			
			/*
			 * List<Object[]> form3Report = form3Repository.getForm3DataN(
			 * request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader
			 * ("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
			 * request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader
			 * ("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
			 * cCode,'Y')
			 */;
			 List<Object[]> form3Report = form3Repository.getForm3DataReport(
						request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
						request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
						cCode,'Y');
			 
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(form3Report)) {
			throw new ResourceNotFoundException("error.form.reportdetails",messageSource,locale);
		
		}
		else {
			Response form3Response =new  Response();
			form3Response.setForm3Data(form3Report.stream().map((object) -> {
				Form3Dao form3Dao = new Form3Dao();
				form3Dao.setClasses(object[0]== null ? "" : object[0].toString());
				form3Dao.setKindAndHowLongContinuable(object[1]== null ? "" : object[1].toString());
			    form3Dao.setRunningNoEachClass(Long.valueOf(object[2]== null ? "" : object[2].toString()));
				form3Dao.setPin(object[3] == null ? "" : object[3].toString());
				form3Dao.setHissaNo(object[4] == null ? "" : object[4].toString());
				form3Dao.setNetCultiAreaH(object[5]== null ? "" : object[5].toString());
				form3Dao.setAssessment(object[6]== null ? "" : object[6].toString());
				form3Dao.setNaAreaH(object[7]== null ? "" : object[7].toString());
				form3Dao.setNaAssessment(object[8]== null ? "" : object[8].toString());
				form3Dao.setSanadNo(object[9] == null ? "" : object[9].toString());
				form3Dao.setNoInRegisterOfLands(object[10]== null ? "" : object[10].toString());
				form3Dao.setJuniSalami(object[11]== null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(object[11].toString())));
				form3Dao.setRate(object[12] == null ? "" : object[12].toString());
				form3Dao.setEchAssessment(object[13] == null ? "" : object[13].toString());
				form3Dao.setTotal(object[14] == null ? "" : object[14].toString());
				form3Dao.setNetPending(object[15]== null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(object[15].toString())));
				form3Dao.setRemarks(object[16] == null ? "" :object[16].toString());
				if(object[17] !=null) {
					form3Dao.setId(Long.valueOf(object[17] == null ? "" : object[17].toString()));
				}	
				form3Dao.setcCode(object[18] == null ? "" :object[18].toString());
				form3Dao.setPotKharabArea(object[19] == null ? "" :object[19].toString());
				form3Dao.setTotalArea(object[20] == null ? "" :object[20].toString());
				return form3Dao;
			}).sorted(Comparator.comparing(Form3Dao:: getIntPin, Comparator.reverseOrder())).collect(Collectors.toList()));
			return form3Response;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}

	}
	@Override
	public Response getForm3AbstractReportDetails(String cCode,HttpServletRequest request, Locale locale) {
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
		List<Object[]> abstractdtl=form3Repository.getReport3AbstractData(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode,'Y');
		DatabaseContextHolder.restoreCtx();

		if(Collections.isEmpty(abstractdtl)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}
		Response form3Response =new  Response();
		form3Response.setForm3Data(abstractdtl.stream().map((object) -> {
			Form3Dao form3Abstart = new Form3Dao();
			form3Abstart.setClasses(object[0]== null ? "" :object[0].toString());
			form3Abstart.setKindAndHowLongContinuable(object[1]== null ? "" :object[1].toString());
			form3Abstart.setNetCultiAreaH(object[2]== null ? "" : object[2].toString());
			form3Abstart.setNaAreaH(object[3]== null ? "" : object[3].toString());
			form3Abstart.setJuniSalami(object[4]==null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(object[5].toString())));
			form3Abstart.setNetPending(object[5]==null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(object[5].toString())));
			return form3Abstart;
		}).sorted(Comparator.comparing(Form3Dao:: getIntPin)).collect(Collectors.toList()));
		return form3Response;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	
	}
//	@Override
//	public MessageResponse save(Form3Dao form3Dao,HttpServletRequest request,Locale locale) {
//		if(form3Dao.getcCode() != null) {
//			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//			Form3 form3 = toEntity(form3Dao);
//			if(form3Dao.getHissaNo() != null) {
//				String [] hissaNoo = form3Dao.getHissaNo().split("/");
//				if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
//					String  tHissaNo = form3Dao.getHissaNo();
//					for (int i = hissaNoo.length; i <= 8; i++) {
//						tHissaNo += " /";
//					}
//					hissaNoo = tHissaNo.split("/");
//				}
//				List<String> lstHissNo = Arrays.asList(hissaNoo);
//				//System.out.println("lstHissNo--->"+lstHissNo);
//
//				form3.setPin1(lstHissNo.get(0));
//				form3.setPin2(lstHissNo.get(1));
//				form3.setPin3(lstHissNo.get(2));
//				form3.setPin4(lstHissNo.get(3));
//				form3.setPin5(lstHissNo.get(4));
//				form3.setPin6(lstHissNo.get(5));
//				form3.setPin7(lstHissNo.get(6));
//				form3.setPin8(lstHissNo.get(7));
//			}
//
//			Integer serialNo=getSerialNo(form3.getCCode(),request);
//			if(serialNo > 0) {
//				form3.setRunningNoEachClass(Long.valueOf(++serialNo));
//			}else {
//				form3.setRunningNoEachClass(1L);
//			}
//			DatabaseContextHolder.restoreCtx();
//
//			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//			form3Repository.save(form3);
//			DatabaseContextHolder.restoreCtx();
//			return MessageResponse.builder().status("SUCCESS").message(
//					messageSource.getMessage("error.form.savedata", null, locale)).build();	
//		}
//		throw new FieldValidationException("error.form.errorwhilesave");
//	}


//	public Integer getSerialNo(String cCode,HttpServletRequest request) {
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//		Integer serialNoo=form3Repository.getSerialNo(
//				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
//				cCode
//				);
//		DatabaseContextHolder.restoreCtx();
//		return serialNoo;
//	}
//
//	private Form3 toEntity(Form3Dao form3Dao) {
//		Form3 form3 = new Form3();
//		BeanUtils.copyProperties(form3Dao, form3);
//		return form3;
//
//	}
//	@Override
//	public List<Form3Dao> getForm3EferfarDetials(String cCode, String pin, String hissaNo,HttpServletRequest request,Locale locale) {
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//		List<Object[]> form3Dtl = form3Repository.getForm3EferfarDetials(
//				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//				cCode,pin,hissaNo
//				);
//		DatabaseContextHolder.restoreCtx();
//		if(Collections.isEmpty(form3Dtl)) {
//			throw new ResourceNotFoundException("error.form.eferfar3",messageSource,locale);
//		}
//		else {
//			List<Form3Dao> lstForm3 = form3Dtl.stream().map((object) -> {
//
//				Form3Dao form3Dao=new Form3Dao();
//				form3Dao.setcCode(object[0].toString());
//				form3Dao.setPin(object[1].toString());
//				form3Dao.setNaAreaH(object[2].toString());
//				form3Dao.setHissaNo(object[3].toString());
//				form3Dao.setNaAssessment(object[4].toString());
//				form3Dao.setNetCultiAreaH(object[5].toString());
//				form3Dao.setAssessment(object[6].toString());
//
//				return form3Dao;
//			}). collect(Collectors.toList());
//			return lstForm3;
//		}
//	}	
}