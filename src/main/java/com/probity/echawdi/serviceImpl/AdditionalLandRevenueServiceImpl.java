package com.probity.echawdi.serviceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.AdditionalLandRevenueDao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.dao.LandRevenueDemandDao;
import com.probity.echawdi.entity.AdditionalLandRevenue;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.AdditionalLandRevenueRepository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.repository.LandRevenueDemandRepository;
import com.probity.echawdi.service.AdditionalLandRevenueService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;
import io.jsonwebtoken.lang.Collections;

@Component
public class AdditionalLandRevenueServiceImpl implements AdditionalLandRevenueService{

	@Autowired
	private MessageSource messageSource;

	@Autowired
	public AdditionalLandRevenueRepository additionalLandRevenueRepository;

	@Autowired
	public LandRevenueDemandRepository landRevenueDemandRepository;

	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;

	@Override
	public Response getAddLandRevenueDetails(String cCode, String khataNo,HttpServletRequest request, Locale locale) {
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
		List<Object[]> addLrDtlLst=additionalLandRevenueRepository.getAddLRDtls(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				cCode,khataNo);
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(addLrDtlLst)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}else {
			Response additionalLandRevenueResponse =new  Response();
			additionalLandRevenueResponse.setAdditionalLandRevenueData(addLrDtlLst.stream().map((object) -> {
				AdditionalLandRevenueDao additionalLandRevenueDao=new AdditionalLandRevenueDao();
				additionalLandRevenueDao.setKhataNo(object[0].toString());
				additionalLandRevenueDao.setSurveyHissaNo(object[1].toString());
				additionalLandRevenueDao.setKhataOwnerName(object[2].toString());
				additionalLandRevenueDao.setArea(BigDecimal.valueOf(Double.valueOf(object[3].toString())));	
				additionalLandRevenueDao.setAssessment(BigDecimal.valueOf(Double.valueOf(object[4].toString())));
				return additionalLandRevenueDao;
			}). collect(Collectors.toList()));
			return additionalLandRevenueResponse;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}

	@Override
	public Response save(AdditionalLandRevenueDao additionalLandRevenueDao,HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		if(!(landRevenueDemandRepository.existsBycCodeAndKhataNoAndRevenueYearAndActiveFlag(
				additionalLandRevenueDao.getcCode(),
				additionalLandRevenueDao.getKhataNo(),
				additionalLandRevenueDao.getRevenueYear(),'Y'))){
			if(!(additionalLandRevenueRepository.existsBycCodeAndRevenueYearAndKhataNoAndActiveFlag
					(additionalLandRevenueDao.getcCode(),additionalLandRevenueDao.getRevenueYear(),additionalLandRevenueDao.getKhataNo(),'Y'))){
				
				List<Object[]> addLrDtlLst=additionalLandRevenueRepository.getAddLRDtls(
						request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
						additionalLandRevenueDao.getcCode(),additionalLandRevenueDao.getKhataNo());
				AdditionalLandRevenue additionalLandRevenue= toEntity(additionalLandRevenueDao);
				List<LandRevenueDemandDao> lst = addLrDtlLst.stream().map(r -> {
					LandRevenueDemandDao lrDao = new LandRevenueDemandDao();
					additionalLandRevenue.setKhataOwnerName(r[2].toString());
					additionalLandRevenue.setArea(BigDecimal.valueOf(Double.valueOf(r[3].toString())));
//					additionalLandRevenue.setAssessment(BigDecimal.valueOf(Double.valueOf(r[4].toString())));
					return lrDao;
				}).collect(Collectors.toList());
				
				additionalLandRevenue.setAssessment(additionalLandRevenueDao.getAssessment().setScale(0,BigDecimal.ROUND_HALF_UP));
				additionalLandRevenue.setApplicableForDemand('Y');
				additionalLandRevenueRepository.save(additionalLandRevenue);
				DatabaseContextHolder.restoreCtx();
				return getAddLandRevenueRecords(additionalLandRevenueDao.getcCode(),additionalLandRevenueDao.getRevenueYear(),request,locale);
			}else  {
				throw new ExchangeException("error.form.already",messageSource,locale);
			}
		}
		else  {
			throw new ExchangeException("error.form.alreadyInDemand",messageSource,locale);
		}
	}

	@Override
	public Response getAddLandRevenueRecords(String cCode,String revenueYear, HttpServletRequest request,Locale locale) {
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
		List<Object[]> RecordsLst=additionalLandRevenueRepository.getAddLTablesRecords(request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,cCode, revenueYear);
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(RecordsLst)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}
		else {
			Response additionalLandRevenueResponse =new  Response();
			additionalLandRevenueResponse.setAdditionalLandRevenueData(RecordsLst.stream().map((object) -> {
				AdditionalLandRevenueDao additionalLandRevenueDao=new AdditionalLandRevenueDao();	
				additionalLandRevenueDao.setId(Long.valueOf(object[0].toString()));
				additionalLandRevenueDao.setKhataNo(object[1].toString());
				additionalLandRevenueDao.setKhataOwnerName(object[2].toString());
				additionalLandRevenueDao.setAssessment(BigDecimal.valueOf(Double.valueOf(object[3].toString())));
				additionalLandRevenueDao.setArea(BigDecimal.valueOf(Double.valueOf(object[4].toString())));
				additionalLandRevenueDao.setCheckboxgroup(object[5]==null ? "" : object[5].toString());
				additionalLandRevenueDao.setRadiogroup(object[6]==null ? "" : object[6].toString());
				additionalLandRevenueDao.setRemarks(object[7]==null ? "" : object[7].toString());
				return additionalLandRevenueDao;
			}).collect(Collectors.toList()));
			return additionalLandRevenueResponse;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}


	@Override
	public Response getAddLandRevenueReportDtls(String cCode, String revenueYear, HttpServletRequest request,
			Locale locale) {
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
		List<Object[]> ReportrecordsLst=additionalLandRevenueRepository.getAddLReportRecords(request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,cCode, revenueYear);
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(ReportrecordsLst)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}
		else {
			Response additionalLandRevenueResponse =new  Response();
			additionalLandRevenueResponse.setAdditionalLandRevenueData(ReportrecordsLst.stream().map((object) -> {
				AdditionalLandRevenueDao additionalLandRevenueDao=new AdditionalLandRevenueDao();	
				additionalLandRevenueDao.setId(Long.valueOf(object[0].toString()));
				additionalLandRevenueDao.setKhataNo(object[1].toString());
				additionalLandRevenueDao.setKhataOwnerName(object[2].toString());
				additionalLandRevenueDao.setAssessment(BigDecimal.valueOf(Double.valueOf(object[3].toString())));
				additionalLandRevenueDao.setArea(BigDecimal.valueOf(Double.valueOf(object[4].toString())));
				additionalLandRevenueDao.setCheckboxgroup(object[5]==null ? "" : object[5].toString());
				additionalLandRevenueDao.setRadiogroup(object[6]==null ? "" : object[6].toString());
				additionalLandRevenueDao.setRemarks(object[7]==null ? "" : object[7].toString());
				return additionalLandRevenueDao;
			}).collect(Collectors.toList()));

			return additionalLandRevenueResponse;
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}

	private AdditionalLandRevenue toEntity(AdditionalLandRevenueDao additionalLandRevenueDao){
		AdditionalLandRevenue additionalLandRevenue = new AdditionalLandRevenue();
		BeanUtils.copyProperties(additionalLandRevenueDao, additionalLandRevenue,"id");
		return additionalLandRevenue;
	}

	@Override
	public Response deleteMergeDtlByParentId(long id,HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		AdditionalLandRevenue additionalLandRevenue =additionalLandRevenueRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "AdditionalLandRevenue", id));
		additionalLandRevenue.setActiveFlag('N');
		additionalLandRevenueRepository.save(additionalLandRevenue);
		DatabaseContextHolder.restoreCtx();
		return null;
	}

	@Override
	public Response saveAll(List<AdditionalLandRevenueDao> additionalLandRevenueDao, HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<Long> ids=additionalLandRevenueDao.stream().map(r->{
			return r.getId();
		}).collect(Collectors.toList());
		List<AdditionalLandRevenue> additionalLandRevenueLst=additionalLandRevenueRepository.findByIdIn(ids);
		additionalLandRevenueLst.forEach(r->{r.setApplicableForDemand('Y');});
		additionalLandRevenueRepository.saveAll(additionalLandRevenueLst);
		DatabaseContextHolder.restoreCtx();
		return null;
	}

	public AdditionalLandRevenueDao toDao(AdditionalLandRevenue additionalLandRevenue) {
		AdditionalLandRevenueDao additionalLandRevenueDao=new AdditionalLandRevenueDao();
		BeanUtils.copyProperties(additionalLandRevenue, additionalLandRevenueDao);
		return additionalLandRevenueDao;
	}
}