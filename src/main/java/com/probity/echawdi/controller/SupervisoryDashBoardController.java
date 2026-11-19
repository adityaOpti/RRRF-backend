package com.probity.echawdi.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.config.WithDatabase;
import com.probity.echawdi.dao.Form1Dao;
import com.probity.echawdi.entity.DashboardSupervisoryDtl;
import com.probity.echawdi.entity.District;
import com.probity.echawdi.entity.Division;
import com.probity.echawdi.entity.MVillageMapper;
import com.probity.echawdi.entity.Taluka;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.DistrictRepository;
import com.probity.echawdi.repository.MVillageMapperRepository;
import com.probity.echawdi.repository.SupervisoryDashBoardRepository;
import com.probity.echawdi.repository.TalukaRepository;
import com.probity.echawdi.service.SupervisoryDashBoardService;
import com.probity.echawdi.utils.DataSourceType;

@RestController
@RequestMapping("/api/dashboard")
public class SupervisoryDashBoardController {
	@Autowired
	private SupervisoryDashBoardService supervisoryDashBoardService;
	
	@Autowired
	private SupervisoryDashBoardRepository supervisoryDashBoardRepository;
	
	@Autowired 
	private DistrictRepository districtRepository;
	
	@Autowired 
	private TalukaRepository talukaRepository;
	
	@Autowired 
	private MVillageMapperRepository mVillageMapperRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@WithDatabase(DataSourceType.echmst)
	@PostMapping("/supervisoryDashBoardByDivision")
	public List<DashboardSupervisoryDtl> supervisoryDashBoard(@RequestParam String divisionCode){
		try {
			List<Object[]> result=supervisoryDashBoardRepository.getByDivisionCode(divisionCode);
			return result.stream().map(obj->{
				DashboardSupervisoryDtl dashboardSupervisoryDtl=new DashboardSupervisoryDtl();
				dashboardSupervisoryDtl.setTotalDistricts(obj[0].toString());
				dashboardSupervisoryDtl.setTotalTalukas(obj[1].toString());
				dashboardSupervisoryDtl.setTotalVillages(obj[2].toString());
				return dashboardSupervisoryDtl;
			}).collect(Collectors.toList());			
		} catch (Exception e) {
			return null;	
		}
	}
	
	@WithDatabase(DataSourceType.echmst)
	@PostMapping("/supervisoryDashBoardByDistrict")
	public List<DashboardSupervisoryDtl> supervisoryDashBoardByDistrict(@RequestParam String divisionCode){
		try {
			List<Object[]> result=supervisoryDashBoardRepository.getByDivisionByDistrictCode(divisionCode);
			return result.stream().map(obj->{
				DashboardSupervisoryDtl dashboardSupervisoryDtl=new DashboardSupervisoryDtl();
				dashboardSupervisoryDtl.setDistrictCode(obj[0].toString());
				dashboardSupervisoryDtl.setDistrictName(obj[1].toString());
				dashboardSupervisoryDtl.setTotalDistricts(obj[2].toString());
				dashboardSupervisoryDtl.setTotalTalukas(obj[3].toString());
				dashboardSupervisoryDtl.setTotalVillages(obj[4].toString());
				return dashboardSupervisoryDtl;
			}).collect(Collectors.toList());			
		} catch (Exception e) {
			return null;	
		}
	}
	
	@WithDatabase(DataSourceType.echmst)
	@PostMapping("/supervisoryDashBoardAllRecords")
	public Response supervisoryDashBoardAllRecords(@RequestParam String divisionCode,
			@RequestParam String districtCode,
			@RequestParam String talukaCode,Locale locale){
		
		List<Object[]> list =new ArrayList<>();
		if(divisionCode!=null && districtCode.isEmpty() && talukaCode.isEmpty()) {
			list=districtRepository.getDropDownsDistrict(divisionCode);
		}else if(districtCode!=null && divisionCode!=null && talukaCode.isEmpty() ) {
			list=talukaRepository.getDropDownsTaluka(divisionCode,districtCode);
		}else  {
			list=mVillageMapperRepository.findAllByDistrictCodeAndTalukaCode(districtCode,talukaCode);
		}
		
		if(list.isEmpty()) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}
		
		Response response =new  Response();
		response.setDashboardSupervisoryDtlData(list.stream().map((obj) -> {
			DashboardSupervisoryDtl dashboardSupervisoryDtl = new DashboardSupervisoryDtl();
			dashboardSupervisoryDtl.setDistrictCode(obj[0].toString());
			dashboardSupervisoryDtl.setDistrictName(obj[1].toString());
			dashboardSupervisoryDtl.setTalukaCode(obj[2].toString());
			dashboardSupervisoryDtl.setTalukaName(obj[3].toString());
			dashboardSupervisoryDtl.setCCode(obj[4].toString());
			dashboardSupervisoryDtl.setVillageName(obj[5].toString());
			return dashboardSupervisoryDtl;
		}).collect(Collectors.toList()));
		return response;	
	}	
	@WithDatabase(DataSourceType.echmst)
	@GetMapping("/getDropDownsDivision")
	public List<Division> getDropDownsDivision() {
		return supervisoryDashBoardService.getDropDownsDivision();
	}
	
	@WithDatabase(DataSourceType.echmst)
	@GetMapping("/getDropDownsDistrict")
	public List<District> getDropDownsDistrict(@RequestParam String divisionCode) {
		return supervisoryDashBoardService.getDropDownsDistrict(divisionCode);
	}
	@WithDatabase(DataSourceType.echmst)
	@GetMapping("/getDropDownsTaluka")
	public List<Taluka> getDropDownsTaluka(@RequestParam String districtCode) {
		return supervisoryDashBoardService.getDropDownsTaluka(districtCode);
	}
	@WithDatabase(DataSourceType.echmst)
	@GetMapping("/getDropDownsVillage")
	public List<MVillageMapper> getDropDownsVillage(@RequestParam String districtCode,@RequestParam String talukaCode) {
			return supervisoryDashBoardService.getDropDownsVillage(districtCode,talukaCode);
	}
	
}
