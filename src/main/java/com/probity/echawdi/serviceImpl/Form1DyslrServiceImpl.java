/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.Form1AbstractDyslrDao;
import com.probity.echawdi.dao.Form1Dao;
import com.probity.echawdi.dao.Form1DyslrDao;
import com.probity.echawdi.entity.Form1Abstract_dyslr;
import com.probity.echawdi.entity.Form1Dyslr;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form1AbstractDyslrRepository;
import com.probity.echawdi.repository.Form1AbstractRepository;
import com.probity.echawdi.repository.Form1DyslrRepository;
import com.probity.echawdi.service.Form1DyslrService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;




@Component
public class  Form1DyslrServiceImpl implements Form1DyslrService{
	
	@Autowired
	private Form1DyslrRepository form1DyslrRepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private Form1AbstractRepository form1AbstractRepository;
	@Autowired
	private Form1AbstractDyslrRepository form1AbstractDyslrRepository;
	
	
	@Override 
	public void updateForm1(@Valid Form1DyslrDao form1DyslrDao,HttpServletRequest request,Locale locale) {
		Form1Dyslr formDyslr=new Form1Dyslr();
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		BeanUtils.copyProperties(form1DyslrDao, formDyslr);
		if(form1DyslrDao.getHissaNo() != null) {
			String [] hissaNoo = form1DyslrDao.getHissaNo().split("/");
			if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
				String  tHissaNo = form1DyslrDao.getHissaNo();
				for (int i = hissaNoo.length; i <= 8; i++) {
					tHissaNo += " /";
				}
				hissaNoo = tHissaNo.split("/");
			}
			List<String> lstHissNo = Arrays.asList(hissaNoo);
			//System.out.println("lstHissNo--->"+lstHissNo);
			formDyslr.setPin1(lstHissNo.get(0).trim()==null ? lstHissNo.get(0):  lstHissNo.get(0).trim());
			formDyslr.setPin2(lstHissNo.get(1).trim()==null ? lstHissNo.get(1):  lstHissNo.get(1).trim());
			formDyslr.setPin3(lstHissNo.get(2).trim()==null ? lstHissNo.get(2):  lstHissNo.get(2).trim());
			formDyslr.setPin4(lstHissNo.get(3).trim()==null ? lstHissNo.get(3):  lstHissNo.get(3).trim());
			formDyslr.setPin5(lstHissNo.get(4).trim()==null ? lstHissNo.get(4):  lstHissNo.get(4).trim());
			formDyslr.setPin6(lstHissNo.get(5).trim()==null ? lstHissNo.get(5):  lstHissNo.get(5).trim());
			formDyslr.setPin7(lstHissNo.get(6).trim()==null ? lstHissNo.get(6):  lstHissNo.get(6).trim());
			formDyslr.setPin8(lstHissNo.get(7).trim()==null ? lstHissNo.get(7):  lstHissNo.get(7).trim());
		}
		form1DyslrRepository.save(formDyslr);
		DatabaseContextHolder.restoreCtx();
		
	}

	@Override 
	public void saveForm1Dyslr(@Valid Form1DyslrDao form1DyslrDao,HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form1Dyslr formDyslr=new Form1Dyslr();
		BeanUtils.copyProperties(form1DyslrDao, formDyslr);
		if(form1DyslrDao.getPin() != null) {
			String [] hissaNoo = form1DyslrDao.getPin().split("/");
			if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 9) {
				String  tHissaNo = form1DyslrDao.getPin();
				for (int i = hissaNoo.length; i <= 9; i++) {
					tHissaNo += " /";
				}
				hissaNoo = tHissaNo.split("/");
			}
			List<String> lstHissNo = Arrays.asList(hissaNoo);
			//System.out.println("lstHissNo--->"+lstHissNo);
			formDyslr.setPin(lstHissNo.get(0).trim()==null ? lstHissNo.get(0):  lstHissNo.get(0).trim());
			formDyslr.setPin1(lstHissNo.get(1).trim()==null ? lstHissNo.get(1):  lstHissNo.get(1).trim());
			formDyslr.setPin2(lstHissNo.get(2).trim()==null ? lstHissNo.get(2):  lstHissNo.get(2).trim());
			formDyslr.setPin3(lstHissNo.get(3).trim()==null ? lstHissNo.get(3):  lstHissNo.get(3).trim());
			formDyslr.setPin4(lstHissNo.get(4).trim()==null ? lstHissNo.get(4):  lstHissNo.get(4).trim());
			formDyslr.setPin5(lstHissNo.get(5).trim()==null ? lstHissNo.get(5):  lstHissNo.get(5).trim());
			formDyslr.setPin6(lstHissNo.get(6).trim()==null ? lstHissNo.get(6):  lstHissNo.get(6).trim());
			formDyslr.setPin7(lstHissNo.get(7).trim()==null ? lstHissNo.get(7):  lstHissNo.get(7).trim());
			formDyslr.setPin8(lstHissNo.get(8).trim()==null ? lstHissNo.get(8):  lstHissNo.get(8).trim());
		}
		
		formDyslr.setHissaNo(formDyslr.getPin1()+"/"+formDyslr.getPin2()+"/"+formDyslr.getPin3()+"/"+formDyslr.getPin4()+"/"+formDyslr.getPin5()
		+"/"+formDyslr.getPin6()+"/"+formDyslr.getPin7()+"/"+formDyslr.getPin8());
		
		formDyslr.setHissaNo(formDyslr.getHissaNo().replaceAll("//", ""));
		
		form1DyslrRepository.save(formDyslr);
		DatabaseContextHolder.restoreCtx();
		
	}

	
	
	@Override
	public void deleteForm1ById(Form1DyslrDao form1DyslrDao,Locale locale,HttpServletRequest request){
		Form1Dyslr formDyslr=new Form1Dyslr();
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		BeanUtils.copyProperties(form1DyslrDao, formDyslr);
		if(form1DyslrDao.getHissaNo() != null) {
			String [] hissaNoo = form1DyslrDao.getHissaNo().split("/");
			if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
				String  tHissaNo = form1DyslrDao.getHissaNo();
				for (int i = hissaNoo.length; i <= 8; i++) {
					tHissaNo += " /";
				}
				hissaNoo = tHissaNo.split("/");
			}
			List<String> lstHissNo = Arrays.asList(hissaNoo);
			//System.out.println("lstHissNo--->"+lstHissNo);
			formDyslr.setPin1(lstHissNo.get(0).trim()==null ? lstHissNo.get(0):  lstHissNo.get(0).trim());
			formDyslr.setPin2(lstHissNo.get(1).trim()==null ? lstHissNo.get(1):  lstHissNo.get(1).trim());
			formDyslr.setPin3(lstHissNo.get(2).trim()==null ? lstHissNo.get(2):  lstHissNo.get(2).trim());
			formDyslr.setPin4(lstHissNo.get(3).trim()==null ? lstHissNo.get(3):  lstHissNo.get(3).trim());
			formDyslr.setPin5(lstHissNo.get(4).trim()==null ? lstHissNo.get(4):  lstHissNo.get(4).trim());
			formDyslr.setPin6(lstHissNo.get(5).trim()==null ? lstHissNo.get(5):  lstHissNo.get(5).trim());
			formDyslr.setPin7(lstHissNo.get(6).trim()==null ? lstHissNo.get(6):  lstHissNo.get(6).trim());
			formDyslr.setPin8(lstHissNo.get(7).trim()==null ? lstHissNo.get(7):  lstHissNo.get(7).trim());
		}
		formDyslr.setActiveFlag('N');
		form1DyslrRepository.save(formDyslr);
		DatabaseContextHolder.restoreCtx();
	}

	
	private Form1DyslrDao toDao(Form1Dyslr formDyslr){
		Form1DyslrDao form1DyslrDao = new Form1DyslrDao();
		BeanUtils.copyProperties(formDyslr, form1DyslrDao);
		return form1DyslrDao;
	}


	@Override
	public Response getForm1DyslrReportDetails(Integer districtCode, Integer talukaCode, String cCode, Locale locale,
			HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		
		List<Object[]> form1ReportDyslr = form1DyslrRepository.findByCcode(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode);
		DatabaseContextHolder.restoreCtx();
		if(form1ReportDyslr.isEmpty()) {
			throw new ResourceNotFoundException("error.form.reportdetails",messageSource,locale);
		}
		else {
			List<Form1Dao> form1Daos;
			form1Daos=form1ReportDyslr.stream().map((object) -> {
				Form1Dao form1Dao = new Form1Dao();
				form1Dao.setPin(object[0].toString());
				form1Dao.setTenureName(object[1]==null ? "" : object[1].toString());
				form1Dao.setAssessment(object[2]==null ? "" : object[2].toString());
				form1Dao.setNetCultiAreaH(object[3]==null ? "" : object[3].toString());
				form1Dao.setTotalAreaH(object[4]==null ? "" : object[4].toString());
				form1Dao.setNaAssessment(object[5]==null ? "" : object[5].toString());
				form1Dao.setPotkharabaAH(object[6]==null ? "" : object[6].toString());
				form1Dao.setPotkharabaBH(object[7]==null ? "" : object[7].toString());
				form1Dao.setPublicRightsOfWayAndEasements(object[8]==null ? "" : object[8].toString());
				form1Dao.setParticularsOfAlteration(object[9]==null ? "" :object[9].toString());
				form1Dao.setDesignation(object[10]==null ? "" : object[10].toString());
				form1Dao.setRemarks(object[11]==null ? "" : object[11].toString());
				form1Dao.setOrderNo(object[12]==null ? "" : object[12].toString());
				if(object[13] !=null) {
					form1Dao.setOrderDate(LocalDate.parse(object[13].toString()));
				}
				form1Dao.setHissaNo(object[14]==null ? "" :object[14].toString());
				return form1Dao;
			}).collect(Collectors.toList());
			Response form1Response =new  Response();
			form1Response.setForm1Data(form1Daos.stream().sorted(Comparator.comparing(Form1Dao:: getIntPin)).collect(Collectors.toList()));
			return form1Response;
		}
	}
	
	
	@Override
	public Response getFormDyslrSaveEntries( String cCode,HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<Character> flags = Arrays.asList('Y', 'E');
		List<Form1Dyslr> form1Dyslr = form1DyslrRepository.findAllBycCodeAndActiveFlagIn(cCode, flags);
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(form1Dyslr)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}
		Response form1DyslrResponse =new  Response();
		form1DyslrResponse.setForm1DyslrData(form1Dyslr.stream().map(dyslr -> toDao(dyslr)).collect(Collectors.toList()));
      	return form1DyslrResponse;	
	}
	
	
	@Override
	public Response getFormDyslrDeleteEntries( String cCode,HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<Form1Dyslr> form1Dyslr = form1DyslrRepository.findAllBycCodeAndActiveFlag(cCode,'N');
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(form1Dyslr)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}
		Response form1DyslrResponse =new  Response();
		form1DyslrResponse.setForm1DyslrData(form1Dyslr.stream().map(dyslr -> toDao(dyslr)).collect(Collectors.toList()));
      	return form1DyslrResponse;	
	}
	
	

//	changes For Akarbannd
	
	@Override
	public Response getAllForm1DyslrData(Integer districtCode, Integer talukaCode, String cCode, String pin,
			Locale locale, HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		List<Object[]> listOfForm1 = new ArrayList<>();
		if (pin.isEmpty()) {
			listOfForm1 = form1DyslrRepository.getDylsrAkarbandEntries(
					request.getHeader("echHost"), request.getHeader("echDbName"), request.getHeader("echSchemaName"),
					CommonConstants.echUser, CommonConstants.echPassword, cCode, 'Y', CommonConstants.DefaultDataPort);
		

		}
		else {
			listOfForm1 = form1DyslrRepository.getDylsrAkarbandEntriesWithPin( request.getHeader("echHost"), request.getHeader("echDbName"),
					request.getHeader("echSchemaName"), CommonConstants.echUser, CommonConstants.echPassword, cCode,
					pin, 'Y', CommonConstants.DefaultDataPort);
		}
		DatabaseContextHolder.restoreCtx();
		if (listOfForm1.isEmpty()) {
			throw new ResourceNotFoundException("error.form.getData", messageSource, locale);
		}
		Response form1Response = new Response();
		form1Response.setForm1DyslrData(listOfForm1.stream().map((object) -> {
			Form1DyslrDao form1Dao = new Form1DyslrDao();
			form1Dao.setAssessment(object[0] == null ? "" : object[0].toString());
			form1Dao.setCultivableAreaInt(object[1] == null ? "" :object[1].toString());
			form1Dao.setNetCultiAreaH(object[2] == null ? "" : object[2].toString());
			form1Dao.setPotkharabaType(object[3] == null ? "" : object[3].toString());
			form1Dao.setTenureName(object[4] == null ? "" : object[4].toString());
			form1Dao.setTotalAreaH(object[5] == null ? "" : object[5].toString());
			form1Dao.setPublicRightsOfWayAndEasements(object[6] == null ? "" : object[6].toString());
			form1Dao.setParticularsOfAlteration(object[7] == null ? "" : object[7].toString());
			if (object[8] != null) {
				form1Dao.setOrderDate(LocalDate.parse(object[8].toString()));
			}
			form1Dao.setRemarks(object[9] == null ? "" : object[9].toString());
			form1Dao.setJirayatArea(object[10] == null ? "" : object[10].toString());
			form1Dao.setBagayatArea(object[11] == null ? "" : object[11].toString());
			form1Dao.setTariArea(object[12] == null ? "" : object[12].toString());
			form1Dao.setOtherArea(object[13] == null ? "" : object[13].toString());
			form1Dao.setJirayatAssessment(object[14] == null ? "" : object[14].toString());
			form1Dao.setBagayatAssessment(object[15] == null ? "" : object[15].toString());
			form1Dao.setTariAssessment(object[16] == null ? "" : object[16].toString());
			form1Dao.setOtherAssessment(object[17] == null ? "" : object[17].toString());
			form1Dao.setPin(object[18] == null ? "" : object[18].toString());
//			form1Dao.setOrderNo(object[12] == null ? "" : object[12].toString());
			form1Dao.setHissaNo(object[19] == null ? "" : object[19].toString());
			if (object[20] != null) {
				form1Dao.setId(Long.valueOf(object[20].toString()));
			}

			return form1Dao;
		}).sorted(Comparator.comparing(Form1DyslrDao::getIntPin)).collect(Collectors.toList()));
		return form1Response;
	}
	
	
	@Override
	public void updateForm1Dyslr(@Valid Form1DyslrDao form1DyslrDao, HttpServletRequest request, Locale locale) {
		Form1Dyslr formDyslr = new Form1Dyslr();
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
		if(form1DyslrDao.getId()!=null) {
			formDyslr = form1DyslrRepository.findById(form1DyslrDao.getId()).
					orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form1Dyslr", form1DyslrDao.getId()));
		}
          System.out.println(form1DyslrDao.getId()+"------------------------------------------------");
		BeanUtils.copyProperties(form1DyslrDao, formDyslr);	
		if (form1DyslrDao.getHissaNo() != null) {
			String[] hissaNoo = form1DyslrDao.getHissaNo().split("/");
			if (hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
				String tHissaNo = form1DyslrDao.getHissaNo();
				for (int i = hissaNoo.length; i <= 8; i++) {
					tHissaNo += " /";
				}
				hissaNoo = tHissaNo.split("/");
			}
			List<String> lstHissNo = Arrays.asList(hissaNoo);
			// System.out.println("lstHissNo--->"+lstHissNo);
			formDyslr.setPin1(lstHissNo.get(0).trim() == null ? lstHissNo.get(0) : lstHissNo.get(0).trim());
			formDyslr.setPin2(lstHissNo.get(1).trim() == null ? lstHissNo.get(1) : lstHissNo.get(1).trim());
			formDyslr.setPin3(lstHissNo.get(2).trim() == null ? lstHissNo.get(2) : lstHissNo.get(2).trim());
			formDyslr.setPin4(lstHissNo.get(3).trim() == null ? lstHissNo.get(3) : lstHissNo.get(3).trim());
			formDyslr.setPin5(lstHissNo.get(4).trim() == null ? lstHissNo.get(4) : lstHissNo.get(4).trim());
			formDyslr.setPin6(lstHissNo.get(5).trim() == null ? lstHissNo.get(5) : lstHissNo.get(5).trim());
			formDyslr.setPin7(lstHissNo.get(6).trim() == null ? lstHissNo.get(6) : lstHissNo.get(6).trim());
			formDyslr.setPin8(lstHissNo.get(7).trim() == null ? lstHissNo.get(7) : lstHissNo.get(7).trim());
		}
		formDyslr.setActiveFlag('E');
		form1DyslrRepository.save(formDyslr);
		DatabaseContextHolder.restoreCtx();

	}
	

@Override
	public List<Form1AbstractDyslrDao> getDyslrForm1FooterDetails(Integer districtCode, Integer talukaCode,
			String cCode, Locale locale, HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<Object[]> form1Abstract=form1DyslrRepository.getDyslrForm1Abstarctdetails(request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName")
				,CommonConstants.echUser,CommonConstants.echPassword,cCode);
		DatabaseContextHolder.restoreCtx();
		List<Form1AbstractDyslrDao> form1AbstractDaos= form1Abstract.stream().map((obj)->{
			Form1AbstractDyslrDao form1AbstractDao=new Form1AbstractDyslrDao();
			form1AbstractDao.setCCode(obj[0].toString()==null ? "" : obj[0].toString());
			form1AbstractDao.setRiversNalas(obj[1]== null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(obj[1].toString())));
			form1AbstractDao.setNalas(obj[2]== null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(obj[2].toString())));
			form1AbstractDao.setVillageSite(obj[3]== null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(obj[3].toString())));
			form1AbstractDao.setRoadsAndPath(obj[4]== null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(obj[4].toString())));
			return form1AbstractDao;
		}).collect(Collectors.toList());
		return form1AbstractDaos;

	}

@Override
public Form1AbstractDyslrDao getForm1AbstractDyslrReport(Integer districtCode,Integer talukaCode,String cCode,Locale locale,HttpServletRequest request) {
	dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));

	/*List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
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
			}}*/
	
	if(cCode.equals(cCode)) {	
	List<Form1Abstract_dyslr> formAbstractdyslrLst= new ArrayList<>();
	List<Object[]> liveDataLst= new ArrayList<>();
	
	System.out.println("I am here above the form1AbstractQueryDyslr-----------------: ");
	
	liveDataLst=form1AbstractRepository.fetchLiveData(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
			request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
			cCode) ;
	Form1Abstract_dyslr form1AbstractRdyslr = form1AbstractDyslrRepository.findAllBycCodeAndActiveFlag(cCode, 'Y');
	
	System.out.println("formAbstractObject printed here--------------------- : "+form1AbstractRdyslr);
	
	DatabaseContextHolder.restoreCtx();
	if(!liveDataLst.isEmpty()) {
		formAbstractdyslrLst=liveDataLst.stream().map((liveData)->{
			Form1Abstract_dyslr form1Abstractdyslr=new Form1Abstract_dyslr();	
			form1Abstractdyslr.setcCode(liveData[0].toString());          
			form1Abstractdyslr.setTenure1Area(BigDecimal.valueOf(Double.valueOf(liveData[1]==null ? "0": liveData[1].toString())));
			form1Abstractdyslr.setTenure1Assessment(BigDecimal.valueOf(Double.valueOf(liveData[2]==null ? "0": liveData[2].toString())));
			form1Abstractdyslr.setTenure2Area(BigDecimal.valueOf(Double.valueOf(liveData[3]==null ? "0": liveData[3].toString())));
			form1Abstractdyslr.setTenure2Assessment(BigDecimal.valueOf(Double.valueOf(liveData[4]==null ? "0": liveData[4].toString())));
			form1Abstractdyslr.setTenure3Area(BigDecimal.valueOf(Double.valueOf(liveData[5]==null ? "0": liveData[5].toString())));
			form1Abstractdyslr.setTenure3Assessment(BigDecimal.valueOf(Double.valueOf(liveData[6]==null ? "0": liveData[6].toString())));
			form1Abstractdyslr.setTenure4Area(BigDecimal.valueOf(Double.valueOf(liveData[7]==null ? "0": liveData[7].toString())));
			form1Abstractdyslr.setTenure4Assessment(BigDecimal.valueOf(Double.valueOf(liveData[8]==null ? "0": liveData[8].toString())));
			form1Abstractdyslr.setPotKharabArea(BigDecimal.valueOf(Double.valueOf(liveData[9]==null ? "0": liveData[9].toString())));
			form1Abstractdyslr.setPotKharabAssessment(BigDecimal.valueOf(Double.valueOf(liveData[10]==null ? "0": liveData[10].toString())));
			form1Abstractdyslr.setRiverAreaH(BigDecimal.valueOf(Double.valueOf(liveData[11]==null ? "0": liveData[11].toString())));
			form1Abstractdyslr.setNalaAreaH(BigDecimal.valueOf(Double.valueOf(liveData[12]==null ? "0": liveData[12].toString())));
			form1Abstractdyslr.setForm3NetCultiArea(BigDecimal.valueOf(Double.valueOf(liveData[13]==null ? "0": liveData[13].toString())));
			form1Abstractdyslr.setForm3Assessment(BigDecimal.valueOf(Double.valueOf(liveData[14]==null ? "0": liveData[14].toString())));
			form1Abstractdyslr.setUnOccupied(BigDecimal.valueOf(Double.valueOf(liveData[15]==null ? "0": liveData[15].toString())));
			
			
			return form1Abstractdyslr;
		}).collect(Collectors.toList());
		
		System.out.println("I am here before before  the if(form1AbstractRdyslr!=null) condition ------------ : ");

		if(form1AbstractRdyslr!=null){
			
			System.out.println("I am here into the if(form1AbstractRdyslr!=null) condition ------------ : ");
			
			BeanUtils.copyProperties(form1AbstractRdyslr,formAbstractdyslrLst.get(0),"tenure1Area","tenure1Assessment","tenure2Area","tenure2Assessment","tenure3Area","tenure3Assessment","tenure4Area","tenure4Assessment",
					"potKharabArea","potKharabAssessment","riverAreaH","nalaAreaH","form3NetCultiArea","form3Assessment","unOccupied");
		}
		return toDao(formAbstractdyslrLst.get(0));

	}else {
		throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
	}}else {
		throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
	}
}
public Form1AbstractDyslrDao toDao(Form1Abstract_dyslr form1AbstractDyslr) {
	Form1AbstractDyslrDao form1AbstractDyslrDao=new Form1AbstractDyslrDao();
		BeanUtils.copyProperties(form1AbstractDyslr, form1AbstractDyslrDao);
	return form1AbstractDyslrDao;
}
	
	public Form1Abstract_dyslr toEntiy(Form1AbstractDyslrDao form1AbstractDyslrDao) {
		Form1Abstract_dyslr form1AbstractDyslr=new Form1Abstract_dyslr();
		BeanUtils.copyProperties(form1AbstractDyslrDao, form1AbstractDyslr);
	return form1AbstractDyslr;
	}

	@Override
	public Form1DyslrDao getNewForm1DyslrReport(Integer districtCode, Integer talukaCode, String cCode, Locale locale,
	        HttpServletRequest request) {

	    dataSourceKeeper.setDataSourceEchwadi(
	            request.getHeader("echDbName"),
	            request.getHeader("echSchemaName")
	    );

	    List<Object[]> result = form1DyslrRepository.fetchLiveData2(
	            request.getHeader("echHost"),
	            request.getHeader("echDbName"),
	            request.getHeader("echSchemaName"),
	            CommonConstants.echUser,
	            CommonConstants.echPassword,
	            cCode ,districtCode,talukaCode
	    );
	    for (Object[] row : result) {

//	        System.out.println("Tenure = " + row[0] == null ? "" : row[0].toString().trim());
//	        System.out.println("Total Area = " + row[1] == null
//	                ? BigDecimal.ZERO
//	                : new BigDecimal(row[1].toString()));
	    	
	    	
	    	System.out.println("Row = " + Arrays.toString(row));

	    	System.out.println("row[0] = [" + row[0] + "]");
	    	System.out.println("row[1] = [" + row[1] + "]");

	    	System.out.println("row[0] type = " +
	    	        (row[0] == null ? "null" : row[0].getClass().getName()));

	    	System.out.println("row[1] type = " +
	    	        (row[1] == null ? "null" : row[1].getClass().getName()));

	    	
	    	System.out.println("row[2] type = " +
	    	        (row[2] == null ? "null" : row[2].getClass().getName()));

//	        BigDecimal value = convertArea(row[1] == null ? null : row[1].toString());
//
//	        System.out.println("Converted Value = " + value);
	    }
	    
	    Form1DyslrDao dao = new Form1DyslrDao();

	    Form1AbstractDyslrDao area = new Form1AbstractDyslrDao();

	    for(Object[] row : result){

	       // String tenure = row[0].toString();
	        //new line for an error in UAT  
	        String tenure = row[0] == null ? "" : row[0].toString().trim();
	      
//	        BigDecimal value = row[1] == null
//	                ? BigDecimal.ZERO
//	                : new BigDecimal(row[1].toString());
	        
	        String value = row[1] == null ? "" : row[1].toString().trim();
	        
	        		//  convertArea(row[1].toString());
	        		  
	        		  
	        BigDecimal assessmentValue = row[2] == null
	                ? BigDecimal.ZERO
	                : new BigDecimal(row[2].toString());
	        
	        


	        switch (tenure.trim()) {

	        case "1":
	        case "भोगवटादार वर्ग -1":
	            area.setTenure1Areaa(value);
	            area.setTenure1Assessment(assessmentValue);
	            break;

	        case "2":
	        case "भोगवटादार वर्ग - 2":
	            area.setTenure2Areaa(value);
	            area.setTenure2Assessment(assessmentValue);
	            break;

	        case "3":
	        case "सरकार":
	            area.setTenure3Areaa(value);
	            area.setTenure3Assessment(assessmentValue);
	            break;

	        case "4":
	        case "सरकार पट्टेदार":
	            area.setTenure4Areaa(value);
	            area.setTenure4Assessment(assessmentValue);
	            break;

//	        case "5":
//	        case "सरकार":
//	            area.setCantonmentLandMilitaryCamp(value);
//	            area.setCantonmentLandMilitaryCamp(assessmentValue);
//
//	            
//	            break;
	    }
	    }

	    List<Form1AbstractDyslrDao> areaSummaryList = new ArrayList<>();
	    areaSummaryList.add(area);

	    dao.setAreaSummaryList(areaSummaryList);
	    return dao;

	    }


	private BigDecimal convertArea(String area) {

	    if (area == null || area.trim().isEmpty()) {
	        return BigDecimal.ZERO;
	    }

	    String[] parts = area.split("\\.");

	    BigDecimal hectare = parts.length > 0 
	            ? new BigDecimal(parts[0]) 
	            : BigDecimal.ZERO;

	    BigDecimal are = parts.length > 1 
	            ? new BigDecimal(parts[1]) 
	            : BigDecimal.ZERO;

	    BigDecimal sqm = parts.length > 2 
	            ? new BigDecimal(parts[2]) 
	            : BigDecimal.ZERO;

	    return hectare
	            .add(are.divide(BigDecimal.valueOf(100)))
	            .add(sqm.divide(BigDecimal.valueOf(10000)));
	}
	
	public List<Form1AbstractDyslrDao> getNewForm1DyslrReportM(Integer districtCode, Integer talukaCode, Locale locale,
	        HttpServletRequest request) {
		
		
		System.out.println();

	    dataSourceKeeper.setDataSourceEchwadi(
	            request.getHeader("echDbName"),
	            request.getHeader("echSchemaName")
	    );

	    List<Object[]> result = form1DyslrRepository.fetchLiveData2Mis(request.getHeader("echHost"),
	            request.getHeader("echDbName"),
	            request.getHeader("echSchemaName"),
	            CommonConstants.echUser,
	            CommonConstants.echPassword, districtCode, talukaCode);

	    Map<String, Form1AbstractDyslrDao> ccodeMap = new LinkedHashMap<>();

	    for (Object[] row : result) {

	        String ccode = row[0] == null ? "" : row[0].toString().trim();
	        String tenure = row[1] == null ? "" : row[1].toString().trim();

//	        BigDecimal value = row[2] == null
//	                ? BigDecimal.ZERO
//	                : parseHectareAreaSqm(row[2].toString());
//	        
	        String value = row[2] == null ? "" : row[2].toString().trim();

	        BigDecimal assessmentValue = row[3] == null
	                ? BigDecimal.ZERO
	                : new BigDecimal(row[3].toString());

	        Form1AbstractDyslrDao area = ccodeMap.get(ccode);
	        if (area == null) {
	            area = new Form1AbstractDyslrDao();
	            area.setCCode(ccode);
	            area.setDistrictCode(districtCode);
	            area.setTalukaCode(talukaCode);
	            ccodeMap.put(ccode, area);
	        }

	        switch (tenure) {

	            case "1":
	            case "भोगवटादार वर्ग -1":
	                area.setTenure1Areaa(value);
	                area.setTenure1Assessment(assessmentValue);
	                break;

	            case "2":
	            case "भोगवटादार वर्ग - 2":
	            case "भोगवटादार वर्ग -2":
	                area.setTenure2Areaa(value);
	                area.setTenure2Assessment(assessmentValue);
	                break;

	            case "3":
	            case "सरकार":
	                area.setTenure3Areaa(value);
	                area.setTenure3Assessment(assessmentValue);
	                break;

	            case "4":
	            case "सरकार पट्टेदार":
	                area.setTenure4Areaa(value);
	                area.setTenure4Assessment(assessmentValue);
	                break;
	        }
	    }

	    return new ArrayList<>(ccodeMap.values());
	}


public Form1AbstractDyslrDao toDaoList(Form1Abstract_dyslr form1AbstractDyslr) {
		Form1AbstractDyslrDao form1AbstractDyslrDao=new Form1AbstractDyslrDao();
			BeanUtils.copyProperties(form1AbstractDyslr, form1AbstractDyslrDao);
		return form1AbstractDyslrDao;
	}
	

@Override
		public List<Form1AbstractDyslrDao> getForm1AbstractReportDyslrListModified(

		        Integer districtCode,
		        Integer talukaCode,
		        Locale locale,
		        HttpServletRequest request) {

		    dataSourceKeeper.setDataSourceEchwadi(
		            request.getHeader("echDbName"),
		            request.getHeader("echSchemaName"));

		    List<Form1Abstract_dyslr> formAbstractList =
		    		form1AbstractDyslrRepository.findByDistrictCodeAndTalukaCode(
		                    districtCode, talukaCode);

		    DatabaseContextHolder.restoreCtx();

		    if (formAbstractList.isEmpty()) {
		        throw new ResourceNotFoundException(
		                "error.form.getData",
		                messageSource,
		                locale);
		    }

		    List<Form1AbstractDyslrDao> reportList = new ArrayList<>();
		    for (Form1Abstract_dyslr formAbstract : formAbstractList) {
		        Form1Abstract_dyslr mergedRecord = new Form1Abstract_dyslr();
		        BeanUtils.copyProperties(formAbstract, mergedRecord);

		        List<Object[]> liveDataLst = form1DyslrRepository.getVillageWiseLandData(
		                request.getHeader("mhrHost"),
		                request.getHeader("mhrDbName"),
		                request.getHeader("mhrSchemaName"),
		                CommonConstants.mhrorUser,
		                CommonConstants.mhrorPassword,
		                request.getHeader("echHost"),
		                request.getHeader("echDbName"),
		                request.getHeader("echSchemaName"),
		                CommonConstants.echUser,
		                CommonConstants.echPassword,
		        		formAbstract.getDistrictCode().toString(),
		                formAbstract.getTalukaCode().toString());
		        

		        if (!liveDataLst.isEmpty() && liveDataLst.get(0) != null) {
		            Object[] liveData = liveDataLst.get(0);
		            System.out.println("===== LIVE DATA =====");
		            for (int i = 0; i < liveData.length; i++) {
		                System.out.println("liveData[" + i + "] = " + liveData[i]);
		            }
		            System.out.println("=====================");
		            
		            mergedRecord.setcCode(formAbstract.getcCode());
		            mergedRecord.setDistrictCode(formAbstract.getDistrictCode());
		            mergedRecord.setTalukaCode(formAbstract.getTalukaCode());
		            mergedRecord.setTenure1Area(safeBigDecimal(liveData[0]));
		            mergedRecord.setTenure1Assessment(safeBigDecimal(liveData[1]));
		            mergedRecord.setTenure2Area(safeBigDecimal(liveData[2]));
		            mergedRecord.setTenure2Assessment(safeBigDecimal(liveData[3]));
		            mergedRecord.setTenure3Area(safeBigDecimal(liveData[4]));
		            mergedRecord.setTenure3Assessment(safeBigDecimal(liveData[5]));
		            mergedRecord.setTenure4Area(safeBigDecimal(liveData[6]));
		            mergedRecord.setTenure4Assessment(safeBigDecimal(liveData[7]));
		            mergedRecord.setPotKharabArea(safeBigDecimal(liveData[8]));
		            mergedRecord.setPotKharabAssessment(safeBigDecimal(liveData[9]));
		            mergedRecord.setRiverAreaH(safeBigDecimal(liveData[10]));
		            mergedRecord.setNalaAreaH(safeBigDecimal(liveData[11]));
		            mergedRecord.setForm3NetCultiArea(safeBigDecimal(liveData[12]));
		            mergedRecord.setForm3Assessment(safeBigDecimal(liveData[13]));
		            mergedRecord.setUnOccupied(safeBigDecimal(liveData[14]));

		        }
		        System.out.println("===== MERGED RECORD BEFORE toDao =====");
		        System.out.println("tenure1Area = " + mergedRecord.getTenure1Area());
		        System.out.println("tenureCode1 = " + mergedRecord.getTenureCode1());
		        System.out.println("tenureCode2 = " + mergedRecord.getTenureCode2());

		        System.out.println("tenure1Assessment = " + mergedRecord.getTenure1Assessment());
		        System.out.println("tenure2Area = " + mergedRecord.getTenure2Area());
		        System.out.println("tenure2Assessment = " + mergedRecord.getTenure2Assessment());
		        System.out.println("tenure3Area = " + mergedRecord.getTenure3Area());
		        System.out.println("tenure3Assessment = " + mergedRecord.getTenure3Assessment());
		        System.out.println("tenure4Area = " + mergedRecord.getTenure4Area());
		        System.out.println("tenure4Assessment = " + mergedRecord.getTenure4Assessment());
		        System.out.println("potKharabArea = " + mergedRecord.getPotKharabArea());
		        System.out.println("potKharabAssessment = " + mergedRecord.getPotKharabAssessment());
		        System.out.println("riverAreaH = " + mergedRecord.getRiverAreaH());
		        System.out.println("nalaAreaH = " + mergedRecord.getNalaAreaH());
		        System.out.println("form3NetCultiArea = " + mergedRecord.getForm3NetCultiArea());
		        System.out.println("form3Assessment = " + mergedRecord.getForm3Assessment());
		        System.out.println("unOccupied = " + mergedRecord.getUnOccupied());
		        System.out.println("======================================");
		        reportList.add(toDaoList(mergedRecord));
		    }

		    return reportList;
		}
private BigDecimal safeBigDecimal(Object value) {
    if (value == null) {
        return BigDecimal.ZERO;
    }
    String stringValue = value.toString().trim();
    if (stringValue.isEmpty() || "null".equalsIgnoreCase(stringValue)) {
        return BigDecimal.ZERO;
    }
    try {
        return new BigDecimal(stringValue);
    } catch (NumberFormatException e) {
        return BigDecimal.ZERO;
    }
}
}