/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.math.BigDecimal;
import java.sql.Date;
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
import com.probity.echawdi.dao.Form1CDao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.entity.Form1C;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form1CRepository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.service.Form1CService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;


/**
 * @author Rushikesh Durgude
 *
 */

@Component
public class  Form1CServiceImpl implements Form1CService{
	
	@Autowired
	private Form1CRepository form1CRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;


	

	@Override 
	public void updateForm1C(Form1CDao form1CDao,HttpServletRequest request,Locale locale){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form1C form1C=new Form1C();
		if(form1CDao.getId()!=null) {
			form1C = form1CRepository.findById(form1CDao.getId()).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form1C", form1CDao.getId()));
		}
		BeanUtils.copyProperties(form1CDao, form1C,"id");
		if(form1CDao.getHissaNo() != null) {
			String [] hissaNoo = form1CDao.getHissaNo().split("/");
			if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
				String  tHissaNo = form1CDao.getHissaNo();
				for (int i = hissaNoo.length; i <= 8; i++) {
					tHissaNo += " /";
				}
				hissaNoo = tHissaNo.split("/");
			}
			List<String> lstHissNo = Arrays.asList(hissaNoo);
			//System.out.println("lstHissNo--->"+lstHissNo);
			form1C.setPin1(lstHissNo.get(0).trim().isEmpty() ? lstHissNo.get(0):  lstHissNo.get(0).trim());
			form1C.setPin2(lstHissNo.get(1).trim().isEmpty() ? lstHissNo.get(1):  lstHissNo.get(1).trim());
			form1C.setPin3(lstHissNo.get(2).trim().isEmpty() ? lstHissNo.get(2):  lstHissNo.get(2).trim());
			form1C.setPin4(lstHissNo.get(3).trim().isEmpty() ? lstHissNo.get(3):  lstHissNo.get(3).trim());
			form1C.setPin5(lstHissNo.get(4).trim().isEmpty() ? lstHissNo.get(4):  lstHissNo.get(4).trim());
			form1C.setPin6(lstHissNo.get(5).trim().isEmpty() ? lstHissNo.get(5):  lstHissNo.get(5).trim());
			form1C.setPin7(lstHissNo.get(6).trim().isEmpty() ? lstHissNo.get(6):  lstHissNo.get(6).trim());
			form1C.setPin8(lstHissNo.get(7).trim().isEmpty() ? lstHissNo.get(7):  lstHissNo.get(7).trim());
		}
		form1CRepository.save(form1C);
				    	DatabaseContextHolder.restoreCtx();
	}
	
	
	
	@Override
	public void deleteForm1CById(long id,HttpServletRequest request,Locale locale){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		 Form1C form1C =form1CRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form1C", id));
		 form1C.setActiveFlag('N');
		 form1CRepository.save(form1C);
		 DatabaseContextHolder.restoreCtx();
	 }

	

	@Override
	public Response getAllForm1CData(String cCode, String pin,Integer clause,HttpServletRequest request,Locale locale) {
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
		List<Object[]> listOfForm1c =new ArrayList<>();

		if(pin.isEmpty()) {
			listOfForm1c= form1CRepository.findByForm1C(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
					request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
					cCode,clause,'Y');
		}else {
			listOfForm1c= form1CRepository.findAllForm1c(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
					request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
					cCode,pin,clause,'Y');
		}
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(listOfForm1c)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}

		Response form1CResponse =new  Response();
		form1CResponse.setForm1CData(listOfForm1c.stream().map((object) -> {
			Form1CDao form1CDao = new Form1CDao();
			form1CDao.setHissaNo(object[0].toString());
			form1CDao.setKhataOwnerName(object[1].toString());
			form1CDao.setTotalAreaH(BigDecimal.valueOf(Double.valueOf(object[2].toString())));
			form1CDao.setAssessment(BigDecimal.valueOf(Double.valueOf(object[3].toString())));
			form1CDao.setAadeshOnDate(object[4] ==null?  "":object[4].toString());
			if(object[5] !=null) {
				form1CDao.setId(Long.valueOf(object[5].toString()));
			}
			form1CDao.setcCode(object[6].toString());
			form1CDao.setAuthorityCompetentToPermitTransferOfLand(object[7] ==null?  "":object[7].toString());
			form1CDao.setDetailsOfUnclaimedAmount(object[8] ==null?  "":object[8].toString());
			form1CDao.setBreachOfCondition(object[9] ==null?  "":object[9].toString());
			form1CDao.setInspectingOfficer(object[10] ==null?  "":object[10].toString());
			if(object[11]!=null) {
				form1CDao.setInspectionDate(Date.valueOf(object[11].toString()));
			}
			form1CDao.setNameOfVillagePanchayat(object[12] ==null?  "":object[12].toString());
			form1CDao.setTermsOfGrant(object[13] ==null?  "":object[13].toString());
			form1CDao.setRemarks(object[14] ==null?  "":object[14].toString());
			form1CDao.setPin(object[15] ==null?  "":object[15].toString());
			form1CDao.setClause(object[16] ==null?  "":object[16].toString());
			form1CDao.setPurposeOfLandIntended(object[17] ==null?  "":object[17].toString());
			return form1CDao;

		}).sorted(Comparator.comparing(Form1CDao:: getIntPin)).collect(Collectors.toList()));
		return form1CResponse;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}

	@Override
	public Response getForm1CReport( String cCode, HttpServletRequest request,Locale locale) {
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
		List<Object[]> form1C = form1CRepository.findByForm1CReport(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode,'Y');
		DatabaseContextHolder.restoreCtx();
  	if(Collections.isEmpty(form1C)) {
		throw new ResourceNotFoundException("error.form.reportdetails",messageSource,locale);
	}
  	else {
      	Response form1CResponse =new  Response();
      	form1CResponse.setForm1CData(form1C.stream().map((object) -> {
      		Form1CDao form1CDao = new Form1CDao();
      		form1CDao.setHissaNo(object[0].toString());
			form1CDao.setKhataOwnerName(object[1].toString());
			form1CDao.setTotalAreaH(BigDecimal.valueOf(Double.valueOf(object[2].toString())));
			form1CDao.setAssessment(BigDecimal.valueOf(Double.valueOf(object[3].toString())));
			form1CDao.setAadeshOnDate(object[4] ==null?  "":object[4].toString());
			if(object[5] !=null) {
				form1CDao.setId(Long.valueOf(object[5].toString()));
			}
			form1CDao.setcCode(object[6].toString());
			form1CDao.setAuthorityCompetentToPermitTransferOfLand(object[7] ==null?  "":object[7].toString());
			form1CDao.setDetailsOfUnclaimedAmount(object[8] ==null?  "":object[8].toString());
			form1CDao.setBreachOfCondition(object[9] ==null?  "":object[9].toString());
			form1CDao.setInspectingOfficer(object[10] ==null?  "":object[10].toString());
			if(object[11]!=null) {
				form1CDao.setInspectionDate(Date.valueOf(object[11].toString()));
			}
			form1CDao.setNameOfVillagePanchayat(object[12] ==null?  "":object[12].toString());
			form1CDao.setTermsOfGrant(object[13] ==null?  "":object[13].toString());
			form1CDao.setRemarks(object[14] ==null?  "":object[14].toString());
			form1CDao.setPin(object[15] ==null?  "":object[15].toString());
			form1CDao.setClause(object[16] ==null?  "":object[16].toString());
			form1CDao.setPurposeOfLandIntended(object[17] ==null?  "":object[17].toString());
      		return form1CDao;
      		
      	}).collect(Collectors.toList()));
      	return form1CResponse;
	}}else {
		throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
	}
	}
		
	@Override
	public List<Form1CDao> getClauses(HttpServletRequest request){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<Form1CDao> form1CDaoLst =new ArrayList<>();
		List<Object[]> form1CDtl=form1CRepository.getClauses(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword
				
				);
		DatabaseContextHolder.restoreCtx();
		if(!form1CDtl.isEmpty()) {
			form1CDaoLst=form1CDtl.stream().map((object) -> {
				Form1CDao form1CDao =new Form1CDao();
				form1CDao.setCodeNo(object[0].toString());
				form1CDao.setClause(object[1].toString());
				return form1CDao;
			}).collect(Collectors.toList());

			return form1CDaoLst;	
		}
		return null;
	}



		
}