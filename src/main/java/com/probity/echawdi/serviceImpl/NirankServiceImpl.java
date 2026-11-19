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
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.dao.NirankDao;
import com.probity.echawdi.dao.TalathiDashBoardDao;
import com.probity.echawdi.entity.Nirank;
import com.probity.echawdi.entity.TalathiDashBoard;
import com.probity.echawdi.exception.FieldValidationException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.repository.NirankRepository;
import com.probity.echawdi.service.NirankService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;


/**
 * @author Bhagyashri Lolage
 *
 */
@Component
public class NirankServiceImpl implements NirankService {

	@Autowired
	private NirankRepository nirankRepository;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private KhataMergeRepository khataMergeRepository;

	@Override
	public MessageResponse save(NirankDao nirankDao, HttpServletRequest request, Locale locale) {
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		if(nirankDao.getcCode() != null && (nirankDao.getIsNirank()!=null || nirankDao.getIsCompleted()!=null) ) {
			Nirank nirank=new Nirank();
			if(nirankDao.getId()!=null) {
				nirank=nirankRepository.findById(nirankDao.getId()).get();
			}
			BeanUtils.copyProperties(nirankDao, nirank,"id");
			nirankRepository.save(nirank);			
			
//			Nirank nirank = toEntity(nirankDao);
//			nirankRepository.save(nirank);
//			DatabaseContextHolder.restoreCtx();
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.savedata", null, locale)).build();	
		}
		throw new FieldValidationException("error.form.errorwhilesave");
	}


	@Override
	public MessageResponse saveAll(Response requestBody, HttpServletRequest request, Locale locale) {
		if(requestBody.getNirank()!=null) {
		requestBody.getNirank().stream().map(n->{
			if(n.getcCode() != null && (n.getIsNirank()!=null || n.getIsCompleted()!=null) ) {
				Nirank nirank=new Nirank();
				if(n.getId()!=null) {
					nirank=nirankRepository.findById(n.getId()).get();
				}
				BeanUtils.copyProperties(n, nirank);
				nirankRepository.save(nirank);
				return nirank;
			}
			return null;
		}).collect(Collectors.toList());
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.savedata", null, locale)).build();	
		}
		throw new FieldValidationException("error.form.errorwhilesave");
	}



	private Nirank toEntity(NirankDao nirankDao) {
		Nirank nirank = new Nirank();
		BeanUtils.copyProperties(nirankDao,nirank);
		return nirank;
	}



	@Override
	public void editNirankandCompleted(NirankDao nirankDao, Locale locale, HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Nirank nirank = nirankRepository.findById(nirankDao.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Nirank"));
		BeanUtils.copyProperties(nirankDao, nirank);
		nirankRepository.save(nirank);
		DatabaseContextHolder.restoreCtx();
	}



	@Override
	public void deleteNirankAndCompleted(HttpServletRequest request, long id, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Nirank nirank = nirankRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Nirank",id));
		nirank.setActiveFlag('N');
		nirankRepository.save(nirank);
		DatabaseContextHolder.restoreCtx();
	}



	@Override
	public Response getNirankAndCompleted(String cCode, String revenueYear, HttpServletRequest request, Locale locale) {
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
			
			List<Object[]> nirank = nirankRepository.findBycCodeAndRevenueYearAndActiveFlag(cCode,revenueYear,'Y');
			DatabaseContextHolder.restoreCtx();
			
			Response nirankResponse =new  Response();
			nirankResponse.setNirank(nirank.stream().map(object-> {
				NirankDao nirankDao = new NirankDao();
				nirankDao.setcCode(object[0].toString());
				nirankDao.setFormId(object[1].toString());
				nirankDao.setIsNirank((Character)object[2]);
				nirankDao.setIsCompleted((Character)object[3]);
				nirankDao.setDistrictCode(object[4].toString());
				nirankDao.setTalukaCode(object[5].toString());
				
				return nirankDao;
			}).collect(Collectors.toList()));
			return nirankResponse;
			
			}else {
				throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
			}
		
		
		
	}


	private NirankDao toDao(Nirank nirank){
		NirankDao nirankDao = new NirankDao();
		BeanUtils.copyProperties(nirank, nirankDao);
		return nirankDao;
	}


	@Override
	public Response getNirnkForDashBoard(String districtCode, String talukaCode, HttpServletRequest request,
			Locale locale) {
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<Nirank> nirank = nirankRepository.findByDistrictCodeAndTalukaCode(districtCode,talukaCode);
//		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(nirank)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}	 
		Response nirankResponse =new  Response();
		nirankResponse.setNirank(nirank.stream().map(nirankData->toDao(nirankData)).collect(Collectors.toList()));
		return nirankResponse;
	}	

}