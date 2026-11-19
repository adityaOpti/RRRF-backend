/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.Form15Dao;
import com.probity.echawdi.entity.Form15;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form15Repository;
import com.probity.echawdi.service.Form15Service;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Bhagyashri Lolage
 *
 */
@Component
public class Form15ServiceImpl implements Form15Service {

	@Autowired
	private Form15Repository form15Repository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	
	@Override
	public MessageResponse save(Form15Dao form15Dao,HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		
		Form15 form15= toEntity(form15Dao);
        		form15Repository.save(form15);
        		DatabaseContextHolder.restoreCtx();	
        		return MessageResponse.builder().status("SUCCESS").message(
        				messageSource.getMessage("error.form.savedata", null, locale)).build();	
            }
            
	
        	
    @Override
	public Response getAllForm15Data(String sajjaCode,HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<Form15> listOfForm15=new ArrayList<>();
		if(!sajjaCode.isEmpty()) {
		 listOfForm15= form15Repository.findBySajjaCodeAndActiveFlag(sajjaCode,'Y');
		}
//		if(!userId.isEmpty()) {
//		listOfForm15= form15Repository.findByUserIdAndActiveFlag(userId,'Y');
//		}
      	DatabaseContextHolder.restoreCtx();	
      	if(Collections.isEmpty(listOfForm15)) {
      		throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
      	}
      	
      //new line for sorting date wise 15/04/2026
      	
      	listOfForm15.sort(Comparator.comparing(Form15::getCreateDtTm));
      	
      	Response form15Response =new  Response();
      	form15Response.setForm15Data(listOfForm15.stream().map(form15AR -> toDao(form15AR)).collect(Collectors.toList()));
      	return form15Response;
	}

    @Override 
	public void updateForm15(Form15Dao form15Dao,HttpServletRequest request, Locale locale)
	{
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));

		Form15 form15 = form15Repository.findById(form15Dao.getId()).
				orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"form15", form15Dao.getId()));
		    BeanUtils.copyProperties(form15Dao, form15);
		    form15Repository.save(form15);
		    DatabaseContextHolder.restoreCtx();	
	}
	
	@Override
	public void deleteForm15ById(long id,HttpServletRequest request, Locale locale){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
 
		Form15 form15 =form15Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"form15",id));
		 form15.setActiveFlag('N');
		 form15Repository.save(form15);
		 DatabaseContextHolder.restoreCtx();	
	 }

	
    private Form15Dao toDao(Form15 form15){
    	Form15Dao form15Dao = new Form15Dao();
    	BeanUtils.copyProperties(form15, form15Dao,"dateOfDispatch");
    	 if(form15.getDateOfDispatch()!= null) {
         	form15Dao.setDateOfDispatch(form15.getDateOfDispatch());
         }
    	return form15Dao;
    }
    private Form15 toEntity(Form15Dao form15Dao){
    	Form15 form15 = new Form15();
    	BeanUtils.copyProperties(form15Dao, form15,"id");
    	return form15;
    }

   
}
