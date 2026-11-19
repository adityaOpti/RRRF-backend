	/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.Form1AbstractDao;
import com.probity.echawdi.dao.Form1Dao;
import com.probity.echawdi.dao.Form1HeaderDao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.dao.NewForm1DyslrDao;
import com.probity.echawdi.entity.Form1;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form1AbstractRepository;
import com.probity.echawdi.repository.Form1DyslrRepository;
import com.probity.echawdi.repository.Form1Repository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.service.Form1Service;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

/**
 * @author Rushikesh Durgude
 *
 */

@Component
public class  Form1ServiceImpl implements Form1Service{
	
	@Autowired
	private Form1Repository form1Repository;
	@Autowired
	private Form1AbstractRepository form1AbstractRepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;
@Autowired
	private Form1DyslrRepository form1DyslrRepository ;
	@Override 
	public void updateForm1(@Valid Form1Dao form1Dao,HttpServletRequest request,Locale locale) {
		Form1 form1=new Form1();
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		if(form1Dao.getId()!=null) {
			form1 = form1Repository.findById(form1Dao.getId()).
					orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form1", form1Dao.getId()));
		}
		BeanUtils.copyProperties(form1Dao, form1);
		if(form1Dao.getHissaNo() != null) {
			String [] hissaNoo = form1Dao.getHissaNo().split("/");
			if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
				String  tHissaNo = form1Dao.getHissaNo();
				for (int i = hissaNoo.length; i <= 8; i++) {
					tHissaNo += " /";
				}
				hissaNoo = tHissaNo.split("/");
			}
			List<String> lstHissNo = Arrays.asList(hissaNoo);
			//System.out.println("lstHissNo--->"+lstHissNo);
			form1.setPin1(lstHissNo.get(0).trim().isEmpty() ? lstHissNo.get(0):  lstHissNo.get(0).trim());
			form1.setPin2(lstHissNo.get(1).trim().isEmpty() ? lstHissNo.get(1):  lstHissNo.get(1).trim());
			form1.setPin3(lstHissNo.get(2).trim().isEmpty() ? lstHissNo.get(2):  lstHissNo.get(2).trim());
			form1.setPin4(lstHissNo.get(3).trim().isEmpty() ? lstHissNo.get(3):  lstHissNo.get(3).trim());
			form1.setPin5(lstHissNo.get(4).trim().isEmpty() ? lstHissNo.get(4):  lstHissNo.get(4).trim());
			form1.setPin6(lstHissNo.get(5).trim().isEmpty() ? lstHissNo.get(5):  lstHissNo.get(5).trim());
			form1.setPin7(lstHissNo.get(6).trim().isEmpty() ? lstHissNo.get(6):  lstHissNo.get(6).trim());
			form1.setPin8(lstHissNo.get(7).trim().isEmpty() ? lstHissNo.get(7):  lstHissNo.get(7).trim());
		}
		form1Repository.save(form1);
		DatabaseContextHolder.restoreCtx();
		
	}

	@Override
	public void deleteForm1ById(long id,Locale locale,HttpServletRequest request){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form1 form1 =form1Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form1", id));
		form1.setActiveFlag('N');
		form1Repository.save(form1);
		DatabaseContextHolder.restoreCtx();
	}

	@Override
	public Form1Dao getForm1ById(long id,Locale locale,HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form1 form1 =form1Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form1", id));
		DatabaseContextHolder.restoreCtx();
		return toDao(form1);
	}

	@Override
	public Response getAllForm1Data(Integer districtCode,Integer talukaCode,String cCode, String pin,Locale locale,HttpServletRequest request) {
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
		List<Object[]> listOfForm1 =new ArrayList<>();
		if(pin.isEmpty()) {
			listOfForm1 = form1Repository.findByCcode(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
					request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
					cCode,'Y');
		}else {
			listOfForm1 = form1Repository.findByCcodeAndPin(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
					request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
					cCode,pin,'Y');
		}
		DatabaseContextHolder.restoreCtx();
		if(listOfForm1.isEmpty()) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}
		Response form1Response =new  Response();
		form1Response.setForm1Data(listOfForm1.stream().map((object) -> {
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
			if(object[15]!=null) {
				form1Dao.setId(Long.valueOf(object[15].toString()));
			}

			return form1Dao;
		}).sorted(Comparator.comparing(Form1Dao:: getIntPin)).collect(Collectors.toList()));
		return form1Response;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}		
	}



	// convert Entity into Dao
	private Form1Dao toDao(Form1 form1){
		Form1Dao form1Dao = new Form1Dao();
		BeanUtils.copyProperties(form1, form1Dao);
		return form1Dao;
	}

	@Override
	public Response getForm1ReportDetails(Integer districtCode, Integer talukaCode,String cCode,Locale locale,HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		
//		List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
//				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//				request.getHeader("servarthId"));
//		Response response =new  Response();
//		response.setKhataMergeData(ccodeslist.stream().map(r -> {
//			KhataMergeDao km=new KhataMergeDao();
//			km.setcCode(r[0].toString());
//			km.setServarthId(r[1].toString());
//			km.setVillageName(r[2].toString());
//			return km;
//		}).collect(Collectors.toList()));
//		
//		
//		String ccodes=null;
//		for(KhataMergeDao r : response.getKhataMergeData()) {
//			if(r.getcCode().equals(cCode)) {
//			ccodes=r.getcCode();
//			break;
//			}else{
//				ccodes="0";
//				}}
//		
//		if(ccodes.equals(cCode)) {	
		List<Object[]> form1Report = form1Repository.findByCcode(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode,'Y');
		DatabaseContextHolder.restoreCtx();
		if(form1Report.isEmpty()) {
			throw new ResourceNotFoundException("error.form.reportdetails",messageSource,locale);
		}
		else {
			List<Form1Dao> form1Daos;
			form1Daos=form1Report.stream().map((object) -> {
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
				if(object[15]!=null) {
					form1Dao.setId(Long.valueOf(object[15].toString()));
				}
				return form1Dao;
			}).collect(Collectors.toList());
			Response form1Response =new  Response();
			form1Response.setForm1Data(form1Daos.stream().sorted(Comparator.comparing(Form1Dao:: getIntPin)).collect(Collectors.toList()));
			return form1Response;
		}
//		}else {
//			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
//		}	
	}
	
	
	@Override
	public List<Form1AbstractDao> getForm1FooterDetails(Integer districtCode, Integer talukaCode,String cCode,Locale locale,HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//		List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
//				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//				request.getHeader("servarthId"));
//		Response response =new  Response();
//		response.setKhataMergeData(ccodeslist.stream().map(r -> {
//			KhataMergeDao km=new KhataMergeDao();
//			km.setcCode(r[0].toString());
//			km.setServarthId(r[1].toString());
//			km.setVillageName(r[2].toString());
//			return km;
//		}).collect(Collectors.toList()));
//		
//		
//		String ccodes=null;
//		for(KhataMergeDao r : response.getKhataMergeData()) {
//			if(r.getcCode().equals(cCode)) {
//			ccodes=r.getcCode();
//			break;
//			}else{
//				ccodes="0";
//				}}
//		
//		if(ccodes.equals(cCode)) {	
		List<Object[]> form1Abstract=form1AbstractRepository.getForm1Abstarctdetails(request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName")
				,CommonConstants.echUser,CommonConstants.echPassword,cCode);
		DatabaseContextHolder.restoreCtx();
		List<Form1AbstractDao> form1AbstractDaos= form1Abstract.stream().map((obj)->{
			Form1AbstractDao form1AbstractDao=new Form1AbstractDao();
			form1AbstractDao.setcCode(obj[0].toString()==null ? "" : obj[0].toString());
			form1AbstractDao.setRiversNalas(obj[1]== null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(obj[1].toString())));
			form1AbstractDao.setNalas(obj[2]== null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(obj[2].toString())));
			form1AbstractDao.setVillageSite(obj[3]== null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(obj[3].toString())));
			form1AbstractDao.setRoadsAndPath(obj[4]== null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(obj[4].toString())));
			return form1AbstractDao;
		}).collect(Collectors.toList());
		return form1AbstractDaos;
//		}else {
//			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
//		}
	}


	//    private Boolean existingrecord(String cCode,String pin,String hissaNo){
	//    	Optional<Form1> form1=form1Repository.findBycCodeAndPin(cCode,pin,'Y',hissaNo);
	//    	if(form1.isPresent() ) {
	//    		return true;
	//    	}
	//    		return false;
	//	}

	@Override
	public List<Form1HeaderDao> getForm1headerdetails(Integer districtCode, Integer talukaCode,String cCode, Locale locale,HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//		List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
//				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//				request.getHeader("servarthId"));
//		Response response =new  Response();
//		response.setKhataMergeData(ccodeslist.stream().map(r -> {
//			KhataMergeDao km=new KhataMergeDao();
//			km.setcCode(r[0].toString());
//			km.setServarthId(r[1].toString());
//			km.setVillageName(r[2].toString());
//			return km;
//		}).collect(Collectors.toList()));
//		
//		
//		String ccodes=null;
//		for(KhataMergeDao r : response.getKhataMergeData()) {
//			if(r.getcCode().equals(cCode)) {
//			ccodes=r.getcCode();
//			break;
//			}else{
//				ccodes="0";
//				}}
//		
//		if(ccodes.equals(cCode)) {	
		List<Object[]> form1c=form1Repository.getForm1headerdetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,cCode);
		DatabaseContextHolder.restoreCtx();
		List<Form1HeaderDao> form1HeaderDaos= form1c.stream().map((obj)->{
			Form1HeaderDao form1HeaderDao=new Form1HeaderDao();
			form1HeaderDao.setSettlementYear(obj[0]==null ? "" : obj[0].toString());
			form1HeaderDao.setSettlementExp(obj[1]==null ? "" : obj[1].toString());
			form1HeaderDao.setGarden_rate(obj[2]==null ? "" : obj[2].toString());
			form1HeaderDao.setRice_rate(obj[3]==null ? "" : obj[3].toString());
			form1HeaderDao.setSurvey_group(obj[4]==null ? "" : obj[4].toString());
			form1HeaderDao.setDate_inst(obj[5]==null ? "" : obj[5].toString());
			form1HeaderDao.setDry_rate(obj[6]==null ? "" : obj[6].toString());
			form1HeaderDao.setWarkas_rate(obj[7]==null ? "" : obj[7].toString());
			return form1HeaderDao;
		}).collect(Collectors.toList());
		return form1HeaderDaos;
//		}else {
//			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
//		}
	}

	@Override
	public List<Form1Dao> getForm1ODCReportDetails(String cCode, Locale locale,HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//		List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
//				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//				request.getHeader("servarthId"));
//		Response response =new  Response();
//		response.setKhataMergeData(ccodeslist.stream().map(r -> {
//			KhataMergeDao km=new KhataMergeDao();
//			km.setcCode(r[0].toString());
//			km.setServarthId(r[1].toString());
//			km.setVillageName(r[2].toString());
//			return km;
//		}).collect(Collectors.toList()));
//		
//		
//		String ccodes=null;
//		for(KhataMergeDao r : response.getKhataMergeData()) {
//			if(r.getcCode().equals(cCode)) {
//			ccodes=r.getcCode();
//			break;
//			}else{
//				ccodes="0";
//				}}
//		
//		if(ccodes.equals(cCode)) {	
		List<Object[]> form1odc=form1Repository.getform1ODCReportDetails(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,cCode);
		DatabaseContextHolder.restoreCtx();
		List<Form1Dao> form1Daos= form1odc.stream().map((object)->{
			Form1Dao form1Dao=new Form1Dao();
			form1Dao.setcCode(object[0].toString());
			form1Dao.setPin(object[1].toString());
			form1Dao.setHissaNo(object[2].toString());
			form1Dao.setTotalAreaH(object[3].toString());
			form1Dao.setNetCultiAreaH(object[4].toString());
			form1Dao.setAssessment(object[5].toString());
			form1Dao.setPotkharabaAH(object[6].toString());
			form1Dao.setPotkharabaBH(object[7].toString());
			form1Dao.setTenureName(object[8].toString());
			return form1Dao;
		}).sorted(Comparator.comparing(Form1Dao:: getIntPin)).collect(Collectors.toList());
		return form1Daos;
//		}else {
//			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
//		}

	}

	@Override
	public List<Form1Dao> form1OdcDiff(String cCode,Locale locale,HttpServletRequest request){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//		List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
//				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//				request.getHeader("servarthId"));
//		Response response =new  Response();
//		response.setKhataMergeData(ccodeslist.stream().map(r -> {
//			KhataMergeDao km=new KhataMergeDao();
//			km.setcCode(r[0].toString());
//			km.setServarthId(r[1].toString());
//			km.setVillageName(r[2].toString());
//			return km;
//		}).collect(Collectors.toList()));
//		
//		
//		String ccodes=null;
//		for(KhataMergeDao r : response.getKhataMergeData()) {
//			if(r.getcCode().equals(cCode)) {
//			ccodes=r.getcCode();
//			break;
//			}else{
//				ccodes="0";
//				}}
//		
//		if(ccodes.equals(cCode)) {	
		List<Form1Dao> form1List=new ArrayList<>();
		List<Object[]> form1AndOdcDiff=form1Repository.form1OdcDiff(request.getHeader("mhrHost"),
				request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,cCode);
		DatabaseContextHolder.restoreCtx();
		form1List=form1AndOdcDiff.stream().map((object)->{
			Form1Dao form1Dao=new Form1Dao();
			form1Dao.setcCode(object[0].toString());
			form1Dao.setPin(object[1].toString());
			form1Dao.setForm7TenureCode(object[2].toString());
			form1Dao.setForm1TenureCode(object[3].toString());
			form1Dao.setForm7Totalarea(object[4].toString());
			form1Dao.setForm1Totalarea(object[5].toString());
			form1Dao.setForm7PotKharaba(object[6].toString());
			form1Dao.setForm1PotKharaba(object[7].toString());
			form1Dao.setForm7NetCultiArea(object[8].toString());
			form1Dao.setForm1NetCultiArea(object[9].toString());
			form1Dao.setForm7Assessment(object[10].toString());
			form1Dao.setForm1Assessment(object[11].toString());
			form1Dao.setForm7PotType(object[12]==null?"":object[12].toString());
			form1Dao.setForm1PotType(object[13]==null?"":object[13].toString());
			return form1Dao;
		}).sorted(Comparator.comparing(Form1Dao:: getIntPin)).collect(Collectors.toList());
		return form1List;
//		}else {
//			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
//		}
	}
	
	@Override
	public List<NewForm1DyslrDao> form1OdcDyslrDiff(String cCode, Locale locale, HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<NewForm1DyslrDao> form1List=new ArrayList<>();
//		
//		List<Object[]> form1AndOdcDiff=form1DyslrRepository.Form1OdcDiffDyslr(request.getHeader("echHost"),
//				request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,cCode);
//		DatabaseContextHolder.restoreCtx();
		List<Object[]> form1AndOdcDiff=form1DyslrRepository.Form1OdcDiffDyslr( 
				request.getHeader("echHost"),//1
				request.getHeader("echDbName"),//2
	            request.getHeader("echSchemaName"),//3
	            CommonConstants.echUser,//4
	            CommonConstants.echPassword,//5
	            request.getHeader("mhrHost"),//6
				request.getHeader("mhrDbName"),//7
				request.getHeader("mhrSchemaName"),//8
				CommonConstants.mhrorUser,//9
				CommonConstants.mhrorPassword,//10
				cCode);
		DatabaseContextHolder.restoreCtx();	
		
		
		
		form1List = form1AndOdcDiff.stream().map(object -> {
		    NewForm1DyslrDao form1Dao = new NewForm1DyslrDao();

		    form1Dao.setCCode(object[0].toString());
		    form1Dao.setPin(object[1].toString());
		    form1Dao.setForm7TenureCode(object[2].toString());
		    form1Dao.setForm1DyslrTenureCode(object[3].toString());
		    form1Dao.setForm7Totalarea(object[4].toString());
		    form1Dao.setForm1DyslrTotalarea(object[5].toString());
		    form1Dao.setForm7PotKharaba(object[6].toString());
		    form1Dao.setForm1DyslrPotKharaba(object[7].toString());
		    form1Dao.setForm7NetCultiArea(object[8].toString());
		    form1Dao.setForm1DyslrNetCultiArea(object[9].toString());
		    form1Dao.setForm7Assessment(object[10].toString());
		    form1Dao.setForm1DyslrAssessment(object[11].toString());
		    form1Dao.setForm7PotType(object[12] == null ? "" : object[12].toString());
		    form1Dao.setForm1PotType(object[13] == null ? "" : object[13].toString());

		    return form1Dao;
		})
		.sorted(Comparator.comparingInt(dao -> Integer.parseInt(dao.getPin())))
		.collect(Collectors.toList());

		return form1List;
	}
}