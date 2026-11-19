package com.probity.echawdi.service;

import java.util.List;

import com.probity.echawdi.dao.GovernmentPropertyRegisterDto;

public interface GovernmentPropertyRegisterService {

    GovernmentPropertyRegisterDto saveGovernmentProperty(
            GovernmentPropertyRegisterDto dto);
    
    List<GovernmentPropertyRegisterDto> getBySevarthId(String sevarthId);
}