/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.Form19Dao;
import com.probity.echawdi.entity.Form19;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form19Repository;
import com.probity.echawdi.service.Form19Service;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Rushikesh Durgude
 *
 */
@Component
public class Form19ServiceImpl implements Form19Service {

	@Autowired
	private Form19Repository form19Repository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	
	@Override
	public MessageResponse save(Form19Dao form19Dao,HttpServletRequest request,Locale locale) {
				dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
        		Form19 form19 = toEntity(form19Dao);
        		form19Repository.save(form19);
        		DatabaseContextHolder.restoreCtx();
        		return MessageResponse.builder().status("SUCCESS").message(
        				messageSource.getMessage("error.form.savedata", null, locale)).build();	
        	}
        	
    @Override
	public Response getAllForm19Data(String userId,String sajjaCode,HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
      	List<Form19> form19Data = new ArrayList<>();
      	if(!userId.isEmpty()) {
      		form19Data = form19Repository.findByUserIdAndActiveFlag(userId,'Y');
      	}
      	if(!sajjaCode.isEmpty()) {
      		form19Data = form19Repository.findBySajjaCodeAndActiveFlag(sajjaCode,'Y');
      	}
     	DatabaseContextHolder.restoreCtx();	
      	if(Collections.isEmpty(form19Data)) {
      		throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
    	
      	}
      	Response form19Response =new  Response();
      	form19Response.setForm19Data(form19Data.stream().map(form19AR -> toDao(form19AR)).collect(Collectors.toList()));
      	return form19Response;
	}

    @Override 
	public void updateForm19(Form19Dao form19Dao,HttpServletRequest request,Locale locale)
	{
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));

		Form19 form19 = form19Repository.findById(form19Dao.getId()).
				orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form19", form19Dao.getId()));
		    BeanUtils.copyProperties(form19Dao, form19);
			form19Repository.save(form19);
			DatabaseContextHolder.restoreCtx();
	}
	
	@Override
	public void deleteForm19ById(long id,HttpServletRequest request,Locale locale){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		 
		Form19 form19 =form19Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form19",id));
		 form19.setActiveFlag('N');
		 form19Repository.save(form19);
		 DatabaseContextHolder.restoreCtx();
	 }



    private Form19 toEntity(Form19Dao form19Dao){
    	Form19 form19 = new Form19();
    	BeanUtils.copyProperties(form19Dao, form19,"id");
    	return form19;
    }
    private Form19Dao toDao(Form19 form19){
        Form19Dao form19Dao = new Form19Dao();
        BeanUtils.copyProperties(form19, form19Dao,"dateOfPurchase","dateOfCreditAtTreasury");
        if(form19.getDateOfPurchase()!= null) {
        	form19Dao.setDateOfPurchase(form19.getDateOfPurchase());
        }
        if(form19.getDateOfCreditAtTreasury()!=null) {
        	form19Dao.setDateOfCreditAtTreasury(form19.getDateOfCreditAtTreasury());
        }
        return form19Dao;
    }
}
