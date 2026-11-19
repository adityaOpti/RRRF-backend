package com.probity.echawdi.serviceImpl;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.Form1EDao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.entity.Form1E;
import com.probity.echawdi.exception.FieldValidationException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form1ERepository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.service.Form1EService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.CommonConstants.DateFormats;
import com.probity.echawdi.utils.CommonConstants.FileName;
import com.probity.echawdi.utils.DataSourceKeeper;
import com.probity.echawdi.utils.StaticPropertyFetcher;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Rushikesh Durgude
 *
 */

@Component
public class  Form1EServiceImpl implements Form1EService{

	@Autowired
	private Form1ERepository form1ERepository;
	@Autowired
	private MessageSource  messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;
	
	
	@Override
	public Response getAllForm1EData(String cCode, String pin,HttpServletRequest request,Locale locale) {
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
      	List<Form1E> form1E = form1ERepository.findBycCodeAndPinAndActiveFlag(cCode,pin,'Y');
      	if(Collections.isEmpty(form1E)) {
      		throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
      	}
      	
      	Response form1EResponse =new  Response();
      	form1EResponse.setForm1EData(form1E.stream().map((form1ER) -> {
			try {
				return toDao(form1ER);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}).collect(Collectors.toList()));
      	DatabaseContextHolder.restoreCtx();
      	return form1EResponse;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}
	
	@Override
	public Response getForm1EReport(String cCode,HttpServletRequest request,Locale locale) throws IOException{
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
		List<Form1E> form1E = form1ERepository.findBycCodeAndActiveFlag(cCode,'Y');
		if(Collections.isEmpty(form1E) || form1E ==null) {
			throw new ResourceNotFoundException("error.form.reportdetails",messageSource,locale);
		}
		else {
		Response form1EResponse =new  Response();
		form1EResponse.setForm1EData(form1E.stream().map((object) -> {
			try {
				return toDao(object);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}).collect(Collectors.toList()));
		DatabaseContextHolder.restoreCtx();
		return form1EResponse;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}
	
	@Override
	public void deleteForm1EById(long id,HttpServletRequest request,Locale locale){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form1E form1E =form1ERepository.findById(id).
				        orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form1E", id));
		 form1E.setActiveFlag('N');
		 form1ERepository.save(form1E);
		 DatabaseContextHolder.restoreCtx(); 
	 }

	
	@Override
	public Form1EDao getForm1EById(long id,HttpServletRequest request,Locale locale) throws IOException {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form1E form1E =form1ERepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form1E", id));
		DatabaseContextHolder.restoreCtx();
		return toDao(form1E);
	}

	@Override 
	public MessageResponse updateForm1E(Form1EDao form1EDao,HttpServletRequest request, Locale locale){
		try {
			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
			Form1E form1E =form1ERepository.getById(form1EDao.getId());
			form1E.setNameOfEncroacher(form1EDao.getNameOfEncroacher());
			form1E.setDateOfEncroachment(form1EDao.getDateOfEncroachment());
			form1E.setPurposeOfEncroachmentLand(form1EDao.getPurposeOfEncroachmentLand());
			form1E.setYearFromWhichRentRecoverable(form1EDao.getYearFromWhichRentRecoverable());
			form1E.setDateOfDecisionOnEncroachment(form1EDao.getDateOfDecisionOnEncroachment());
			form1E.setYearOfEncroachmentNotedIfRemoved(form1EDao.getYearOfEncroachmentNotedIfRemoved());
			form1E.setAreaOfEncroached(form1EDao.getAreaOfEncroached());
			form1E.setRemarks(form1EDao == null ? "" : form1EDao.getRemarks());
			
			form1ERepository.save(form1E);
			DatabaseContextHolder.restoreCtx();
			return MessageResponse.builder().message("Updated Successfully").status("SUCCESS").build();
		}catch(Exception e){
			throw new FieldValidationException("error.while.save");
		}
	}
    
    public MessageResponse saveForm1E(String form1EDao, MultipartFile file,HttpServletRequest request,Locale locale) throws JsonMappingException, JsonProcessingException {
    	
    	dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		
    	String filePath=null;
    	if(file!=null) {
    		
    		String newFileName = new SimpleDateFormat(DateFormats.FILE_NAME_DATE_FORMAT).format(new Date());
    		String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'),file.getOriginalFilename().length());
    		
    		try {
    			//System.out.println("Not Uploaded");
    			//System.out.println("Uploaded");
//    			if(form1EDaos.getcCode() != null) {
    				boolean flag=false;
    				if (!file.getOriginalFilename().isEmpty()&& extension.toLowerCase().matches(CommonConstants.Matcher.validFileMatcher)) {
    					if(extension.toLowerCase().matches(CommonConstants.Matcher.imageMatcher)) {
    						try {
    							BufferedImage image = ImageIO.read(file.getInputStream());
    							image.getData();
    							flag=true;
    							
    						} catch (Exception e) {
    							e.printStackTrace();
    							throw new FieldValidationException("error.form.errorwhilesave");
    						}								
    						
    					}else if(extension.toLowerCase().matches(CommonConstants.Matcher.pdfMather)){
    						try {
    							PDDocument document = PDDocument.load(file.getInputStream());
    							document.getDocument();
    							flag=true;
    						} catch (Exception e) {
    							e.printStackTrace();
    							throw new FieldValidationException("error.form.errorwhilesave");
    							
    						}
    					}
    					else {
    						throw new FieldValidationException("error.form.errorwhilesave");
    					}					
    					if(flag) {
    						
    						File dir = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "Form1E"));
    						if(!dir.exists()) {
    							dir.mkdirs();
    						}
    						
    						BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream
    								(new File(dir, newFileName+extension)));
    						outputStream.write(file.getBytes());
    						outputStream.flush();
    						outputStream.close();
    						filePath=newFileName+extension;
    						throw new FieldValidationException("error.form.errorwhilesave");
    					}
    					throw new FieldValidationException("error.form.errorwhilesave");
    				}
    				throw new FieldValidationException("error.form.errorwhilesave");
//    			}
//    			throw new FieldValidationException("error.form.errorwhilesave");
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    			throw new FieldValidationException("error.form.errorwhilesave");
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
		}
    	
    	
		if(form1EDao!=null) {
			ObjectMapper  objMapper = new ObjectMapper().registerModule(new JavaTimeModule());
			Form1EDao form1EDaos=objMapper.readValue(form1EDao, Form1EDao.class);
			if(form1EDaos.getDateOfDecisionOnEncroachment().toString().equals("1992-02-29")){
				form1EDaos.setDateOfDecisionOnEncroachment(null);
			}
			Form1E form1E=toEntity(form1EDaos);
			
			if(filePath!=null) {
				form1E.setAreaOfEncroachedPath(filePath);
			}
			
			if(form1EDaos.getHissaNo() != null) {
				String [] hissaNoo = form1EDaos.getHissaNo().split("/");
				if(hissaNoo != null && hissaNoo.length > 0 && hissaNoo.length < 8) {

					String  tHissaNo = form1EDaos.getHissaNo();
					for (int i = hissaNoo.length; i <= 8; i++) {
						tHissaNo += " /";
					}
					hissaNoo = tHissaNo.split("/");
				}
				List<String> lstHissNo = Arrays.asList(hissaNoo);
				form1E.setPin1(lstHissNo.get(0));
				form1E.setPin2(lstHissNo.get(1));
				form1E.setPin3(lstHissNo.get(2));
				form1E.setPin4(lstHissNo.get(3));
				form1E.setPin5(lstHissNo.get(4));
				form1E.setPin6(lstHissNo.get(5));
				form1E.setPin7(lstHissNo.get(6));
				form1E.setPin8(lstHissNo.get(7));
			}
			form1ERepository.save(form1E);
			DatabaseContextHolder.restoreCtx();
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.savedata", null, locale)).build();	
	    }
    	
		return null;
		
	}
    
    private Form1EDao toDao(Form1E form1E) throws IOException{
        Form1EDao form1EDao = new Form1EDao();
        BeanUtils.copyProperties(form1E, form1EDao);
    	if(form1EDao.getPin()!=null) {
			String hissaNo = Stream.of(
					form1E.getPin1().trim().isEmpty() ? null : form1E.getPin1(),  
							form1E.getPin2().trim().isEmpty() ? null : "/"+form1E.getPin2(), 
									form1E.getPin3().trim().isEmpty() ? null : "/"+form1E.getPin3(),
											form1E.getPin4().trim().isEmpty() ? null : "/"+form1E.getPin4(),
													form1E.getPin5().trim().isEmpty() ? null : "/"+form1E.getPin5(),
															form1E.getPin6().trim().isEmpty() ? null : "/"+form1E.getPin6(),
																	form1E.getPin7().trim().isEmpty() ? null : "/"+form1E.getPin7(),
																			form1E.getPin8().trim().isEmpty() ? null : "/"+form1E.getPin8())
			.filter(s -> s != null)
			.collect(Collectors.joining());
			form1EDao.setHissaNo(hissaNo.isEmpty() ? "" : hissaNo);
    	}
        if(form1E.getAreaOfEncroachedPath()!=null) {
        	File file = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "Form1E")+form1E.getAreaOfEncroachedPath());
        	form1EDao.setAttachment(Base64.encodeBase64(Files.readAllBytes(file.toPath())));
        }
        return form1EDao;
    }

    private Form1E toEntity(Form1EDao form1EDao){
    	Form1E form1E = new Form1E();
    	BeanUtils.copyProperties(form1EDao, form1E,"id");
    	return form1E;
    }
}