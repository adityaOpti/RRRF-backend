/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.math.BigDecimal;
import java.util.LinkedList;
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
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.dao.OneTimeEntryDao;
import com.probity.echawdi.entity.OneTimeEntry;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.FieldValidationException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.repository.LandRevenueDemandRepository;
import com.probity.echawdi.repository.OneTimeEntryRepository;
import com.probity.echawdi.service.OneTimeEntryService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Bhagyashri Lolage
 *
 */

@Component
public class  OneTimeEntryServiceImpl implements OneTimeEntryService{
	
	@Autowired
	private OneTimeEntryRepository oneTimeEntryRepository;
	@Autowired
	private LandRevenueDemandRepository landRevenueDemandRepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;
	
	@Override
	public List<Object> khataNmbers(String cCode,String revenueYear,HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<Object> oneTimeEntry = oneTimeEntryRepository.findKhataNo(
				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode,revenueYear,'Y'
				);
		DatabaseContextHolder.restoreCtx();
		return oneTimeEntry;
	}
	
	@Override
	public Response getOneTimeEntryDataBycCode(String cCode,String khataNo,HttpServletRequest request,Locale locale) {
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
		List<OneTimeEntry> listOfOneTimeEntry = oneTimeEntryRepository.findAllOneTimeEntryBycCodeAndKhataNoAndActiveFlagOrderByIdDesc(cCode,khataNo,'Y');
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(listOfOneTimeEntry)) {
			throw new ResourceNotFoundException("error.form.oneTimeEntry",messageSource,locale);
		}else {
			Response oneTimeEntryResponse =new  Response();
			oneTimeEntryResponse.setOneTimeEntryData(listOfOneTimeEntry.stream().map(oneTimeEntryR -> toDao(oneTimeEntryR)).collect(Collectors.toList()));
			return oneTimeEntryResponse;
		}
	}
	else {
		throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
	}
		}
	
	@Override
	public OneTimeEntryDao getOneTimeEntryById(long id,HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		OneTimeEntry oneTimeEntry =oneTimeEntryRepository.findById(id).
		orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "OneTimeEntry", id));
		DatabaseContextHolder.restoreCtx();
		return toDao(oneTimeEntry);
	}

	@Override
	public MessageResponse save(OneTimeEntryDao oneTimeEntryDao,HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		if(!(landRevenueDemandRepository.existsBycCodeAndKhataNoAndRevenueYearAndActiveFlag(
				oneTimeEntryDao.getcCode(),
				oneTimeEntryDao.getKhataNo(),
				oneTimeEntryDao.getRevenueYear(),'Y'))){
	    if(oneTimeEntryDao.getcCode() != null) {
	    		OneTimeEntry oneTimeEntry = toEntity(oneTimeEntryDao);
				List<BigDecimal> netPendings = new LinkedList<>();				
				netPendings.add(oneTimeEntryDao.getPreYearPendingJm());
				netPendings.add(oneTimeEntryDao.getPreYearPendingZp());
				netPendings.add(oneTimeEntryDao.getPreYearPendingGp());
				netPendings.add(oneTimeEntryDao.getPreYearPendingEducationalCess());
				netPendings.add(oneTimeEntryDao.getPreYearPendingEmployeeGuaranteeScheme());
				netPendings.add(oneTimeEntryDao.getPreYearPendingAddlLandRevenue());
				netPendings.add(oneTimeEntryDao.getPreYearPendingAddlEducationalCess());
//				netPendings.add(oneTimeEntryDao.getMiscellaneousAmount()==null ? BigDecimal.ZERO :oneTimeEntryDao.getMiscellaneousAmount());
				netPendings.add(oneTimeEntryDao.getPreYearNoticeFee());
				netPendings.add(oneTimeEntryDao.getPreYearSankirnJmWith());
				netPendings.add(oneTimeEntryDao.getPreYearSankirnJmWithout());
				netPendings.add(oneTimeEntryDao.getPreYearPendingNaCess());
//				oneTimeEntry.setNetPending()
				oneTimeEntry.setNetPending(netPendings.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
				oneTimeEntryRepository.save(oneTimeEntry);
				DatabaseContextHolder.restoreCtx();
				return MessageResponse.builder().status("SUCCESS").message(messageSource.getMessage("error.form.savedata", null, locale)).build();	
        }
        throw new FieldValidationException("error.form.errorwhilesave");
		}
		else  {
			throw new ExchangeException("error.form.alreadyInDemand",messageSource,locale);
		}
	}
	
	@Override
	public void updateOneTimeEntry(@Valid OneTimeEntryDao oneTimeEntryDao,HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		OneTimeEntry oneTimeEntry =oneTimeEntryRepository.findById(oneTimeEntryDao.getId())
								   .orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "OneTimeEntry", oneTimeEntryDao.getId()));
			BeanUtils.copyProperties(oneTimeEntryDao,oneTimeEntry);
			List<BigDecimal> netPendings = new LinkedList<>();
			netPendings.add(oneTimeEntryDao.getPreYearPendingJm());
			netPendings.add(oneTimeEntryDao.getPreYearPendingZp());
			netPendings.add(oneTimeEntryDao.getPreYearPendingGp());
			netPendings.add(oneTimeEntryDao.getPreYearPendingEducationalCess());
			netPendings.add(oneTimeEntryDao.getPreYearPendingEmployeeGuaranteeScheme());
			netPendings.add(oneTimeEntryDao.getPreYearPendingAddlLandRevenue());
			netPendings.add(oneTimeEntryDao.getPreYearPendingAddlEducationalCess());
//			netPendings.add(oneTimeEntryDao.getMiscellaneousAmount()==null ? BigDecimal.ZERO :oneTimeEntryDao.getMiscellaneousAmount());
			netPendings.add(oneTimeEntryDao.getPreYearNoticeFee());
			netPendings.add(oneTimeEntryDao.getPreYearSankirnJmWith());
			netPendings.add(oneTimeEntryDao.getPreYearSankirnJmWithout());
			netPendings.add(oneTimeEntryDao.getPreYearPendingNaCess());
			oneTimeEntry.setNetPending(netPendings.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
			oneTimeEntryRepository.save(oneTimeEntry);
			DatabaseContextHolder.restoreCtx();
	}

	@Override
	public void deleteOneTimeEntryById(Long id,HttpServletRequest request,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		OneTimeEntry oneTimeEntry = oneTimeEntryRepository.findById(id).
									orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "OneTimeEntry", id));
									oneTimeEntry.setActiveFlag('N');
									oneTimeEntryRepository.save(oneTimeEntry);
									DatabaseContextHolder.restoreCtx();
	}
	
    static OneTimeEntryDao toDao(OneTimeEntry oneTimeEntry){
    	OneTimeEntryDao oneTimeEntryDao = new OneTimeEntryDao();
        BeanUtils.copyProperties(oneTimeEntry, oneTimeEntryDao);
    	return oneTimeEntryDao;
    }

    private OneTimeEntry toEntity(OneTimeEntryDao oneTimeEntryDao){
    	OneTimeEntry oneTimeEntry = new OneTimeEntry();
    	BeanUtils.copyProperties(oneTimeEntryDao,oneTimeEntry,"id");
        return oneTimeEntry;
    }
    
    @Override
   	public Response getOneTimeEntryReportDtls(String cCode,  Locale locale,HttpServletRequest request) {
   		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//   		List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
//   				CommonConstants.AugDatamhrHost,request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//   				request.getHeader("servarthId"));
//   		System.out.println("****************************************************** in  Report ***************************************************");
//   		Response response =new  Response();
//   		response.setKhataMergeData(ccodeslist.stream().map(r -> {
//   			KhataMergeDao km=new KhataMergeDao();
//   			km.setcCode(r[0].toString());
//   			km.setServarthId(r[1].toString());
//   			km.setVillageName(r[2].toString());
//   			return km;
//   		}).collect(Collectors.toList()));
//   		
   		
   		//String ccodes=null;
   		
//   		for(KhataMergeDao r : response.getKhataMergeData()) {
//   			if(r.getcCode().equals(cCode)) {
//   			ccodes=r.getcCode();
//   			break;
//   			}else{
//   				ccodes="0";
//   				}
//   			}
   		System.out.println("****************************************************** outside  Report ReportrecordsLst ***************************************************");
   		
   		//if(ccodes.equals(cCode)) 
   		//{	
   			List<Object[]> ReportrecordsLst=oneTimeEntryRepository.getOneTimeReportRecords(request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,cCode, CommonConstants.currentYear);
   		DatabaseContextHolder.restoreCtx();
   		System.out.println("****************************************************** in  Report ReportrecordsLst ***************************************************");
   		if(Collections.isEmpty(ReportrecordsLst)) {
   			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
   		}
   		else {
   			Response oneTimeEntryResponse = new Response();
   			oneTimeEntryResponse.setOneTimeEntryData(ReportrecordsLst.stream().map((object)->{
   				OneTimeEntryDao oneTimeEntryDao = new OneTimeEntryDao();
   				oneTimeEntryDao.setKhataNo(object[0].toString());
   				System.out.println("--------------------khataNo--------------->"+object[0].toString());
   				oneTimeEntryDao.setPreYearPendingJm(BigDecimal.valueOf(Double.valueOf(object[1].toString())));
   				oneTimeEntryDao.setPreYearPendingZp(BigDecimal.valueOf(Double.valueOf(object[2].toString())));
   				oneTimeEntryDao.setPreYearPendingGp(BigDecimal.valueOf(Double.valueOf(object[3].toString())));

   				oneTimeEntryDao.setPreYearPendingAddlLandRevenue(BigDecimal.valueOf(Double.valueOf(object[4].toString())));
   				oneTimeEntryDao.setPreYearPendingEducationalCess(BigDecimal.valueOf(Double.valueOf(object[5].toString())));
   				oneTimeEntryDao.setPreYearPendingAddlEducationalCess(BigDecimal.valueOf(Double.valueOf(object[6].toString())));
   				oneTimeEntryDao.setPreYearPendingEmployeeGuaranteeScheme(BigDecimal.valueOf(Double.valueOf(object[7].toString())));
   				oneTimeEntryDao.setPreYearPendingNaCess(BigDecimal.valueOf(Double.valueOf(object[8].toString())));
   				oneTimeEntryDao.setPreYearSankirnJmWith(BigDecimal.valueOf(Double.valueOf(object[9].toString())));
   				oneTimeEntryDao.setPreYearSankirnJmWithout(BigDecimal.valueOf(Double.valueOf(object[10].toString())));
   				oneTimeEntryDao.setPreYearNoticeFee(BigDecimal.valueOf(Double.valueOf(object[11].toString())));
   				oneTimeEntryDao.setNetPending(BigDecimal.valueOf(Double.valueOf(object[12].toString())));
   					return oneTimeEntryDao;
   				}).collect(Collectors.toList()));
   			
   			return oneTimeEntryResponse;
   		}
   		//}else {
   		//	throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
   		//}

   	
       }
}