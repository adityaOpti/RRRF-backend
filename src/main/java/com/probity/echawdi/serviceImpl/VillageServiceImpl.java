/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.MVillageDao;
import com.probity.echawdi.dao.VillageDao;
import com.probity.echawdi.dao.VillageVasuliRecordsDao;
import com.probity.echawdi.entity.District;
import com.probity.echawdi.entity.MVillageMapper;
import com.probity.echawdi.entity.Village;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.DistrictRepository;
import com.probity.echawdi.repository.LgdMapperMisRepository;
import com.probity.echawdi.repository.MVillageMapperRepository;
import com.probity.echawdi.repository.TalukaRepository;
import com.probity.echawdi.repository.VillageRepository;
import com.probity.echawdi.service.VillageService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;
import com.probity.echawdi.utils.DataSourceType;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Ashish Dahale
 *
 */

@Component
public class VillageServiceImpl implements VillageService{

	@Autowired
	private MVillageMapperRepository mVillageMapperRepository;
	@Autowired
	private VillageRepository villageRepository;
	@Autowired
	private MessageSource messagesource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private TalukaRepository talukaRepository;
	
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private LgdMapperMisRepository lgdMapperMisRepo;
	
	
	@Override
	public List<VillageDao> getVillageListByUserName(String username,HttpServletRequest request,Locale locale){
		
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<Object[]> villageLst = villageRepository.getVillageListByUserName(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),
																				CommonConstants.mhrorUser,CommonConstants.mhrorPassword,username);
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(villageLst)) {
			throw new ResourceNotFoundException("error.form.villagelist", messagesource,locale,username);
		}else {
		if(villageLst !=null) {
		List<VillageDao> lstVillage = villageLst.stream().map((object) -> {
		VillageDao villageDao = new VillageDao();
		villageDao.setCCode(object[0].toString());
		villageDao.setUserName(object[1].toString());
		villageDao.setUserType(object[2].toString());
		villageDao.setVillageCode(object[3].toString());
		villageDao.setVillageName(object[4].toString());
		return villageDao;
		}).collect(Collectors.toList());
		return lstVillage;
		}
		return null;
		}
	}

	@Override
	public List<MVillageDao> getVillageRecords(String districtCode,String talukaCode,Locale locale,HttpServletRequest request) {
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		MVillageMapper mVillageMapper=mVillageMapperRepository.findByDistrictCodeAndTalukaCode(districtCode,talukaCode);
		List<Object[]> list = villageRepository.getVillageRecords(
				CommonConstants.AugDatamhrHost,mVillageMapper.getMhrDbName(),mVillageMapper.getMhrSchemaName(),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				mVillageMapper.getEchHost(),mVillageMapper.getEchDbName(),mVillageMapper.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword,districtCode,talukaCode,CommonConstants.currentYear 
				);
//		DatabaseContextHolder.restoreCtx();
		List<MVillageDao> lstVillage = list.stream().map((object) -> {
			
			MVillageDao mvillageDao=new MVillageDao();
			
			mvillageDao.setVillagename(object[0].toString());
			mvillageDao.setOneTimeDataEntry(BigDecimal.valueOf(Double.valueOf(object[1]==null ? "0" : object[1].toString())));
			mvillageDao.setForm17KaJaPa(BigDecimal.valueOf(Double.valueOf(object[2]==null ? "0" : object[2].toString())));
			mvillageDao.setAddLandRevenue(BigDecimal.valueOf(Double.valueOf(object[3]==null ? "0" : object[3].toString())));
			mvillageDao.setKhataMerge(BigDecimal.valueOf(Double.valueOf(object[4]==null ? "0" : object[4].toString())));
			mvillageDao.setForm1(BigDecimal.valueOf(Double.valueOf(object[5]==null ? "0" : object[5].toString())));
			mvillageDao.setForm1a(BigDecimal.valueOf(Double.valueOf(object[6]==null ? "0" : object[6].toString())));
			mvillageDao.setForm1c(BigDecimal.valueOf(Double.valueOf(object[7]==null ? "0" : object[7].toString())));
			mvillageDao.setForm1b(BigDecimal.valueOf(Double.valueOf(object[8]==null ? "0" : object[8].toString())));
			mvillageDao.setForm1d(BigDecimal.valueOf(Double.valueOf(object[9]==null ? "0" : object[9].toString())));
			mvillageDao.setForm1e(BigDecimal.valueOf(Double.valueOf(object[10]==null ? "0" : object[10].toString())));
			mvillageDao.setForm2(BigDecimal.valueOf(Double.valueOf(object[11]==null ? "0" : object[11].toString())));
			mvillageDao.setForm3(BigDecimal.valueOf(Double.valueOf(object[12]==null ? "0" : object[12].toString())));
			mvillageDao.setForm6b(BigDecimal.valueOf(Double.valueOf(object[13]==null ? "0" : object[13].toString())));
			mvillageDao.setForm6d(BigDecimal.valueOf(Double.valueOf(object[14]==null ? "0" : object[14].toString())));
			mvillageDao.setForm7a(BigDecimal.valueOf(Double.valueOf(object[15]==null ? "0" : object[15].toString())));
			mvillageDao.setForm7b(BigDecimal.valueOf(Double.valueOf(object[16]==null ? "0" : object[16].toString())));
			mvillageDao.setForm14(BigDecimal.valueOf(Double.valueOf(object[17]==null ? "0" : object[17].toString())));
			mvillageDao.setForm15(BigDecimal.valueOf(Double.valueOf(object[18]==null ? "0" : object[18].toString())));
			mvillageDao.setForm17(BigDecimal.valueOf(Double.valueOf(object[19]==null ? "0" : object[19].toString())));
			mvillageDao.setForm8b8c(BigDecimal.valueOf(Double.valueOf(object[20]==null ? "0" : object[20].toString())));
			mvillageDao.setTotalKhataNo(BigDecimal.valueOf(Double.valueOf(object[21]==null ? "0" : object[21].toString())));
			mvillageDao.setVasuliZaleli(BigDecimal.valueOf(Double.valueOf(object[22]==null ? "0" : object[22].toString())).setScale(2,RoundingMode.HALF_UP));
			mvillageDao.setNetVasuli(BigDecimal.valueOf(Double.valueOf(object[23]==null ? "0" : object[23].toString())).setScale(2,RoundingMode.HALF_UP));
			mvillageDao.setDistrictname(object[24].toString());
			mvillageDao.setTalukaname(object[25].toString());
			mvillageDao.setCCode(object[26].toString());
		return mvillageDao;
		}).collect(Collectors.toList());
		
		return lstVillage;
	}
	
	public VillageDao toDao(Village village) {
		VillageDao villageDao =new VillageDao();
		BeanUtils.copyProperties(village, villageDao);
		return villageDao;
	}
	@Override
	public List<Response> getDropDownsDistrict(HttpServletRequest request, Locale locale){
		DatabaseContextHolder.setCtx(DataSourceType.echmst);
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<District> distirct =districtRepository.findAll();
		DatabaseContextHolder.restoreCtx();
		return distirct.stream().map(r->{
			Response response=new Response();
			BeanUtils.copyProperties(r, response);
			return response;
		}).collect(Collectors.toList());
	}

	@Override
	public List<Response> getDropDownsTaluka(String districtCode,HttpServletRequest request, Locale locale ){
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		DatabaseContextHolder.setCtx(DataSourceType.echmst);
		List<Object[]> taluka =talukaRepository.findAllByDistrictCode(districtCode);
		DatabaseContextHolder.restoreCtx();
		return taluka.stream().map(r->{
			Response response =new Response();
			response.setDistrictCode(r[0].toString());
			response.setTalukaCode(r[1].toString());
			response.setTalukaName(r[2].toString());
			return response;
		}).collect(Collectors.toList());

	}

	@Override
	public List<Response> getDropDownsVillage(String districtCode ,String talukaCode,HttpServletRequest request, Locale locale){
		DatabaseContextHolder.setCtx(DataSourceType.echmst);
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		MVillageMapper mVillageMapper=mVillageMapperRepository.findByDistrictCodeAndTalukaCode(districtCode,talukaCode);
		List<Object[]> village =mVillageMapperRepository.findAllByDistrictCodeAndTalukaCode(mVillageMapper.getMhrHost(),mVillageMapper.getMhrDbName(),mVillageMapper.getMhrSchemaName(),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,districtCode,talukaCode);
		DatabaseContextHolder.restoreCtx();
		return village.stream().map(r->{
			Response lrd=new Response();
			lrd.setVillageName(r[0].toString());
			lrd.setDistrictCode(r[1].toString());
			lrd.setTalukaCode(r[2].toString());
			lrd.setcCode(r[3].toString());
			return lrd;
		}).collect(Collectors.toList());
	}

	@Override
	public List<VillageVasuliRecordsDao> getVillageVasuliRecords(String districtCode,
			String talukaCode,
			String revenueYear,
			Locale locale,
			HttpServletRequest request
			) {
		
			
		List<Object[]> list = lgdMapperMisRepo.getByVillageVasuliRecords(
				districtCode,
				talukaCode,
				revenueYear
				);
		
		List<VillageVasuliRecordsDao> 
			vasuliRecords = list.stream().map((object) -> {
			
			
			VillageVasuliRecordsDao VasuliMisReocords = new VillageVasuliRecordsDao();
			
			
			
			VasuliMisReocords.setDistrictCode(object[0] != null ? object[0].toString() : "");
			VasuliMisReocords.setDistrictName(object[1] != null ? object[1].toString() : "");
			VasuliMisReocords.setTalukaCode(object[2] != null ? object[2].toString() : "");
			VasuliMisReocords.setTalukaName(object[3] != null ? object[3].toString() : "");
			VasuliMisReocords.setCcode(object[4] != null ? object[4].toString() : "");
			VasuliMisReocords.setVillageName(object[5] != null ? object[5].toString() : "");
			VasuliMisReocords.setTotalDemandJm(object[6] != null ? object[6].toString() : "0");
			VasuliMisReocords.setTotalDemandSankirn(object[7] != null ? object[7].toString() : "0");
			VasuliMisReocords.setTotalCollectedJm(object[8] != null ? object[8].toString() : "0");
			VasuliMisReocords.setTotalCollectedSankirn(object[9] != null ? object[9].toString() : "0");
			VasuliMisReocords.setRevenueYear(object[10] != null ? object[10].toString() : "0");
			
//			VasuliMisReocords.setDistrictCode(object[0].toString());
//			VasuliMisReocords.setDistrictName(object[1].toString());
//			VasuliMisReocords.setTalukaCode(object[2].toString());
//			VasuliMisReocords.setTalukaName(object[3].toString());
//			VasuliMisReocords.setCcode(object[4].toString());
//			VasuliMisReocords.setVillageName(object[5].toString());
//			VasuliMisReocords.setTotalDemandJm(object[6].toString());
//			VasuliMisReocords.setTotalDemandSankirn(object[7].toString());
//			VasuliMisReocords.setTotalCollectedJm(object[8].toString());
//			VasuliMisReocords.setTotalCollectedSankirn(object[8].toString());
			
			
			
			
		return VasuliMisReocords;
		}).collect(Collectors.toList());
		
		return vasuliRecords;
		
		
	}
}