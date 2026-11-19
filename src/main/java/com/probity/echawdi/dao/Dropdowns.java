package com.probity.echawdi.dao;

import com.probity.echawdi.entity.District;
import com.probity.echawdi.entity.Taluka;
import com.probity.echawdi.entity.Village;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Dropdowns {

public District district;
public Taluka taluka;
public Village village;
}
