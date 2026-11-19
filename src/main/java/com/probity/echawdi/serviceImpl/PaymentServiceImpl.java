/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.probity.echawdi.dao.TalukaDao;
import com.probity.echawdi.dao.VillageDao;
import com.probity.echawdi.entity.District;
import com.probity.echawdi.entity.Village;
import com.probity.echawdi.repository.DistrictRepository;
import com.probity.echawdi.repository.TalukaRepository;
import com.probity.echawdi.repository.VillageRepository;
import com.probity.echawdi.service.PaymentService;

/**
 * @author Rushikesh Durgude
 *
 */
@Component
public class PaymentServiceImpl implements PaymentService{
	//	@Autowired
	//	private PaymentRepository paymentRepository;
	//	
	//	@Autowired
	//	private MessageSource messageSource;

	@Autowired 
	private DistrictRepository distirctRepository;
	@Autowired
	private TalukaRepository talukaRepository;
	@Autowired 
	private VillageRepository villageRepository;
	@Autowired 
	private VillageServiceImpl villageServiceImpl;

	@Override
	public ResponseEntity<?> getDropDownsDistrict(){
		List<District> distirct =distirctRepository.findAll();
		return new ResponseEntity<> (distirct,HttpStatus.OK);	
	}

	@Override
	public ResponseEntity<?> getDropDownsTaluka(String districtCode ){
		List<Object[]> taluka =talukaRepository.findAllByDistrictCode(districtCode);

		List<TalukaDao> talukaList = taluka.stream().map((object) -> {
			TalukaDao talukaDao =new TalukaDao();
			talukaDao.setDistrictCode(object[0].toString());
			talukaDao.setTalukaCode(object[1].toString());
			talukaDao.setTalukaName(object[2].toString());
			return talukaDao;
		}).collect(Collectors.toList());
		return new ResponseEntity<> (talukaList,HttpStatus.OK);	
	}

	@Override
	public ResponseEntity<?> getDropDownsVillage(String districtCode ,String talukaCode){

		List<Village> village =villageRepository.findAllByDistrictCodeAndTalukaCode(districtCode,talukaCode);

		List<VillageDao> villageDao=village.stream().map(v -> villageServiceImpl.toDao(v)).collect(Collectors.toList());

		villageDao=villageDao.stream().sorted(Comparator.comparing(VillageDao::fetchcCode)).collect(Collectors.toList());

		return new ResponseEntity<> (villageDao,HttpStatus.OK);	
	}

}