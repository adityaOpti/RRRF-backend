package com.probity.echawdi.service;

import java.util.List;

import com.probity.echawdi.entity.District;
import com.probity.echawdi.entity.Division;
import com.probity.echawdi.entity.MVillageMapper;
import com.probity.echawdi.entity.Taluka;

public interface SupervisoryDashBoardService {

	List<Division> getDropDownsDivision();

	List<District> getDropDownsDistrict(String divisionCode);

	List<Taluka> getDropDownsTaluka(String districtCode);

	List<MVillageMapper> getDropDownsVillage(String districtCode, String talukaCode);

}
