package com.probity.echawdi.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.Form6BDao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.entity.Form6B;
import com.probity.echawdi.exception.FieldValidationException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form6BRepository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.service.Form6BService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

@Component
public class Form6BServiceImpl implements Form6BService{

	@Autowired
	private Form6BRepository form6BRepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;

	private Form6BDao toDao(Form6B form6B){
		Form6BDao form6BDao = new Form6BDao();
		BeanUtils.copyProperties(form6B, form6BDao);
		return form6BDao;
	}
	
	private Form6B toEntity(Form6BDao form6BDao) {
		Form6B form6B = new Form6B();
		BeanUtils.copyProperties(form6BDao,form6B,"dateOfDeath","id");
		if(form6B.getDateOfDeath()!= null) {
			form6B.setDateOfDeath(form6B.getDateOfDeath());
		}
		return form6B;
	}

	@Override
	public MessageResponse save(Form6BDao form6BDao,Locale locale,HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		if(form6BDao.getcCode() != null) {
			Form6B form6B = toEntity(form6BDao);
			form6BRepository.save(form6B);
			DatabaseContextHolder.restoreCtx();
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.savedata", null, locale)).build();	
		}
		throw new FieldValidationException("error.form.errorwhilesave");
	}

	@Override 
	public void updateForm6B(Form6BDao form6BDao,Locale locale,HttpServletRequest request)
	{
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form6B form6B = form6BRepository.findById(form6BDao.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Form6B"));
		BeanUtils.copyProperties(form6BDao, form6B);
		form6BRepository.save(form6B);
		DatabaseContextHolder.restoreCtx();
	}

	@Override
	public void deleteForm6BById(long id,Locale locale,HttpServletRequest request){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form6B form6B =form6BRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Form6B"));
		form6B.setActiveFlag('N');
		form6BRepository.save(form6B);
		DatabaseContextHolder.restoreCtx();
	}


	@Override
	public Response getAllForm6BReportDetails(String cCode,Locale locale,HttpServletRequest request) {
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
		List<Form6B> form6BData = form6BRepository.findBycCodeAndActiveFlag(cCode,'Y');
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(form6BData)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}	 
		Response form6BResponse =new  Response();
		form6BResponse.setForm6BData(form6BData.stream().map(form6B->toDao(form6B)).collect(Collectors.toList()));
		return form6BResponse;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}


	@Override
	public List<Form6BDao> getForm6BEferfarDetails(String cCode,Integer entryVillageForm6,
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
		List<Object[]> form6BDtl = form6BRepository.getForm6BEferfarDetails(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				cCode,entryVillageForm6);
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(form6BDtl)) {
			throw new ResourceNotFoundException("error.form.eferfar",messageSource,locale);
		}
		else {
			List<Form6BDao> lstForm6B = form6BDtl.stream().map((object) -> {
				Form6BDao form6BDao=new Form6BDao();
				form6BDao.setEntryVillageForm6(Integer.valueOf(object[0].toString()));
				form6BDao.setNameOfAcquirerOfRight(object[1].toString());
				return form6BDao;
			}). collect(Collectors.toList());
			return lstForm6B;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}


	@Override
	public Response getAllForm6BData(String cCode,Integer entryVillageForm6,Locale locale,HttpServletRequest request) {
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
		List<Form6B> listOfForm6B = form6BRepository.findBycCodeAndEntryVillageForm6AndActiveFlag(
				cCode,
				entryVillageForm6,
				'Y');
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(listOfForm6B)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}else {
			Response form6BResponse =new  Response();
			form6BResponse.setForm6BData(listOfForm6B.stream().map(form6BAR -> toDao(form6BAR)).collect(Collectors.toList()));
			return form6BResponse;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}

	@Override
	public List<Form6BDao> getSankirnReceiptDetails(String cCode, String khataNo, HttpServletRequest request,
			Locale locale) {
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
		List<Object[]> form6BDetl=form6BRepository.getBySankirnReceiptDetail(
				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode, khataNo,'Y');
		DatabaseContextHolder.restoreCtx();
		
		if(Collections.isEmpty(form6BDetl)) {
			throw new ResourceNotFoundException("error.form.khata",messageSource,locale);
		}
		else {
			List<Form6BDao> lstForm6B = form6BDetl.stream().map((object) -> {
				Form6BDao form6BDao=new Form6BDao();
				
				form6BDao.setKhataNo(object[0]==null ? "" : object[0].toString());
				form6BDao.setReceiptNo(object[1] == null ? "" : object[1].toString());
				if(object[2]!=null){
					form6BDao.setReceiptDate(LocalDate.parse(object[2].toString()));
				}
				form6BDao.setcCode(object[3]==null ? "" : object[3].toString());
				return form6BDao;
			}). collect(Collectors.toList());
			return lstForm6B;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
			
		}
		
	
}


//old code
//	    	form6BDao.setReceiptNoDate(form6B.getReceiptNo()+" "+ form6B.getReceiptDate().toString() );
//	public Form6BDao toDao(Form6B form6B) {
//		Form6BDao form6BDao=new Form6BDao();
//		BeanUtils.copyProperties(form6B, form6BDao);
//		String hissaNo = Stream.of(form6B.getPin1().isEmpty()? form6B.getPin1()+"/":form6B.getPin1(),  
//								   form6B.getPin2().isEmpty()? form6B.getPin2()+"/":null, 
//								   form6B.getPin3().isEmpty()? form6B.getPin3()+"/":null,
//								   form6B.getPin4().isEmpty()? form6B.getPin4()+"/":null,
//								   form6B.getPin5().isEmpty()? form6B.getPin5()+"/":null,
//								   form6B.getPin6().isEmpty()? form6B.getPin6()+"/":null,
//								   form6B.getPin7().isEmpty()? form6B.getPin7()+"/":null,
//								   form6B.getPin8().isEmpty()? form6B.getPin8()+"/":null)
//						.filter(s -> s != null)
//						.collect(Collectors.joining());
//		form6BDao.setHissaNo(hissaNo.isEmpty() ? "" : hissaNo);
//		return form6BDao;
//	}