/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.sql.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.Form1Dao;
import com.probity.echawdi.dao.Form7Dao;
import com.probity.echawdi.dao.Form7KhataDao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form7Repository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.service.Form7Service;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Bhagyashri Lolage 
 *
 */


@Component
public class Form7ServiceImpl implements Form7Service{

	@Autowired
	private Form7Repository form7Repository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;

	@Override
	public List<Form7Dao> findHissaNoByVillageAndSurveyNo(HttpServletRequest request,String cCode,String pin,Character marked,Locale locale){
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
		List<Object[]> lstHissanNoDtl =form7Repository.findHissaNo(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),
				CommonConstants.mhrorUser,CommonConstants.mhrorPassword,cCode, pin, marked);
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(lstHissanNoDtl)) {
			throw new ResourceNotFoundException("error.form.villageandsurveyno",messageSource,locale);
		}else {
			if(lstHissanNoDtl != null && lstHissanNoDtl.size()>0) {
				List<Form7Dao> lstHissaNo = lstHissanNoDtl.stream().map( (object) -> {
					Form7Dao form7Dao = new Form7Dao();
					form7Dao.setPin(object[0].toString());
					form7Dao.setHissaNo(object[1].toString());
					return form7Dao;
				}).collect(Collectors.toList());
				return lstHissaNo;
			}
			return null;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}

	@Override
	public List<Form7Dao> getForm1Details(HttpServletRequest request,String cCode,String pin,String hissaNo,Locale locale){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<Object[]> form1Dtl = form7Repository.getForm1(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),
				CommonConstants.mhrorUser,CommonConstants.mhrorPassword,cCode,pin,hissaNo);
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(form1Dtl)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}
		else {
			if(form1Dtl!=null) {
				List<Form7Dao> lstForm7 = form1Dtl.stream().map((object) -> {
					Form7Dao form7Dao=new Form7Dao();
					form7Dao.setCCode(object[0].toString());
					form7Dao.setPin(object[1].toString());
					form7Dao.setAssessment(object[2].toString());
					form7Dao.setPin1(object[3].toString());
					form7Dao.setNetCultiAreaH(object[4].toString());
					form7Dao.setTotalAreaH(object[5].toString());
					form7Dao.setNaAssessment(object[6].toString());
					form7Dao.setPotkharabaAH(object[7].toString());
					form7Dao.setPotkharabaBH(object[8].toString());
					form7Dao.setHissaNo(object[9].toString());
					form7Dao.setTenureName(object[10].toString());
					form7Dao.setKhataNo(object[11].toString());
					return form7Dao;
				}). collect(Collectors.toList());
				return lstForm7;
			}
			return null;
		}
	}

	@Override
	public List<Form7Dao> getForm1AArea(HttpServletRequest request,String cCode, String pin,String hissaNo,Locale locale) {
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
		List<Object[]> form1ADtl = form7Repository.getForm1AArea(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),
																  CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
																  cCode,pin,hissaNo
																);
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(form1ADtl)) {
			throw new ResourceNotFoundException("error.form.area1a",messageSource,locale);
		}else {
			List<Form7Dao> lstForm7 = form1ADtl.stream().map((object) -> {
				Form7Dao form7Dao=new Form7Dao();
				form7Dao.setCCode(object[0].toString());
				form7Dao.setPin(object[1].toString());
				form7Dao.setPin1(object[2].toString());
				form7Dao.setPin2(object[3].toString());
				form7Dao.setPin3(object[4].toString());
				form7Dao.setPin4(object[5].toString());
				form7Dao.setPin5(object[6].toString());
				form7Dao.setPin6(object[7].toString());
				form7Dao.setPin7(object[8].toString());
				form7Dao.setPin8(object[9].toString());
				form7Dao.setTotalAreaH(object[10].toString());
				form7Dao.setHissaNo(object[11].toString());
				form7Dao.setTotalAreaR(object[12].toString());
				form7Dao.setTotalAreaSqm(object[13].toString());
				return form7Dao;
			}). collect(Collectors.toList());
			return lstForm7;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}
	
	@Override
	public Response getAllForm6AReportDetails(String cCode,HttpServletRequest request, Locale locale) {
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
		List<Object[]> form6AReport = form7Repository.getAllForm6AReportDetails(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				cCode);
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(form6AReport)) {
			throw new ResourceNotFoundException("error.form.reportdetails",messageSource,locale);
		}
		else {
			Response form1Response =new  Response();
			form1Response.setForm1Data(form6AReport.stream().map((object) -> {
				Form1Dao form1Dao = new Form1Dao();
				form1Dao.setMutNo(object[0] == null ? "" : object[0].toString());
				form1Dao.setPin(object[1] == null ? "" : object[1].toString() );
				form1Dao.setObjectionDate(Date.valueOf(object[2].toString()) );
				form1Dao.setFirstName(object[3] == null ? "" : object[3].toString() );
				form1Dao.setMiddelName(object[4] == null ? "" : object[4].toString() );
				form1Dao.setLastName(object[5] == null ? "" : object[5].toString() );
				form1Dao.setObjectionRemark(object[6] == null ? "" : object[6].toString() );
				form1Dao.setObjectionRemark(object[7] == null ? "" : object[7].toString() );
				form1Dao.setHissaNo(object[8] == null ? "" : object[8].toString() );
				return form1Dao;
			}).collect(Collectors.toList()));
			return form1Response;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}
	
	@Override
	public Form7KhataDao getKhataOwnerName(HttpServletRequest request,String khataNo,String cCode,Locale locale){
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
			Object obj =form7Repository.findByKhataNo(
				CommonConstants.AugDatamhrHost,request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				cCode,khataNo,CommonConstants.AugDataPort);
		DatabaseContextHolder.restoreCtx();
		if(obj == null) {
			throw new ResourceNotFoundException("error.form.ownername",messageSource,locale);
		}else {
			Form7KhataDao form7KhataDao = new Form7KhataDao();
			form7KhataDao.setKhataOwnerName(obj.toString());
			return form7KhataDao;
		}
	}else {
		throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
	}
}
}