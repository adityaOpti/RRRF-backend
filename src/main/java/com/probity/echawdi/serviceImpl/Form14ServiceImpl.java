/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.lang.reflect.Field;
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
import com.probity.echawdi.dao.Form14Dao;
import com.probity.echawdi.dao.Form17Dao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.entity.Form14;
import com.probity.echawdi.exception.FieldValidationException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form14Repository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.service.Form14Service;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Bhagyashri Lolage
 *
 */
@Component
public class Form14ServiceImpl implements Form14Service {

	@Autowired
	private Form14Repository form14Repository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;

	@Override
	public Response getAllForm14Data(HttpServletRequest request,String cCode,Locale locale) {
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
		List<Form14> listOfForm14 = form14Repository.findAllBycCodeAndActiveFlag(cCode,'Y');
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(listOfForm14)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}
		Response form14Response =new  Response();
		form14Response.setForm14Data(listOfForm14.stream().map(form14S -> toDao(form14S)).collect(Collectors.toList()));
		return form14Response;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}
	
	@Override
	public Response getForm14GhoshwaraReport(HttpServletRequest request,String cCode,Locale locale) {
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
		List<Object[]> form14Ghoshwara=new ArrayList<>();
		form14Ghoshwara = form14Repository.getForm14GhoshwaraReport(request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				 cCode,'Y');
		DatabaseContextHolder.restoreCtx();
		
		if(Collections.isEmpty(form14Ghoshwara)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}
		Response response1 =new  Response();
		response1.setForm14Data(form14Ghoshwara.stream().map((object) -> {
			Form14Dao form14Dao = new Form14Dao();
			form14Dao.setcCode(object[0].toString());
			form14Dao.setGovCanalPakkaInUse(object[1] == null ? "0" : object[1].toString());
			form14Dao.setGovCanalPakkaNotUse(object[2] == null ? "0" : object[2].toString());
			form14Dao.setGovCanalPakkaIsThrown(object[3] == null ? "0" : object[3].toString());
			form14Dao.setGovCanalPakkaIsThrownUnRepaired(object[4] == null ? "0" : object[4].toString());
			form14Dao.setGovCanalPakkaIsThrownOtherReason(object[5] == null ? "0" : object[5].toString());
			form14Dao.setGovCanalKacchaInUse(object[6] == null ? "0" : object[6].toString());
			form14Dao.setGovCanalKacchaNotUse(object[7] == null ? "0" : object[7].toString());
			form14Dao.setGovCanalKacchaIsThrown(object[8] == null ? "0" : object[8].toString());
			form14Dao.setPrivateCanalPakkaInUse(object[9] == null ? "0" : object[9].toString());
			form14Dao.setPrivateCanalPakkaNotUse(object[10] == null ? "0" : object[10].toString());
			form14Dao.setPrivateCanalPakkaIsThrown(object[11] == null ? "0" : object[11].toString());
			form14Dao.setPrivateCanalPakkaIsThrownUnRepaired(object[12] == null ? "0" : object[12].toString());
			form14Dao.setPrivateCanalPakkaIsThrownOtherReason(object[13] == null ? "0" : object[13].toString());
			form14Dao.setPrivateCanalKacchaInUse(object[14] == null ? "0" : object[14].toString());
			form14Dao.setPrivateCanalKacchaNotUse(object[15] == null ? "0" : object[15].toString());
			form14Dao.setPrivateCanalKacchaIsThrown(object[16] == null ? "0" : object[16].toString());
			form14Dao.setLakePakkaInUse(object[17] == null ? "0" : object[17].toString());
			form14Dao.setLakePakkaNotUse(object[18] == null ? "0" : object[18].toString());
			form14Dao.setLakePakkaIsThrown(object[19] == null ? "0" : object[19].toString());
			form14Dao.setLakePakkaIsThrownUnRepaired(object[20] == null ? "0" : object[20].toString());
			form14Dao.setLakePakkaIsThrownOtherReason(object[21] == null ? "0" : object[21].toString());
			form14Dao.setLakeKacchaInUse(object[22] == null ? "0" : object[22].toString());
			form14Dao.setLakeKacchaNotUse(object[23] == null ? "0" : object[23].toString());
			form14Dao.setLakeKacchaIsThrown(object[24] == null ? "0" : object[24].toString());
			form14Dao.setWellPakkaInUse(object[25] == null ? "0" : object[25].toString());
			form14Dao.setWellPakkaNotUse(object[26] == null ? "0" : object[26].toString());
			form14Dao.setWellPakkaIsThrown(object[27] == null ? "0" : object[27].toString());
			form14Dao.setWellPakkaIsThrownUnRepaired(object[28] == null ? "0" : object[28].toString());
			form14Dao.setWellPakkaIsThrownOtherReason(object[29] == null ? "0" : object[29].toString());
			form14Dao.setWellKacchaInUse(object[30] == null ? "0" : object[30].toString());
			form14Dao.setWellKacchaNotUse(object[31] == null ? "0" : object[31].toString());
			form14Dao.setWellKacchaIsThrown(object[32] == null ? "0" : object[32].toString());
			form14Dao.setOtherPakkaInUse(object[33] == null ? "0" : object[33].toString());
			form14Dao.setOtherPakkaNotUse(object[34] == null ? "0" : object[34].toString());
			form14Dao.setOtherPakkaIsThrown(object[35] == null ? "0" : object[35].toString());
			form14Dao.setOtherPakkaIsThrownUnRepaired(object[36] == null ? "0" : object[36].toString());
			form14Dao.setOtherPakkaIsThrownOtherReason(object[37] == null ? "0" : object[37].toString());
			form14Dao.setOtherKacchaInUse(object[38] == null ? "0" : object[38].toString());
			form14Dao.setOtherKacchaNotUse(object[39] == null ? "0" : object[39].toString());
			form14Dao.setOtherKacchaIsThrown(object[40] == null ? "0" : object[40].toString());
			return form14Dao;
		}).collect(Collectors.toList()));
		return response1;	
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}

	@Override
	public MessageResponse save(HttpServletRequest request,Form14Dao form14Dao,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		if(form14Dao.getcCode() != null) {
			Form14 form14 = toEntity(form14Dao);
			if(form14Dao.getHissaNo() != null) {
				String [] hissaNoo = form14Dao.getHissaNo().split("/");
				if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {
					String  tHissaNo = form14Dao.getHissaNo();
					for (int i = hissaNoo.length; i <= 8; i++) {
						tHissaNo += " /";
					}
					hissaNoo = tHissaNo.split("/");
				}
				List<String> lstHissNo = Arrays.asList(hissaNoo);
				form14.setPin1(lstHissNo.get(0));
				form14.setPin2(lstHissNo.get(1));
				form14.setPin3(lstHissNo.get(2));
				form14.setPin4(lstHissNo.get(3));
				form14.setPin5(lstHissNo.get(4));
				form14.setPin6(lstHissNo.get(5));
				form14.setPin7(lstHissNo.get(6));
				form14.setPin8(lstHissNo.get(7));
			}
			if(form14Dao.getPakka()!=null) {
				form14.setPakka(form14Dao.getPakka());
			}
			if(form14Dao.getKaccha() !=null) {
				form14.setKaccha(form14Dao.getKaccha());
			}
			form14Repository.save(form14);
			DatabaseContextHolder.restoreCtx();
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.savedata", null, locale)).build();	
		}
		throw new FieldValidationException("error.form.errorwhilesave");
	}


	//form14 Added 4 sep 2024
	
		 public static void copyPropertiesWithoutNull(Form14Dao source, Form14 destination) throws IllegalAccessException {
		        Field[] fields = Form14Dao.class.getDeclaredFields();
		        
		        for (Field field : fields) {
		            field.setAccessible(true); // Enable access to private fields
		            
		            Object value = field.get(source);
		            if (value != null) {
		                try {
		                    Field destinationField = Form14.class.getDeclaredField(field.getName());
		                    destinationField.setAccessible(true); // Enable access to private fields in destination
		                    destinationField.set(destination, value);
		                } catch (NoSuchFieldException e) {
		                    // Handle case where field doesn't exist in destination or other exceptions
		                    e.printStackTrace();
		                }
		            }
		        }
		    }

		
		//end of new method
	
	
	@Override 
	public void updateForm14(HttpServletRequest request,Form14Dao form14Dao,Locale locale)
	{
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form14 form14 = form14Repository.findById(form14Dao.getId()).
				orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form14", form14Dao.getId()));
		//old one 4 sept 2024
		//BeanUtils.copyProperties(form14Dao, form14);
		
		
		//newly added 4 sept 2024
				try {
		            copyPropertiesWithoutNull(form14Dao, form14);
		        } catch (IllegalAccessException e) {
		            e.printStackTrace();
		        }

		//End newly added 4 sept 2024
		
		
		form14Repository.save(form14);
		DatabaseContextHolder.restoreCtx();
	}

	@Override
	public void deleteForm14ById(HttpServletRequest request,long id,Locale locale){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form14 form14 =form14Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form14",id));
		form14.setActiveFlag('N');
		form14Repository.save(form14);
		DatabaseContextHolder.restoreCtx();
	}



	@Override
	public List<Form14Dao> getForm14EferfarDetials(HttpServletRequest request,String cCode, String pin, String hissaNo, Locale locale) {
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
		List<Object[]> form14Dtl = form14Repository.getForm14EferfarDetials(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				cCode,pin,hissaNo);
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(form14Dtl)) {
			throw new ResourceNotFoundException("error.form.eferfar14",messageSource,locale);
		}
		else {
			List<Form14Dao> lstForm14 = form14Dtl.stream().map((object) -> {

				Form14Dao form14Dao=new Form14Dao();
				form14Dao.setSourcesOfWater(object[0].toString());
				return form14Dao;
			}). collect(Collectors.toList());
			return lstForm14;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}


	public Form14Dao toDao(Form14 form14) {
		Form14Dao form14Dao=new Form14Dao();
		BeanUtils.copyProperties(form14, form14Dao);
		if(form14Dao.getPin()!=null) {

			String hissaNo = Stream.of(
					form14.getPin1().trim().isEmpty() ? null : form14.getPin1(),  
					form14.getPin2().trim().isEmpty() ? null : "/"+form14.getPin2(), 
							form14.getPin3().trim().isEmpty() ? null : "/"+form14.getPin3(),
									form14.getPin4().trim().isEmpty() ? null : "/"+form14.getPin4(),
											form14.getPin5().trim().isEmpty() ? null : "/"+form14.getPin5(),
													form14.getPin6().trim().isEmpty() ? null : "/"+form14.getPin6(),
															form14.getPin7().trim().isEmpty() ? null : "/"+form14.getPin7(),
																	form14.getPin8().trim().isEmpty() ? null : "/"+form14.getPin8())
			.filter(s -> s != null)
			.collect(Collectors.joining());
			form14Dao.setHissaNo(hissaNo.isEmpty() ? "" : hissaNo);
		}else {
			if(form14.getPin() != null ) {
				form14Dao.setPin(form14.getPin());
			}
		}
		return form14Dao;
	}


	private Form14 toEntity(Form14Dao form14Dao){
		Form14 form14 = new Form14();
		BeanUtils.copyProperties(form14Dao, form14,"id");
		if(form14Dao.getPin() != null) {
    		form14Dao.setPin(form14Dao.getPin());	
    	}
		return form14;
	}

	@Override
	public Response getAllForm14ReportDetails(HttpServletRequest request,String cCode,Locale locale) {
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
		List<Form14>  form14data = form14Repository.findAllBycCodeAndActiveFlag(cCode,'Y');
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(form14data)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}
		Response form14Response =new  Response();
		form14Response.setForm14Data(form14data.stream().map(form6B->toDao(form6B)).collect(Collectors.toList()));
		return form14Response;
//		}else {
//			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
//		}
	}
}