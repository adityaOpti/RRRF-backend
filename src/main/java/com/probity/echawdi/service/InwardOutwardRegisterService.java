package com.probity.echawdi.service;

import java.util.List;

import com.probity.echawdi.dao.InwardOutwardRegisterDto;

public interface InwardOutwardRegisterService {

    String saveInwardOutwardRegister(InwardOutwardRegisterDto dto);
    
    
    List<InwardOutwardRegisterDto> getBySevarthId(String sevarthId);
}

