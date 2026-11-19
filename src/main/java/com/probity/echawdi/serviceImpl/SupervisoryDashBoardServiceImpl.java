package com.probity.echawdi.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.probity.echawdi.entity.District;
import com.probity.echawdi.entity.Division;
import com.probity.echawdi.entity.MVillageMapper;
import com.probity.echawdi.entity.Taluka;
import com.probity.echawdi.repository.DistrictRepository;
import com.probity.echawdi.repository.DivisionRepository;
import com.probity.echawdi.repository.MVillageMapperRepository;
import com.probity.echawdi.repository.TalukaRepository;
import com.probity.echawdi.service.Form1Service;
import com.probity.echawdi.service.SupervisoryDashBoardService;
@Component
public class SupervisoryDashBoardServiceImpl implements SupervisoryDashBoardService {
	
	@Autowired
	private MVillageMapperRepository mVillageMapperRepository;
	@Autowired
	private TalukaRepository talukaRepository;
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private DivisionRepository divisionRepository;
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public List<Division> getDropDownsDivision() {
		List<Object[]> division =divisionRepository.findAllDivision();
		return division.stream().map(r->{
			Division response=new Division();
			response.setDivisionCode(r[0].toString());
			response.setDivisionName(r[1].toString());
			return response;
		}).collect(Collectors.toList());
	}

	@Override
	public List<District> getDropDownsDistrict(String divisionCode){
		List<Object[]> distirct =districtRepository.getDropDownsDistrictDtl(divisionCode);
		return distirct.stream().map(r->{
			District response=new District();
			response.setDistrictCode(r[0].toString());
			response.setDistrictName(r[1].toString());
//			response.setCount(r[2].toString());
			return response;
		}).collect(Collectors.toList());
	}

	@Override
	public List<Taluka> getDropDownsTaluka(String districtCode ){
		List<Object[]> taluka =talukaRepository.getDropDownsTalukaDtl(districtCode);
		return taluka.stream().map(r->{
			Taluka response =new Taluka();
			response.setTalukaCode(r[0].toString());
			response.setTalukaName(r[1].toString());
			response.setDistrictCode(r[2].toString());
//			response.setCount(r[3].toString());
			
			return response;
		}).collect(Collectors.toList());

	}

	@Override
	public List<MVillageMapper> getDropDownsVillage(String districtCode ,String talukaCode){
//		MVillageMapper mVillageMapper=mVillageMapperRepository.findByDistrictCodeAndTalukaCode(districtCode,talukaCode);
		List<Object[]> village =mVillageMapperRepository.findAllByDistrictCodeAndTalukaCode(districtCode,talukaCode);
		return village.stream().map(r->{
			MVillageMapper lrd=new MVillageMapper();
			lrd.setCCode(r[0].toString());
			lrd.setVillageName(r[1].toString());
			lrd.setDistrictCode(r[2].toString());
			lrd.setTalukaCode(r[3].toString());
//			lrd.setCount(r[4].toString());
			return lrd;
		}).collect(Collectors.toList());
	}

}
