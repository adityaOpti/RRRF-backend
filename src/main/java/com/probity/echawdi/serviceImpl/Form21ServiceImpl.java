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
import com.probity.echawdi.dao.Form21Dao;
import com.probity.echawdi.entity.Form21;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form21Repository;
import com.probity.echawdi.service.Form21Service;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Rushikesh Durgude
 *
 */
@Component
public class Form21ServiceImpl implements Form21Service{

	@Autowired
	private Form21Repository form21Repository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;

	@Override
	public MessageResponse save(Form21Dao form21Dao,HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form21 form21 = toEntity(form21Dao);
		form21Repository.save(form21);
		DatabaseContextHolder.restoreCtx();
		return MessageResponse.builder().status("SUCCESS").message(
				messageSource.getMessage("error.form.savedata", null, locale)).build();	
	}


	@Override
	public Response getAllForm21Data( String userId,int pageNo, int pageSize, String sortBy, String sortDir,HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Form21> form21 = form21Repository.findByUserIdAndActiveFlag(userId,'Y',pageable);
		DatabaseContextHolder.restoreCtx();
		List<Form21> listOfForm21 = form21.getContent();
		if(Collections.isEmpty(listOfForm21)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);

		}
		Response form21Response =new  Response();
		form21Response.setForm21Data(listOfForm21.stream().map(form21AR -> toDao(form21AR)).collect(Collectors.toList()));
		form21Response.setPageNo(form21.getNumber());
		form21Response.setPageSize(form21.getSize());
		form21Response.setTotalElements(form21.getTotalElements());
		form21Response.setTotalPages(form21.getTotalPages());
		form21Response.setLast(form21.isLast());
		return form21Response;
	}



	@Override 
	public void updateForm21(Form21Dao form21Dao,HttpServletRequest request,Locale locale)
	{
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form21 form21 = form21Repository.findById(form21Dao.getId()).
				orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form21", form21Dao.getId()));
		BeanUtils.copyProperties(form21Dao, form21);
		form21Repository.save(form21);
		DatabaseContextHolder.restoreCtx();
	}

	@Override
	public void deleteForm21ById(long id,HttpServletRequest request,Locale locale){
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form21 form21 =form21Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form21",id));
		form21.setActiveFlag('N');
		form21Repository.save(form21);
		DatabaseContextHolder.restoreCtx();
	}



	private Form21 toEntity(Form21Dao form21Dao){
		Form21 form21 = new Form21();
		BeanUtils.copyProperties(form21Dao, form21);
		return form21;
	}
	private Form21Dao toDao(Form21 form21){
		Form21Dao form21Dao = new Form21Dao();
		BeanUtils.copyProperties(form21, form21Dao);
		return form21Dao;
	}

}
