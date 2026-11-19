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
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.RevenueYearDao;
import com.probity.echawdi.entity.RevenueYear;
import com.probity.echawdi.exception.FieldValidationException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.RevenueYearRepository;
import com.probity.echawdi.service.RevenueYearService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;
import com.probity.echawdi.utils.DataSourceType;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Bhagyashri Lolage
 *
 */

@Component
public class  RevenueYearServiceImpl implements RevenueYearService{

	@Autowired
	private RevenueYearRepository revenueYearRepository;

	@Autowired
	private DataSourceKeeper dataSourceKeeper;

	@Autowired
	private MessageSource messageSource;

	@Override
	public MessageResponse save(RevenueYearDao revenueYearDao,Locale locale,HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		if(revenueYearDao.getRevenueYear() != null) {
			RevenueYear revenueYear = toEntity(revenueYearDao);
			revenueYearRepository.save(revenueYear);
			DatabaseContextHolder.restoreCtx();
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.savedata", null, locale)).build();	
		}
		DatabaseContextHolder.restoreCtx();
		throw new FieldValidationException("error.form.errorwhilesave");
	}
	//	
	//	
	//	@Override 
	//	public void updateRevenueYear(RevenueYearDao revenueYearDao,Locale locale)
	//	{
	//		RevenueYear revenueYear = revenueYearRepository.findById(revenueYearDao.getId()).
	//									  orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "revenueYear", revenueYearDao.getId()));
	//			revenueYear=toEntity(revenueYearDao);
	//	     	//System.out.println(revenueYear);
	//			revenueYearRepository.save(revenueYear);
	//	}
		
		
		@Override
		public void deleteRevenueYearById(long id,Locale locale,HttpServletRequest request){
			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
			RevenueYear revenueYear =revenueYearRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "revenueYear", id));
			 revenueYear.setActiveFlag('N');
			 revenueYearRepository.save(revenueYear);
			 DatabaseContextHolder.restoreCtx();
		 }
	
		
	//	@Override
	//	public RevenueYearDao getRevenueYearById(long id,Locale locale) {
	//		RevenueYear revenueYear =revenueYearRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "revenueYear", id));
	//		return toDao(revenueYear);
	//	}

	@Override
	public Response getRevenueYearData(HttpServletRequest request,Locale locale) {
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		DatabaseContextHolder.setCtx(DataSourceType.echmst);
		List<Object[]> revenueYear = revenueYearRepository.findByActiveFlag();
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(revenueYear)) {
			throw new ResourceNotFoundException("error.form.revenueyear",messageSource,locale);
		}else {
			Response revenueYearResponse =new  Response();
			revenueYearResponse.setRevenueYearData(revenueYear.stream().map((object) -> {   
				RevenueYearDao revenueYearDao=new RevenueYearDao();
				revenueYearDao.setRevenueYear(object[0].toString());
				Character activeFlag = object[1].toString().charAt(0);
				revenueYearDao.setActiveFlag(activeFlag);
				return revenueYearDao;
			}).collect(Collectors.toList()));
			return revenueYearResponse;
		}
	}


	//    private RevenueYearDao toDao(RevenueYear revenueYear){
	//    	RevenueYearDao revenueYearDao = new RevenueYearDao();
	//        BeanUtils.copyProperties(revenueYear, revenueYearDao);
	//        return revenueYearDao;
	//    }

	private RevenueYear toEntity(RevenueYearDao revenueYearDao){
		RevenueYear revenueYear = new RevenueYear();
		BeanUtils.copyProperties(revenueYearDao, revenueYear);
		return revenueYear;
	}
}