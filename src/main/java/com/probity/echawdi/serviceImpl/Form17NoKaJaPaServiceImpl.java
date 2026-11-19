package com.probity.echawdi.serviceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.AkrushakRateDao;
import com.probity.echawdi.dao.Form17Dao;
import com.probity.echawdi.dao.Form17NoKaJaPaDao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.entity.Form17NoKaJaPa;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form17NoKaJaPaRepository;
import com.probity.echawdi.repository.Form17Repository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.repository.LandRevenueDemandRepository;
import com.probity.echawdi.service.Form17NoKaJaPaService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

@Component
public class Form17NoKaJaPaServiceImpl implements Form17NoKaJaPaService {

	@Autowired
	private Form17NoKaJaPaRepository form17NoKaJaPaRepository;
	
	@Autowired
	private LandRevenueDemandRepository landRevenueDemandRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;
	@Autowired
	private Form17Repository form17Repository;
	
	@Override
	public Response getAllForm17KaJaPaData(HttpServletRequest request, String cCode, String khataNo,
			String revenueYear, Locale locale) {
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
      	List<Form17NoKaJaPa> form17NoKaJaPa = form17NoKaJaPaRepository.findBycCodeAndKhataNoAndRevenueYearAndActiveFlag(cCode,khataNo,revenueYear,'Y');
      	DatabaseContextHolder.restoreCtx();
      	if(Collections.isEmpty(form17NoKaJaPa)) {
      		throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
      	}
      	Response form17NoKaJaPaResponse =new  Response();
      	 form17NoKaJaPaResponse.setForm17NoKaJaPaData(form17NoKaJaPa.stream().map(form17NoKaJaPaAR -> toDao(form17NoKaJaPaAR)).collect(Collectors.toList()));
      	 return form17NoKaJaPaResponse;
	}else {
		throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
	}
	}

	
	@Override
	public Form17NoKaJaPaDao getForm17ById(long id,HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form17NoKaJaPa form17 =form17NoKaJaPaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Form17", id));
		DatabaseContextHolder.restoreCtx();
		return toDao(form17);
	}

	@Override
	public MessageResponse save(@Valid Form17NoKaJaPaDao form17NoKaJaPaDao,HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		if(!(landRevenueDemandRepository.existsBycCodeAndKhataNoAndRevenueYearAndActiveFlag(
				form17NoKaJaPaDao.getCCode(),
				form17NoKaJaPaDao.getKhataNo(),
				form17NoKaJaPaDao.getRevenueYear(),'Y'))){
		Form17NoKaJaPa form17NoKaJaPa= toEntity(form17NoKaJaPaDao);
		
		
//		List<Object[]> f17=form17Repository.findByKhataNo(
//				CommonConstants.AugDatamhrHost,request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//				form17NoKaJaPaDao.getCCode(),form17NoKaJaPaDao.getKhataNo(),CommonConstants.AugDataPort
//				);
//		DatabaseContextHolder.restoreCtx();
//		List<Form17NoKaJaPaDao> lst = f17.stream().map(r -> {
//			Form17NoKaJaPaDao f17Kajapa = new Form17NoKaJaPaDao();
//			form17NoKaJaPa.setKhataNo(r[0].toString());
//			form17NoKaJaPa.setPersonLiable(r[1].toString());
//			form17NoKaJaPa.setTotalArea(BigDecimal.valueOf(Double.valueOf(r[2].toString())));
//			form17NoKaJaPa.setSurveyHissaNo(r[3].toString());
//			form17NoKaJaPa.setUomOfTotalArea(r[4].toString());
//			return f17Kajapa;
//		}).collect(Collectors.toList());
		
		
		
		 if(form17NoKaJaPaDao.getKaJaPaRate()!=null) {
			  if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partA")) {
			    if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partA") && form17NoKaJaPaDao.getPrayojanType().equals("निवासी")) {
			    	form17NoKaJaPa.setAmountOfJm(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)));
			    	form17NoKaJaPa.setAmountOfGp(BigDecimal.ZERO);
			    	form17NoKaJaPa.setAmountOfZp(BigDecimal.ZERO);
			    	form17NoKaJaPa.setAssessment(form17NoKaJaPa.getAmountOfJm());
			    }else if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partA") && form17NoKaJaPaDao.getPrayojanType().equals("औद्योगिक")) {
			    	form17NoKaJaPa.setAmountOfJm(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(1.5)));
			    	form17NoKaJaPa.setAmountOfGp(BigDecimal.ZERO);
			    	form17NoKaJaPa.setAmountOfZp(BigDecimal.ZERO);
			    	form17NoKaJaPa.setAssessment(form17NoKaJaPa.getAmountOfJm());
			    }else if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partA") && form17NoKaJaPaDao.getPrayojanType().equals("वाणिज्य")) {
			    	form17NoKaJaPa.setAmountOfJm(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(2)));
			    	form17NoKaJaPa.setAmountOfGp(BigDecimal.ZERO);
			    	form17NoKaJaPa.setAmountOfZp(BigDecimal.ZERO);
			    	form17NoKaJaPa.setAssessment(form17NoKaJaPa.getAmountOfJm());
			    }else if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partA") && form17NoKaJaPaDao.getPrayojanType().equals("इतर")) {
			    	form17NoKaJaPa.setAmountOfJm(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(1.5)));
			    	form17NoKaJaPa.setAmountOfGp(BigDecimal.ZERO);
			    	form17NoKaJaPa.setAmountOfZp(BigDecimal.ZERO);
			    	form17NoKaJaPa.setAssessment(form17NoKaJaPa.getAmountOfJm());
			    }
			  }
			  
			  if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partB")) {
				  if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partB") && form17NoKaJaPaDao.getPrayojanType().equals("निवासी")) {
				    	form17NoKaJaPa.setAmountOfJm(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)));
				    	form17NoKaJaPa.setAmountOfGp(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)));
				    	form17NoKaJaPa.setAmountOfZp(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(7)));
				    	form17NoKaJaPa.setAssessment(form17NoKaJaPa.getAmountOfJm().add(form17NoKaJaPa.getAmountOfGp()).add(form17NoKaJaPa.getAmountOfZp()));
				
				  }else if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partB") && form17NoKaJaPaDao.getPrayojanType().equals("औद्योगिक")) {
				    	form17NoKaJaPa.setAmountOfJm(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(1.5)));
				    	form17NoKaJaPa.setAmountOfGp(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(1.5)));
				    	form17NoKaJaPa.setAmountOfZp(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(7)).multiply(BigDecimal.valueOf(1.5)));
				    	form17NoKaJaPa.setAssessment(form17NoKaJaPa.getAmountOfJm().add(form17NoKaJaPa.getAmountOfGp()).add(form17NoKaJaPa.getAmountOfZp()));
				
				    	
				  }else if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partB") && form17NoKaJaPaDao.getPrayojanType().equals("वाणिज्य")) {
				    	form17NoKaJaPa.setAmountOfJm(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(2)));
				    	form17NoKaJaPa.setAmountOfGp(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(2)));
				    	form17NoKaJaPa.setAmountOfZp(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(7)).multiply(BigDecimal.valueOf(2)));
				    	form17NoKaJaPa.setAssessment(form17NoKaJaPa.getAmountOfJm().add(form17NoKaJaPa.getAmountOfGp()).add(form17NoKaJaPa.getAmountOfZp()));
				    
				  }else if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partB") && form17NoKaJaPaDao.getPrayojanType().equals("इतर")) {
				    	form17NoKaJaPa.setAmountOfJm(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(1.5)));
				    	form17NoKaJaPa.setAmountOfGp(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(1.5)));
				    	form17NoKaJaPa.setAmountOfZp(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(7)).multiply(BigDecimal.valueOf(1.5)));
				    	form17NoKaJaPa.setAssessment(form17NoKaJaPa.getAmountOfJm().add(form17NoKaJaPa.getAmountOfGp()).add(form17NoKaJaPa.getAmountOfZp()));
				    	
				  }  
			  }
	}

		
		
		
		form17NoKaJaPaRepository.save(form17NoKaJaPa);
		
		DatabaseContextHolder.restoreCtx();
		return MessageResponse.builder().status("SUCCESS").message(
				messageSource.getMessage("error.form.savedata", null, locale)).build();	
		}
		else  {
			throw new ExchangeException("error.form.alreadyInDemand",messageSource,locale);
		}
		
	}

	@Override
	public void deleteForm17NoKaJaPaById(long id,HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form17NoKaJaPa form17NoKaJaPa =form17NoKaJaPaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form17NoKaJaPa",id));
		DatabaseContextHolder.restoreCtx();
		form17NoKaJaPa.setActiveFlag('N');
		 form17NoKaJaPaRepository.save(form17NoKaJaPa);
	}

	@Override
	public void updateForm17NoKaJaPa(@Valid Form17NoKaJaPaDao form17NoKaJaPaDao,HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form17NoKaJaPa form17NoKaJaPa = form17NoKaJaPaRepository.findById(form17NoKaJaPaDao.getId()).
				orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form17NoKaJaPa", form17NoKaJaPaDao.getId()));
		DatabaseContextHolder.restoreCtx();
		    BeanUtils.copyProperties(form17NoKaJaPaDao, form17NoKaJaPa);
		    if(form17NoKaJaPaDao.getId()!=null) {
				List<Object[]> lrdetails = form17NoKaJaPaRepository.getDetailsById(
						request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
						form17NoKaJaPaDao.getCCode(),form17NoKaJaPaDao.getId());
				List<Form17NoKaJaPaDao> lst = lrdetails.stream().map(r -> {
					Form17NoKaJaPaDao f17 = new Form17NoKaJaPaDao();
					form17NoKaJaPa.setKhataNo(r[2].toString());
					form17NoKaJaPa.setPersonLiable(r[3].toString());
					form17NoKaJaPa.setSurveyHissaNo(r[4].toString());
					form17NoKaJaPa.setTotalArea(BigDecimal.valueOf(Double.valueOf(r[5].toString())));
					form17NoKaJaPa.setUomOfTotalArea(r[6].toString());
					return f17;
				}).collect(Collectors.toList());
				}
		    
		    if(form17NoKaJaPaDao.getKaJaPaRate()!=null) {
		  if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partA")) {
		    if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partA") && form17NoKaJaPaDao.getPrayojanType().equals("निवासी")) {
		    	form17NoKaJaPa.setAmountOfJm(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)));
		    	form17NoKaJaPa.setAmountOfGp(BigDecimal.ZERO);
		    	form17NoKaJaPa.setAmountOfZp(BigDecimal.ZERO);
		    	form17NoKaJaPa.setAssessment(form17NoKaJaPa.getAmountOfJm());
		    }else if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partA") && form17NoKaJaPaDao.getPrayojanType().equals("औद्योगिक")) {
		    	form17NoKaJaPa.setAmountOfJm(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(1.5)));
		    	form17NoKaJaPa.setAmountOfGp(BigDecimal.ZERO);
		    	form17NoKaJaPa.setAmountOfZp(BigDecimal.ZERO);
		    	form17NoKaJaPa.setAssessment(form17NoKaJaPa.getAmountOfJm());
		    }else if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partA") && form17NoKaJaPaDao.getPrayojanType().equals("वाणिज्य")) {
		    	form17NoKaJaPa.setAmountOfJm(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(2)));
		    	form17NoKaJaPa.setAmountOfGp(BigDecimal.ZERO);
		    	form17NoKaJaPa.setAmountOfZp(BigDecimal.ZERO);
		    	form17NoKaJaPa.setAssessment(form17NoKaJaPa.getAmountOfJm());
		    }else if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partA") && form17NoKaJaPaDao.getPrayojanType().equals("इतर")) {
		    	form17NoKaJaPa.setAmountOfJm(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(1.5)));
		    	form17NoKaJaPa.setAmountOfGp(BigDecimal.ZERO);
		    	form17NoKaJaPa.setAmountOfZp(BigDecimal.ZERO);
		    	form17NoKaJaPa.setAssessment(form17NoKaJaPa.getAmountOfJm());
		    }
		  }
		  
		  if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partB")) {
			  if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partB") && form17NoKaJaPaDao.getPrayojanType().equals("निवासी")) {
			    	form17NoKaJaPa.setAmountOfJm(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)));
			    	form17NoKaJaPa.setAmountOfGp(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)));
			    	form17NoKaJaPa.setAmountOfZp(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(7)));
			    	form17NoKaJaPa.setAssessment(form17NoKaJaPa.getAmountOfJm().add(form17NoKaJaPa.getAmountOfGp()).add(form17NoKaJaPa.getAmountOfZp()));
			    }else if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partB") && form17NoKaJaPaDao.getPrayojanType().equals("औद्योगिक")) {
			    	form17NoKaJaPa.setAmountOfJm(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(1.5)));
			    	form17NoKaJaPa.setAmountOfGp(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(1.5)));
			    	form17NoKaJaPa.setAmountOfZp(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(7)).multiply(BigDecimal.valueOf(1.5)));
			    	form17NoKaJaPa.setAssessment(form17NoKaJaPa.getAmountOfJm().add(form17NoKaJaPa.getAmountOfGp()).add(form17NoKaJaPa.getAmountOfZp()));
			    }else if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partB") && form17NoKaJaPaDao.getPrayojanType().equals("वाणिज्य")) {
			    	form17NoKaJaPa.setAmountOfJm(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(2)));
			    	form17NoKaJaPa.setAmountOfGp(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(2)));
			    	form17NoKaJaPa.setAmountOfZp(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(7)).multiply(BigDecimal.valueOf(2)));
			    	form17NoKaJaPa.setAssessment(form17NoKaJaPa.getAmountOfJm().add(form17NoKaJaPa.getAmountOfGp()).add(form17NoKaJaPa.getAmountOfZp()));
			    }else if(form17NoKaJaPaDao.getLocationOfLand().equals("form2.table.partB") && form17NoKaJaPaDao.getPrayojanType().equals("इतर")) {
			    	form17NoKaJaPa.setAmountOfJm(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(1.5)));
			    	form17NoKaJaPa.setAmountOfGp(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(1.5)));
			    	form17NoKaJaPa.setAmountOfZp(form17NoKaJaPaDao.getAreaAffected().multiply(form17NoKaJaPaDao.getKaJaPaRate()).multiply(BigDecimal.valueOf(10000)).multiply(BigDecimal.valueOf(7)).multiply(BigDecimal.valueOf(1.5)));
			    	form17NoKaJaPa.setAssessment(form17NoKaJaPa.getAmountOfJm().add(form17NoKaJaPa.getAmountOfGp()).add(form17NoKaJaPa.getAmountOfZp()));
			    }  
		  }
		    }
		  
		    form17NoKaJaPaRepository.save(form17NoKaJaPa);
		
	}

	 private Form17NoKaJaPaDao toDao(Form17NoKaJaPa form17NoKaJaPa){
		 Form17NoKaJaPaDao form17NoKaJaPaDao = new Form17NoKaJaPaDao();
	    	BeanUtils.copyProperties(form17NoKaJaPa, form17NoKaJaPaDao);
	    	return form17NoKaJaPaDao;
	    }
	 
	  private Form17NoKaJaPa toEntity(Form17NoKaJaPaDao form17NoKaJaPaDao){
		  Form17NoKaJaPa form17NoKaJaPa = new Form17NoKaJaPa();
	    	BeanUtils.copyProperties(form17NoKaJaPaDao, form17NoKaJaPa);
	    	return form17NoKaJaPa;
	    }

	  @Override
		public  Response getForm17NoKaJaPaReport(String cCode, Locale locale,HttpServletRequest request) {
			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
			
			
			/*List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
					CommonConstants.AugDatamhrHost,
					request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
					request.getHeader("servarthId"));
			Response response =new  Response();
			response.setKhataMergeData(ccodeslist.stream().map(r -> {
				KhataMergeDao km=new KhataMergeDao();
				km.setcCode(r[0].toString());
				km.setServarthId(r[1].toString());
				km.setVillageName(r[2].toString());
				return km;
			}).collect(Collectors.toList()));
			*/
			
//			String ccodes=null;
//			for(KhataMergeDao r : response.getKhataMergeData()) {
//				if(r.getcCode().equals(cCode)) {
//				ccodes=r.getcCode();
//				break;
//				}else{
//					ccodes="0";
//					}}
			
			//if(ccodes.equals(cCode)) {	
				Form17NoKaJaPa form17NoKaJaPa =new Form17NoKaJaPa();
				Form17Dao form17Dao = new Form17Dao();
				BeanUtils.copyProperties(form17Dao,form17NoKaJaPa,"id");
			List<Object[]> ReportrecordsLst=form17NoKaJaPaRepository.getKajapaRecords(
					request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,cCode, CommonConstants.currentYear);
			DatabaseContextHolder.restoreCtx();
//			if(Collections.isEmpty(ReportrecordsLst)) {
//				throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
//			}
//			else {
				Response form17NoKaResponse = new Response();
				form17NoKaResponse.setForm17NoKaJaPaData(ReportrecordsLst.stream().map((object)->{
					Form17NoKaJaPaDao form17NoKaJaPaDao = new Form17NoKaJaPaDao();
					form17NoKaJaPaDao.setKhataNo(object[0].toString());
					form17NoKaJaPaDao.setSurveyHissaNo(object[1].toString());
					form17NoKaJaPaDao.setTotalArea(BigDecimal.valueOf(Double.valueOf(object[2]==null ? "" : object[2].toString())));
					form17NoKaJaPaDao.setAssessment(BigDecimal.valueOf(Double.valueOf(object[3].toString())));
					form17NoKaJaPaDao.setPersonLiable(object[4].toString());
					form17NoKaJaPaDao.setOrderNo(object[5].toString());
					form17NoKaJaPaDao.setUomOfTotalArea(object[6].toString());
					form17NoKaJaPaDao.setLocationOfLand(object[7]==null ? "" : object[7].toString());
					form17NoKaJaPaDao.setPrayojanType(object[8]==null ? "" : object[8].toString());
					form17NoKaJaPaDao.setAmountOfGp(BigDecimal.valueOf(Double.valueOf(object[9]==null ? "" : object[9].toString())));
					form17NoKaJaPaDao.setAmountOfJm(BigDecimal.valueOf(Double.valueOf(object[10]==null ? "" : object[10].toString())));
					form17NoKaJaPaDao.setAmountOfZp(BigDecimal.valueOf(Double.valueOf(object[11]==null ? "" : object[11].toString())));
					form17NoKaJaPaDao.setRemarks(object[12]==null ? "" : object[12].toString());
					return form17NoKaJaPaDao;
				}).collect(Collectors.toList()));
				
				return form17NoKaResponse;
		     //}
			//}else {
			//	throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
			//}
		}
	

		@Override
		public Response getDetailBykhataNoKajapa(String cCode, String khataNo, HttpServletRequest request, Locale locale) {
			
			
//				dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//				List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(CommonConstants.AugDatamhrHost,
//						request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), CommonConstants.mhrorUser,
//						CommonConstants.mhrorPassword, request.getHeader("servarthId"));
//				Response response =new  Response();
//				response.setKhataMergeData(ccodeslist.stream().map(r -> {
//					KhataMergeDao km=new KhataMergeDao();
//					km.setcCode(r[0].toString());
//					km.setServarthId(r[1].toString());
//					km.setVillageName(r[2].toString());
//					return km;
//				}).collect(Collectors.toList()));
//				
//				
//				String ccodes=null;
//				for(KhataMergeDao r : response.getKhataMergeData()) {
//					if(r.getcCode().equals(cCode)) {
//					ccodes=r.getcCode();
//					break;
//					}else{
//						ccodes="0";
//						}}
//				
//				if(ccodes.equals(cCode)) {	
				List<Object[]> form17=form17Repository.findByKhataNo(
						CommonConstants.AugDatamhrHost,request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
						cCode,khataNo,CommonConstants.AugDataPort
						);
				DatabaseContextHolder.restoreCtx();
				if(Collections.isEmpty(form17)) {
					throw new ResourceNotFoundException("error.form.khata",messageSource,locale);
				}else {
					Response form17Response =new  Response();
					form17Response.setForm17Data(form17.stream().map((object) -> {
						Form17Dao form17Dao = new Form17Dao();
						form17Dao.setKhataNo(object[0].toString());
						form17Dao.setPersonLiable(object[1].toString());
						form17Dao.setTotalArea(BigDecimal.valueOf(Double.valueOf(object[2].toString())));
						form17Dao.setSurveyHissaNo(object[3].toString());
						form17Dao.setUomOfTotalArea(object[4].toString());
						return form17Dao;
					}).collect(Collectors.toList()));
					return form17Response;
				}
				//}
//		else {
//					throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
//				}
			} 
		
		
		@Override
		 public BigDecimal getrate(String cCode, String pin, Locale locale, HttpServletRequest request) {

				  BigDecimal manaparate = null;
				  
				  System.out.println("ccode : "+cCode);
				  System.out.println("pin : "+pin);
				  
        
			          manaparate = form17NoKaJaPaRepository.getnapamanaparate(
			              request.getHeader("mhrHost"),     // Host
			              request.getHeader("mhrDbName"),    // DB Name
			              request.getHeader("mhrSchemaName"),// Schema
			              CommonConstants.mhrorUser,         // User
			              CommonConstants.mhrorPassword,     // Password
			              cCode,
			              pin
			          );
			          
			          System.out.println("manaparate : "+manaparate);
			          
			          
			          
			          return manaparate;
			  }
	
	    
}
