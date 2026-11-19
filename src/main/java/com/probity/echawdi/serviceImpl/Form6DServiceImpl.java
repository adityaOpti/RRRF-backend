package com.probity.echawdi.serviceImpl;

import java.util.ArrayList;
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
import com.probity.echawdi.dao.Form6DDao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.entity.Form6D;
import com.probity.echawdi.exception.FieldValidationException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form6DRepository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.service.Form6DService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

@Component
public class Form6DServiceImpl implements Form6DService{

	@Autowired
	private Form6DRepository form6DRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;
	
	@Override
	public Response getAllForm6DData(String cCode,String pin,Locale locale,HttpServletRequest request) {
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
		List<Form6D> lstForm6DData= form6DRepository.findBycCodeAndPinAndActiveFlag(cCode,pin,'Y');
	    DatabaseContextHolder.restoreCtx();
	    if(Collections.isEmpty(lstForm6DData)) {
	    	throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
      	}
	    Response form6DResponse =new  Response();
	     form6DResponse.setForm6DData(lstForm6DData.stream().map((object) -> toDao(object)).collect(Collectors.toList()));
	     return form6DResponse;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}
	
	//native
	@Override
	public MessageResponse save(Form6DDao form6DDao,Locale locale,HttpServletRequest request) {
	 
		if(form6DDao.getcCode() != null) {
//			if(!(form6DRepository.existsByMutationNo(
//					request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
//					form6DDao.getcCode(),form6DDao.getPin(),form6DDao.getHissaNo(),form6DDao.getMutationEntryInVillageForm6()))
//					) {
			DatabaseContextHolder.restoreCtx();
			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
			Form6D form6D = toEntity(form6DDao);
			if(form6DDao.getHissaNo() != null) {
			String [] hissaNoo = form6DDao.getHissaNo().split("/");
			if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
				String  tHissaNo = form6DDao.getHissaNo();
				for (int i = hissaNoo.length; i <= 8; i++) {
					tHissaNo += " /";
				}
				hissaNoo = tHissaNo.split("/");
			}
			List<String> lstHissNo = Arrays.asList(hissaNoo);
			//System.out.println("lstHissNo--->"+lstHissNo);
			
			form6D.setPin1(lstHissNo.get(0).trim());
			form6D.setPin2(lstHissNo.get(1));
			form6D.setPin3(lstHissNo.get(2));
			form6D.setPin4(lstHissNo.get(3));
			form6D.setPin5(lstHissNo.get(4));
			form6D.setPin6(lstHissNo.get(5));
			form6D.setPin7(lstHissNo.get(6));
			form6D.setPin8(lstHissNo.get(7));
			}
			form6DRepository.save(form6D);
			DatabaseContextHolder.restoreCtx();
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.savedata", null, locale)).build();	
//			}
//			throw new ExchangeException("error.form.already");
    	}
	    throw new FieldValidationException("error.form.errorwhilesave");
	}

	private Form6D toEntity(Form6DDao form6DDao) {
		Form6D form6D = new Form6D();
        BeanUtils.copyProperties(form6DDao, form6D,"id");
        return form6D;
	    
	}
	
	private Form6DDao toDao(Form6D form6D){
	    	
		Form6DDao form6DDao = new Form6DDao();
    	BeanUtils.copyProperties(form6D, form6DDao);
    	form6DDao.setSurveyNoOrSubDivisionNo(form6D.getPin()+(form6D.getPin1().trim().isEmpty() ? "" : "-"+form6D.getPin1().trim())+form6D.getPin2().trim()+form6D.getPin3().trim()+form6D.getPin4().trim()
    			+form6D.getPin5().trim()+form6D.getPin6().trim()+form6D.getPin7().trim()+form6D.getPin8().trim());
    	form6DDao.setByWhomDoneAndDate(form6D.getByWhomDone()+" "+ form6D.getDateOfChange());
		
    	String hissaNo = Stream.of(form6D.getPin1(),  
    			form6D.getPin2().trim().isEmpty() ? null : "/"+form6D.getPin2(), 
				form6D.getPin3().trim().isEmpty() ? null : "/"+form6D.getPin3(),
				form6D.getPin4().trim().isEmpty() ? null : "/"+form6D.getPin4(),
				form6D.getPin5().trim().isEmpty() ? null : "/"+form6D.getPin5(),
				form6D.getPin6().trim().isEmpty() ? null : "/"+form6D.getPin6(),
				form6D.getPin7().trim().isEmpty() ? null : "/"+form6D.getPin7(),
				form6D.getPin8().trim().isEmpty() ? null : "/"+form6D.getPin8())
		.filter(s -> s != null)
		.collect(Collectors.joining());
		
    	form6DDao.setHissaNo(hissaNo.isEmpty() ? "" : hissaNo);
    	
    	return form6DDao;
    }

	

	@Override 
	public void updateForm6D(Form6DDao form6DDao,Locale locale,HttpServletRequest request)
	{
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form6D form6D = form6DRepository.findById(form6DDao.getId())
			.orElseThrow(() -> new ResourceNotFoundException("Form6D"));
			BeanUtils.copyProperties(form6DDao, form6D);
			if(form6DDao.getHissaNo()  != null) {
			String [] hissaNoo = form6DDao.getHissaNo().split("/");
			if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
				String  tHissaNo = form6DDao.getHissaNo();
				for (int i = hissaNoo.length; i <= 8; i++) {
					tHissaNo += " /";
				}
				hissaNoo = tHissaNo.split("/");
			}
			List<String> lstHissNo = Arrays.asList(hissaNoo);
			//System.out.println("lstHissNo--->"+lstHissNo);
			form6D.setPin1(lstHissNo.get(0).trim());
			form6D.setPin2(lstHissNo.get(1));
			form6D.setPin3(lstHissNo.get(2));
			form6D.setPin4(lstHissNo.get(3));
			form6D.setPin5(lstHissNo.get(4));
			form6D.setPin6(lstHissNo.get(5));
			form6D.setPin7(lstHissNo.get(6));
			form6D.setPin8(lstHissNo.get(7));
		}
		form6DRepository.save(form6D);
		    DatabaseContextHolder.restoreCtx();
	}
	
	@Override
	public void deleteForm6DById(long id,Locale locale,HttpServletRequest request){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		 Form6D form6D =form6DRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Form6D"));
		 form6D.setActiveFlag('N');
		 form6DRepository.save(form6D);
		 DatabaseContextHolder.restoreCtx();
	 }

	@Override
	public Response getAllForm6DReportDetails(String cCode,Locale locale,HttpServletRequest request) {
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
	    List<Form6D> form6DData = form6DRepository.findBycCodeAndActiveFlag(cCode,'Y');
	    DatabaseContextHolder.restoreCtx();
	    if(Collections.isEmpty(form6DData)) {
	    	throw new ResourceNotFoundException("error.form.reportdetails",messageSource,locale);
      	}
	    Response form6DResponse =new  Response();
	    form6DResponse.setForm6DData(form6DData.stream().map((object) -> toDao(object)).collect(Collectors.toList()));
    return form6DResponse;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}	
	}
	
	///native  done
	@Override
	public List<Form6DDao> getHissaNo(String cCode, String pin,Locale locale,HttpServletRequest request) {
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
			List<Form6DDao> form6DDaoLst= new ArrayList<>();
			  List<Object> form6DDtl = form6DRepository.findBycCodeAndPin(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,cCode, pin);
			  DatabaseContextHolder.restoreCtx();
			  if(Collections.isEmpty(form6DDtl)) {
					throw new ResourceNotFoundException("error.form.eferfar",messageSource,locale);
				}
			  else {
					 form6DDaoLst = form6DDtl.stream().map(objects -> {
					 Form6DDao form6DDao =new Form6DDao();
					 form6DDao.setHissaNo(objects.toString());
					 return form6DDao;
					 }).collect(Collectors.toList());
			  return form6DDaoLst;
			  }}else {
					throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
				}	
	}
	
	//native done
	@Override
	public List<Form6DDao> getForm6DEferfarDetials(String cCode, String pin,String hissaNo,Locale locale,HttpServletRequest request) {
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
				List<Form6DDao> form6DDaoLst= new ArrayList<>();
				List<Object[]> form6DDtl = form6DRepository.findByInputs(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,cCode, pin,hissaNo);
				DatabaseContextHolder.restoreCtx();
				if(Collections.isEmpty(form6DDtl)) {
					throw new ResourceNotFoundException("error.form.eferfar",messageSource,locale);
				}
				else 
				{
					form6DDaoLst = form6DDtl.stream().map(objects -> {
						Form6DDao form6DDao =new Form6DDao();
						form6DDao.setPin(objects[0].toString());
						form6DDao.setHissaNo(objects[1].toString());
						form6DDao.setMutationEntryInVillageForm6(objects[2].toString());
						return form6DDao;
					}).collect(Collectors.toList());
 					return form6DDaoLst;		
				}}else {
					throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
				}	
    }
	
	 @SuppressWarnings("unused")
	private Form6DDao toDao1(Form6D form6D){
		 Form6DDao form6DDao = new Form6DDao();
	        BeanUtils.copyProperties(form6D, form6DDao,"dateOfChange");
	        if(form6D.getDateOfChange()!= null) {
	        	form6DDao.setDateOfChange(form6D.getDateOfChange());
	        }
	        return form6DDao;
	    }
}