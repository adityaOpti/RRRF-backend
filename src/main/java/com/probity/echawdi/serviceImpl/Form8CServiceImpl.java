/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.Form8CDao;
import com.probity.echawdi.entity.Form8C;
import com.probity.echawdi.exception.FieldValidationException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form8CRepository;
import com.probity.echawdi.service.Form8CService;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Bhagyashri Lolage
 *
 */
@Component
public class Form8CServiceImpl implements Form8CService{
	
	@Autowired
	private Form8CRepository form8CRepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;

	@Override
	public Response getAllForm8CData(Integer districtCode, Integer talukaCode,String cCode, int pageNo, int pageSize, String sortBy, String sortDir,
			HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
      	Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      	Page<Form8C> form8C = form8CRepository.findBycCodeAndDistrictCodeAndTalukaCodeAndActiveFlag(cCode,districtCode,talukaCode, 'Y',pageable);
      	DatabaseContextHolder.restoreCtx();	
      	List<Form8C> listOfForm8C = form8C.getContent();
      	if(Collections.isEmpty(listOfForm8C)) {
      		throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
    	
      	}
      	Response form8CResponse =new  Response();
      	form8CResponse.setForm8CData(listOfForm8C.stream().map(form17AR -> toDao(form17AR)).collect(Collectors.toList()));
      	form8CResponse.setPageNo(form8C.getNumber());
      	form8CResponse.setPageSize(form8C.getSize());
      	form8CResponse.setTotalElements(form8C.getTotalElements());
      	form8CResponse.setTotalPages(form8C.getTotalPages());
      	form8CResponse.setLast(form8C.isLast());
      	return form8CResponse;
	}

	
	@Override
	public MessageResponse save(@Valid Form8CDao form8CDao,HttpServletRequest request, Locale locale) {
		 if(form8CDao.getcCode() != null) {
				dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
			 Form8C form8C = toEntity(form8CDao);
	        		form8CRepository.save(form8C);
	        		DatabaseContextHolder.restoreCtx();			
	        		return MessageResponse.builder().status("SUCCESS").message(
	        				messageSource.getMessage("error.form.savedata", null, locale)).build();	
	            }
	            throw new FieldValidationException("error.form.errorwhilesave");
	        }

	@Override
	public void updateForm8C(@Valid Form8CDao form8CDao, HttpServletRequest request,Locale locale) 	{
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form8C form8C = form8CRepository.findById(form8CDao.getId()).
				orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form8C", form8CDao.getId()));
		    BeanUtils.copyProperties(form8CDao, form8C);
		    form8CRepository.save(form8C);
		    DatabaseContextHolder.restoreCtx();	
	}

	@Override
	public void deleteForm8CById(long id,HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form8C form8C =form8CRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form8C",id));
		 form8C.setActiveFlag('N');
		 form8CRepository.save(form8C);
		 DatabaseContextHolder.restoreCtx();	 
	 }

	  private Form8CDao toDao(Form8C Form8C){
	    	Form8CDao Form8CDao = new Form8CDao();
	    	BeanUtils.copyProperties(Form8C, Form8CDao);
	    	return Form8CDao;
	    }
	    private Form8C toEntity(Form8CDao Form8CDao){
	    	Form8C Form8C = new Form8C();
	    	BeanUtils.copyProperties(Form8CDao, Form8C);
	    	return Form8C;
	    }
	

}
