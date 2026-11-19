package com.probity.echawdi.serviceImpl;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.AkrushakRateDao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.dao.KhataMergeDtlDao;
import com.probity.echawdi.dao.LandRevenueDemandDao;
import com.probity.echawdi.entity.KhataMerge;
import com.probity.echawdi.entity.KhataMergeDtl;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.KhataMergeDtlRepository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.repository.LandRevenueDemandRepository;
import com.probity.echawdi.service.KhataMergeService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;
import io.jsonwebtoken.lang.Collections;

@Component
public class KhataMergeServiceImpl implements KhataMergeService{

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private KhataMergeDtlRepository khataMergeDtlRepository;
	@Autowired
	private KhataMergeRepository khataMergeRepository;
	@Autowired
	private LandRevenueDemandRepository landRevenueDemandRepository;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;

	@Override
	public KhataMergeDao getKhataMergeDtl(String cCode,String revenueYear,Locale locale,HttpServletRequest request) {
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
			KhataMergeDao khataMergeDao=new KhataMergeDao();
			List<Object[]> khatamergedtl=khataMergeRepository.getKhataMergeDtl(
					CommonConstants.AugDatamhrHost,request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
					request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
					cCode,CommonConstants.AugDataPort, revenueYear
					);
			
			DatabaseContextHolder.restoreCtx();
			
			khataMergeDao.setKhataMergeDtlLst(	
					khatamergedtl.stream().map(object -> {
						KhataMergeDtlDao khataMergeDtlDao=new KhataMergeDtlDao();
						khataMergeDtlDao.setKhataNo(object[0].toString());
						khataMergeDtlDao.setSurveyHissaNo(trimSurveyHissa(object[1].toString().replaceAll("//", "")));
						khataMergeDtlDao.setKhataOwnerName(object[2].toString());
						khataMergeDtlDao.setArea(BigDecimal.valueOf(Double.valueOf(object[3].toString())));
						khataMergeDtlDao.setAssessment(BigDecimal.valueOf(Double.valueOf(object[4].toString())));
						khataMergeDtlDao.setMergeId(object[5] !=null && object[6] !=null ? prepareMergerId(Long.valueOf(object[6].toString())) : "");
						if(object[6] !=null ) {
							khataMergeDtlDao.setParentId(Long.valueOf(object[6].toString()));
						}
						khataMergeDtlDao.setChildKhataNumbers(object[7]==null ? "" : object[7].toString());
						khataMergeDtlDao.setcCode(object[8].toString());
						return khataMergeDtlDao;
					}).sorted(Comparator.comparing(KhataMergeDtlDao::getMergeId, Comparator.reverseOrder())).collect(Collectors.toList()));
			return khataMergeDao;
		}
		else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}

	public String prepareMergerId(Long parentId) {
		KhataMerge khataMerge=khataMergeRepository.getById(parentId);
		return "M"+khataMerge.getCount()+"-"+khataMerge.getChildKhataNumbers();
	}

	@Override
	public KhataMergeDao saveKhataMerge(KhataMergeDao khataMergeDao, Locale locale,HttpServletRequest request)
	{
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		if(!(landRevenueDemandRepository.existsBycCodeAndKhataNoAndRevenueYearAndActiveFlag(
				khataMergeDao.getcCode(),khataMergeDao.getKhataNo(),khataMergeDao.getRevenueYear(),'Y')))
				{
					if(khataMergeDao.getKhataMergeDtlLst() !=null && khataMergeDao.getKhataNo()!=null && khataMergeDao.getcCode()!=null) 
					{
						final KhataMerge  khataMerge=new KhataMerge();
						BeanUtils.copyProperties(khataMergeDao,khataMerge,"id");
						khataMerge.setArea(BigDecimal.ZERO);
						khataMerge.setAssessment(BigDecimal.ZERO);
						khataMerge.setSurveyHissaNo("");
						Long pId=khataMergeRepository.getParentId();
						Long count=khataMergeRepository.getCountByCCode(khataMergeDao.getcCode());
						final Long parentId=pId==null ? 1: pId+1;
						List<KhataMergeDtl> khataMergeDtlLst=khataMergeDao.getKhataMergeDtlLst().stream().map(r->{
						KhataMergeDtl khataMergeDtl=new KhataMergeDtl();
						BeanUtils.copyProperties(r,khataMergeDtl,"id");
						khataMergeDtl.setParentId(parentId);
						khataMergeDtl.setCCode(khataMergeDao.getcCode());
						khataMergeDtl.setRevenueYear(khataMergeDao.getRevenueYear());
						
						
						System.out.println("RevenueYear----: "+khataMergeDao.getRevenueYear());
						
						if(khataMerge.getKhataNo().toString().equals(khataMergeDtl.getKhataNo().toString())) {
							khataMerge.setKhataOwnerName(khataMergeDtl.getKhataOwnerName());
						}
						if(!(khataMerge.getKhataNo().toString().equals(khataMergeDtl.getKhataNo().toString()))) {
						khataMerge.setChildKhataNumbers(
								khataMerge.getChildKhataNumbers()==null ? 
								khataMergeDtl.getKhataNo().concat(",")
								:
								khataMerge.getChildKhataNumbers().concat(khataMergeDtl.getKhataNo()).concat(","));
						}
						khataMerge.setArea(khataMerge.getArea().add(khataMergeDtl.getArea()));
						khataMerge.setAssessment(khataMerge.getAssessment().add(khataMergeDtl.getAssessment()));
						khataMerge.setSurveyHissaNo(khataMerge.getSurveyHissaNo()=="" ? khataMergeDtl.getSurveyHissaNo() :khataMerge.getSurveyHissaNo()+"," +khataMergeDtl.getSurveyHissaNo());
						khataMerge.setRevenueYear(khataMergeDao.getRevenueYear());
						return khataMergeDtl;
					}).collect(Collectors.toList());
			
						khataMerge.setUom("H-R.Sq.M.");
						khataMerge.setCount(count+1);
						khataMerge.setChildKhataNumbers(
											 khataMerge.getKhataNo()
									        .concat(",")
									        .concat(khataMerge.getChildKhataNumbers().substring(0, khataMerge.getChildKhataNumbers().length() - 1))
									        );
			
					khataMerge.setMergeId("M"+khataMerge.getCount()+"-"+khataMerge.getChildKhataNumbers());
					khataMergeRepository.save(khataMerge);
					khataMergeDtlRepository.saveAll(khataMergeDtlLst);
					DatabaseContextHolder.restoreCtx();
					return getKhataMergeDtl(khataMergeDao.getcCode(),khataMergeDao.getRevenueYear(), locale,request);
				}
				else {
					throw new ExchangeException("khataNo.notempty",messageSource,locale);
				}
		
			}
			else  {
			throw new ExchangeException("error.form.alreadyInDemand",messageSource,locale);
		}
	}


	public String trimSurveyHissa(String rawSurveyHissa) {
		String surveyHissa = null;
		if(rawSurveyHissa.endsWith("/")) {
			StringBuilder b = new StringBuilder(rawSurveyHissa);
			b.replace(rawSurveyHissa.lastIndexOf("/"), rawSurveyHissa.lastIndexOf("/") + 1, "" );
			surveyHissa=b.toString();
			return surveyHissa;
		}else {
			return rawSurveyHissa;
		}
	}

	@Override
	public KhataMergeDao deleteMergeDtlByParentId(long parentId, Locale locale,HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<KhataMergeDtl> khataMergeDtlLst=khataMergeDtlRepository.findByParentId(parentId);
		khataMergeDtlLst=khataMergeDtlLst.stream().map(r->{
			KhataMergeDtl khataMergeDtl=new KhataMergeDtl();
			BeanUtils.copyProperties(r,khataMergeDtl);
			khataMergeDtl.setActiveFlag('N');
			return khataMergeDtl;
		}).collect(Collectors.toList());
		KhataMerge khataMerge =khataMergeRepository.findById(parentId).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "KhataMerge", parentId));
		khataMerge.setActiveFlag('N');
		khataMergeRepository.save(khataMerge);
		khataMergeDtlRepository.saveAll(khataMergeDtlLst);
		DatabaseContextHolder.restoreCtx();
		return getKhataMergeDtl(khataMerge.getCCode(),khataMerge.getRevenueYear(), locale,request);
	}

	@Override
	public KhataMergeDao getKhataMergeDtlReport(String cCode, Locale locale,HttpServletRequest request) {
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
		KhataMerge  khataMerge=new KhataMerge();
		KhataMergeDao  khataMergeDao=new KhataMergeDao();
		BeanUtils.copyProperties(khataMergeDao,khataMerge,"id");
		List<Object[]> Reportkhatamergedtl=khataMergeRepository.getKhataMergeDtlReport(
//				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode ,'Y');
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(Reportkhatamergedtl)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}else {
			khataMergeDao.setKhataMergeDtlLst(	
					Reportkhatamergedtl.stream().map(object -> {
						KhataMergeDtlDao khataMergeDtlDao=new KhataMergeDtlDao();
						khataMergeDtlDao.setKhataNo(object[0].toString());
						khataMergeDtlDao.setKhataOwnerName(object[1].toString());
						khataMergeDtlDao.setMergeId(object[2]==null ? "" : object[2].toString());
						khataMergeDtlDao.setSurveyHissaNo(trimSurveyHissa(object[3].toString().replaceAll("//", "")));
						khataMergeDtlDao.setArea(BigDecimal.valueOf(Double.valueOf(object[4].toString())));
						khataMergeDtlDao.setAssessment(BigDecimal.valueOf(Double.valueOf(object[5].toString())));
						khataMergeDtlDao.setcCode(object[6].toString());
						khataMergeDtlDao.setChildKhataNumbers(object[7]==null ? "" : object[7].toString());
						return khataMergeDtlDao;
					}).sorted(Comparator.comparing(KhataMergeDtlDao::getMergeId, Comparator.reverseOrder())).collect(Collectors.toList()));
		}
		return khataMergeDao;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}
}