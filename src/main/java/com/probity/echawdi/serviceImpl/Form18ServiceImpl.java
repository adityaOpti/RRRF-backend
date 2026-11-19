/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.Form18Dao;
import com.probity.echawdi.entity.Form18;
import com.probity.echawdi.exception.FieldValidationException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form18Repository;
import com.probity.echawdi.service.Form18Service;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Rushikesh Durgude
 *
 */
@Component
public class Form18ServiceImpl implements Form18Service{

	@Autowired
	private Form18Repository form18Repository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	
	@Override
	public MessageResponse save(Form18Dao form18Dao,HttpServletRequest request,Locale locale) {
        if(form18Dao.getcCode() != null) {
			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
        	Form18 form18 = toEntity(form18Dao);
        		form18Repository.save(form18);
        		DatabaseContextHolder.restoreCtx();	
        		return MessageResponse.builder().status("SUCCESS").message(
        				messageSource.getMessage("error.form.savedata", null, locale)).build();	
            }
            throw new FieldValidationException("error.form.errorwhilesave");
        	}
        	
    @Override
	public Response getAllForm18Data(Integer districtCode,Integer talukaCode,String cCode, int pageNo, int pageSize, String sortBy, String sortDir,HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
      	Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      	Page<Form18> form18 = form18Repository.findBycCodeAndDistrictCodeAndTalukaCodeAndActiveFlag(cCode,districtCode,talukaCode,'Y',pageable);
      	DatabaseContextHolder.restoreCtx();
      	List<Form18> listOfForm18 = form18.getContent();
      	if(Collections.isEmpty(listOfForm18)) {
      		throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
    	
      	}
      	Response form18Response =new  Response();
      	form18Response.setForm18Data(listOfForm18.stream().map(form18AR -> toDao(form18AR)).collect(Collectors.toList()));
      	form18Response.setPageNo(form18.getNumber());
      	form18Response.setPageSize(form18.getSize());
      	form18Response.setTotalElements(form18.getTotalElements());
      	form18Response.setTotalPages(form18.getTotalPages());
      	form18Response.setLast(form18.isLast());
      	return form18Response;
	}

	@Override 
	public void updateForm18(Form18Dao form18Dao,HttpServletRequest request,Locale locale)
	{
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form18 form18 = form18Repository.findById(form18Dao.getId()).
				orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form18", form18Dao.getId()));
		    BeanUtils.copyProperties(form18Dao, form18);
			form18Repository.save(form18);
			DatabaseContextHolder.restoreCtx();
	}
	
	@Override
	public void deleteForm18ById(long id,HttpServletRequest request,Locale locale){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form18 form18 =form18Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form18",id));
		 form18.setActiveFlag('N');
		 form18Repository.save(form18);
		 DatabaseContextHolder.restoreCtx(); 
	 }


   
    private Form18 toEntity(Form18Dao form18Dao){
    	Form18 form18 = new Form18();
    	BeanUtils.copyProperties(form18Dao, form18);
    	return form18;
    }
    private Form18Dao toDao(Form18 form18){
        Form18Dao form18Dao = new Form18Dao();
        BeanUtils.copyProperties(form18, form18Dao);
        return form18Dao;
    }
  

}
