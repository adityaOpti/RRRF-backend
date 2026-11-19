package com.probity.echawdi.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.Form2Dao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.entity.Form2;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form2Repository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.service.Form2Service;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

/**
 * @author Rushikesh Durgude
 *
 */

@Component
public class Form2ServiceImpl implements Form2Service{
	@Autowired
	private Form2Repository form2Repository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;

	@Override
	public Response getAllForm2Data(String cCode, String pin,HttpServletRequest request,Locale locale) {
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
		List<Object[]> lstForm2Data=new ArrayList<>();
		if(pin.isEmpty()) {
			lstForm2Data= form2Repository.getAllForm2WithoutPin(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
					request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
					cCode,'Y');
		}else {
			lstForm2Data= form2Repository.getAllForm2ForTableAndReport(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
					request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
					cCode,pin,'Y');
		}
		DatabaseContextHolder.restoreCtx();
		if(lstForm2Data.isEmpty()) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}
		Response form2Response =new  Response();
		form2Response.setForm2Data(lstForm2Data.stream().map((object) -> {
			Form2Dao form2Dao = new Form2Dao();
			form2Dao.setPin(object[0] == null ? "" : object[0].toString() );
			form2Dao.setNaAreaH(object[1] == null ? "" : object[1].toString() );
			form2Dao.setPart(object[3] == null ? "" : object[3].toString() );
			form2Dao.setPurposeOfUse(object[4] == null ? "" : object[4].toString() );
			form2Dao.setRemarks(object[5] == null ? "" : object[5].toString() );
			if(object[6]!=null) {
				form2Dao.setId(Long.valueOf(object[6].toString()));
			}
			if(object[7]!=null) {
				form2Dao.setPeriodFrom((Date) object[7]);
			}

			if(object[8]!=null) {
				form2Dao.setPeriodTo((Date) object[8]);
			}

			form2Dao.setcCode(object[9] == null ? "" : object[9].toString() );
			form2Dao.setHissaNo(object[10] == null ? "" : object[10].toString() );
			form2Dao.setAuthority(object[11] == null ? "" : object[11].toString() );
			form2Dao.setOccupanyPrice(object[12] == null ? "" : object[12].toString() );
			form2Dao.setNameOfFirstOccupant(object[13] == null ? "" : object[13].toString() );
			form2Dao.setEntryNumberInTalukaForm2(object[14] == null ? "" : object[14].toString());
			form2Dao.setNaAssessment(object[15] == null ? "" : object[15].toString() );
			form2Dao.setAuthorityOrderNo(object[16] == null ? "" : object[16].toString() );
			if(object[17]!=null) {
				form2Dao.setAuthorityDate((Date) object[17]);
			}
			return form2Dao;
		}).sorted(Comparator.comparing(Form2Dao:: getIntPin)).collect(Collectors.toList()));
		return form2Response;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}

//	@Override
//	public MessageResponse save(Form2Dao form2Dao,HttpServletRequest request,Locale locale) {
//		if(form2Dao.getcCode() != null) {
//			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//			Form2 form2 = toEntity(form2Dao);
//			if(form2Dao.getHissaNo() != null) {
//				String [] hissaNoo = form2Dao.getHissaNo().split("/");
//				if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
//					String  tHissaNo = form2Dao.getHissaNo();
//					for (int i = hissaNoo.length; i <= 8; i++) {
//						tHissaNo += " /";
//					}
//					hissaNoo = tHissaNo.split("/");
//				}
//				List<String> lstHissNo = Arrays.asList(hissaNoo);
//				//System.out.println("lstHissNo--->"+lstHissNo);
//				form2.setPin1(lstHissNo.get(0));
//				form2.setPin2(lstHissNo.get(1));
//				form2.setPin3(lstHissNo.get(2));
//				form2.setPin4(lstHissNo.get(3));
//				form2.setPin5(lstHissNo.get(4));
//				form2.setPin6(lstHissNo.get(5));
//				form2.setPin7(lstHissNo.get(6));
//				form2.setPin8(lstHissNo.get(7));
//			}
//			form2Repository.save(form2);
//			DatabaseContextHolder.restoreCtx();
//			return MessageResponse.builder().status("SUCCESS").message(
//					messageSource.getMessage("error.form.savedata", null, locale)).build();	
//		}
//		throw new FieldValidationException("error.form.errorwhilesave");
//	}
	
	
//	@Override
//	public Response getNirankDataForm2(String cCode, HttpServletRequest request, Locale locale) {
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//		List<Object[]> form2nirank=form2Repository.findNirank(request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,cCode);
//		DatabaseContextHolder.restoreCtx();
//		if(form2nirank.isEmpty()) {
//			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
//		}
//		Response form2Response =new  Response();
//		form2Response.setForm2Data(form2nirank.stream().map((object) -> {
//			Form2Dao form2Dao = new Form2Dao();
//			form2Dao.setcCode(object[2].toString() );
//			form2Dao.setRemarks(object[3].toString() );
//			if(object[4]!=null) {
//				form2Dao.setId(Long.valueOf(object[4].toString()));
//			}
//			return form2Dao;
//		}).collect(Collectors.toList()));
//		return form2Response;
//		
//	}

	
	@Override 
	public void updateForm2(Form2Dao form2Dao,HttpServletRequest request,Locale locale)
	{
		
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form2 form2 =new Form2();
		if(form2Dao.getId()!=null) {
			form2 = form2Repository.findById(form2Dao.getId())
					.orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form2", form2Dao.getId()));
		}
		BeanUtils.copyProperties(form2Dao, form2,"id");
		
		if(form2Dao.getHissaNo() != null) {
			String [] hissaNoo = form2Dao.getHissaNo().split("/");
			if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
				String  tHissaNo = form2Dao.getHissaNo();
				for (int i = hissaNoo.length; i <= 8; i++) {
					tHissaNo += " /";
				}
				hissaNoo = tHissaNo.split("/");
			}
			List<String> lstHissNo = Arrays.asList(hissaNoo);
			//System.out.println("lstHissNo--->"+lstHissNo);
			form2.setPin1(lstHissNo.get(0));
			form2.setPin2(lstHissNo.get(1));
			form2.setPin3(lstHissNo.get(2));
			form2.setPin4(lstHissNo.get(3));
			form2.setPin5(lstHissNo.get(4));
			form2.setPin6(lstHissNo.get(5));
			form2.setPin7(lstHissNo.get(6));
			form2.setPin8(lstHissNo.get(7));
		}
		form2Repository.save(form2);
		DatabaseContextHolder.restoreCtx();
	}

	@Override
	public void deleteForm2ById(long id,HttpServletRequest request,Locale locale){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form2 form2 =form2Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form2", id));
		form2.setActiveFlag('N');
		form2Repository.save(form2);
		DatabaseContextHolder.restoreCtx();
	}

	@Override
	public Response getForm2ReportDetails(String cCode,HttpServletRequest request,Locale locale) {
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
		List<Object[]> form2Report = form2Repository.getAllForm2ForReport(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode,'Y');
		DatabaseContextHolder.restoreCtx();
		if(form2Report.isEmpty()) {
			throw new ResourceNotFoundException("error.form.reportdetails",messageSource,locale);
		}
		else {
			Response form2Response =new  Response();
			form2Response.setForm2Data(form2Report.stream().map((object) -> {
				Form2Dao form2Dao = new Form2Dao();
				form2Dao.setPin(object[0] == null ? "" : object[0].toString() );
				form2Dao.setNaAreaH(object[1] == null ? "" :object[1].toString());
				form2Dao.setPart(object[3] == null ? "" :object[3].toString());
				form2Dao.setPurposeOfUse(object[4] == null ? "" :object[4].toString());
				form2Dao.setRemarks(object[5] == null ? "" :object[5].toString());
				if(object[6]!=null) {
					form2Dao.setId(Long.valueOf(object[6].toString()));
				}
				if(object[7]!=null) {
					form2Dao.setPeriodFrom((Date) object[7]);
				}
				if(object[8]!=null) {
					form2Dao.setPeriodTo((Date) object[8]);
				}
				form2Dao.setcCode(object[9] == null ? "" :object[9].toString());
				form2Dao.setHissaNo(object[10] == null ? "" :object[10].toString());
				form2Dao.setAuthority(object[11] == null ? "" :object[11].toString());
				form2Dao.setOccupanyPrice(object[12] == null ? "" :object[12].toString());
				form2Dao.setNameOfFirstOccupant(object[13] == null ? "" :object[13].toString());
				form2Dao.setEntryNumberInTalukaForm2(object[14] == null ? "" :object[14].toString());
				form2Dao.setNaAssessment(object[15] == null ? "" : object[15].toString() );
				form2Dao.setAuthorityOrderNo(object[16] == null ? "" : object[16].toString() );
				if(object[17]!=null) {
				form2Dao.setAuthorityDate((Date) object[17]);
				}
				return form2Dao;
			}).sorted(Comparator.comparing(Form2Dao:: getIntPin)).collect(Collectors.toList()));
			return form2Response;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}

	
}
