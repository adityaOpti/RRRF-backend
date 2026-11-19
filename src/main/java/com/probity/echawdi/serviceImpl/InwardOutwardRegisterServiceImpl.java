
package com.probity.echawdi.serviceImpl;



import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.probity.echawdi.dao.InwardOutwardRegisterDto;
import com.probity.echawdi.entity.InwardOutwardRegister;
import com.probity.echawdi.repository.InwardOutwardRegisterRepository;
import com.probity.echawdi.service.InwardOutwardRegisterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InwardOutwardRegisterServiceImpl
        implements InwardOutwardRegisterService {

    private final InwardOutwardRegisterRepository repository;

    @Override
    @Transactional
    public String saveInwardOutwardRegister(
            InwardOutwardRegisterDto dto) {

        InwardOutwardRegister entity = new InwardOutwardRegister();

        entity.setCcode(dto.getCcode());
        entity.setDistrictCode(dto.getDistrictCode());
        entity.setTalukaCode(dto.getTalukaCode());
        entity.setSevarthId(dto.getSevarthId());

        entity.setReceivedBy(dto.getReceivedBy());
        entity.setLetterNumber(dto.getLetterNumber());
        entity.setLetterReceivedDate(dto.getLetterReceivedDate());

        entity.setDeliveredPersonName(dto.getDeliveredPersonName());
        entity.setActionRemarks(dto.getActionRemarks());
        entity.setDeliveredDate(dto.getDeliveredDate());

        entity.setRecordSequenceNumber(dto.getRecordSequenceNumber());

        entity.setRemark(dto.getRemark());

        entity.setActiveFlag(
                dto.getActiveFlag() != null ? dto.getActiveFlag() : true
        );

        repository.save(entity);

        return "Saved successfully";
    }
    
    

@Override
@Transactional(readOnly = true)
public List<InwardOutwardRegisterDto> getBySevarthId(String sevarthId) {

    List<InwardOutwardRegister> records =
            repository.findBySevarthIdOrderByCreatedOnDesc(sevarthId);

    List<InwardOutwardRegisterDto> response = new ArrayList<>();

    for (InwardOutwardRegister entity : records) {

        InwardOutwardRegisterDto dto = new InwardOutwardRegisterDto();

        dto.setId(entity.getId());

        dto.setCcode(entity.getCcode());
        dto.setDistrictCode(entity.getDistrictCode());
        dto.setTalukaCode(entity.getTalukaCode());
        dto.setSevarthId(entity.getSevarthId());

        dto.setReceivedBy(entity.getReceivedBy());
        dto.setLetterNumber(entity.getLetterNumber());
        dto.setLetterReceivedDate(entity.getLetterReceivedDate());

        dto.setDeliveredPersonName(entity.getDeliveredPersonName());
        dto.setActionRemarks(entity.getActionRemarks());
        dto.setDeliveredDate(entity.getDeliveredDate());

        dto.setRecordSequenceNumber(entity.getRecordSequenceNumber());

        dto.setCreatedOn(entity.getCreatedOn());
        dto.setUpdatedOn(entity.getUpdatedOn());

        dto.setActiveFlag(entity.getActiveFlag());
        dto.setRemark(entity.getRemark());

        response.add(dto);
    }

    return response;
}

}


