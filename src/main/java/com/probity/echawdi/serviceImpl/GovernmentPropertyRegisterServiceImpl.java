package com.probity.echawdi.serviceImpl;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.probity.echawdi.dao.GovernmentPropertyRegisterDto;
import com.probity.echawdi.entity.GovernmentPropertyRegister;
import com.probity.echawdi.repository.GovernmentPropertyRegisterRepository;
import com.probity.echawdi.service.GovernmentPropertyRegisterService;

@Service
@Transactional
public class GovernmentPropertyRegisterServiceImpl
        implements GovernmentPropertyRegisterService {

    @Autowired
    private GovernmentPropertyRegisterRepository repository;

    @Override
    public GovernmentPropertyRegisterDto saveGovernmentProperty(
            GovernmentPropertyRegisterDto dto) {

        GovernmentPropertyRegister entity = new GovernmentPropertyRegister();

        // DTO to Entity

        entity.setDistrictCode(dto.getDistrictCode());
        entity.setTalukaCode(dto.getTalukaCode());
        entity.setCcode(dto.getCcode());
        entity.setSevarthId(dto.getSevarthId());
        entity.setPropertyDescription(dto.getPropertyDescription());
        entity.setQuantity(dto.getQuantity());
        entity.setPurchasedDate(dto.getPurchasedDate());
        entity.setAuthorisationCertificate(dto.getAuthorisationCertificate());
        entity.setDateOfDepositInTreasury(dto.getDateOfDepositInTreasury());
        entity.setOutstandingCollectionNumber(dto.getOutstandingCollectionNumber());
        entity.setRemark(dto.getRemark());

        entity.setWrittenOffAmount(
                dto.getWrittenOffAmount() != null
                        ? dto.getWrittenOffAmount() : BigDecimal.ZERO);

        entity.setCollectedAmount(
                dto.getCollectedAmount() != null
                        ? dto.getCollectedAmount() : BigDecimal.ZERO);

        entity.setOutstandingCollectionAmount(
                dto.getOutstandingCollectionAmount() != null
                        ? dto.getOutstandingCollectionAmount() : BigDecimal.ZERO);

        entity.setActiveFlag(
                dto.getActiveFlag() != null ? dto.getActiveFlag() : true);

        GovernmentPropertyRegister savedEntity = repository.save(entity);

        // Entity to DTO

        GovernmentPropertyRegisterDto response =
                new GovernmentPropertyRegisterDto();

        response.setId(savedEntity.getId());

        response.setDistrictCode(savedEntity.getDistrictCode());
        response.setTalukaCode(savedEntity.getTalukaCode());
        response.setCcode(savedEntity.getCcode());
        response.setSevarthId(savedEntity.getSevarthId());
        response.setPropertyDescription(savedEntity.getPropertyDescription());
        response.setQuantity(savedEntity.getQuantity());
        response.setPurchasedDate(savedEntity.getPurchasedDate());
        response.setAuthorisationCertificate(savedEntity.getAuthorisationCertificate());
        response.setDateOfDepositInTreasury(savedEntity.getDateOfDepositInTreasury());
        response.setOutstandingCollectionNumber(savedEntity.getOutstandingCollectionNumber());
        response.setRemark(savedEntity.getRemark());

        response.setWrittenOffAmount(savedEntity.getWrittenOffAmount());
        response.setCollectedAmount(savedEntity.getCollectedAmount());
        response.setOutstandingCollectionAmount(savedEntity.getOutstandingCollectionAmount());

        response.setCreatedAt(savedEntity.getCreatedAt());
        response.setUpdatedAt(savedEntity.getUpdatedAt());
        response.setActiveFlag(savedEntity.getActiveFlag());

        return response;
    }
    
    
    @Override
	public List<GovernmentPropertyRegisterDto> getBySevarthId(String sevarthId) {
		

		        List<GovernmentPropertyRegister> records =
		                repository.findBySevarthId(sevarthId);

		        List<GovernmentPropertyRegisterDto> response = new ArrayList<>();

		        for (GovernmentPropertyRegister entity : records) {

		            GovernmentPropertyRegisterDto dto =
		                    new GovernmentPropertyRegisterDto();

		            dto.setId(entity.getId());

		            dto.setDistrictCode(entity.getDistrictCode());
		            dto.setTalukaCode(entity.getTalukaCode());
		            dto.setCcode(entity.getCcode());
		            dto.setSevarthId(entity.getSevarthId());

		            dto.setPropertyDescription(entity.getPropertyDescription());
		            dto.setQuantity(entity.getQuantity());

		            dto.setPurchasedDate(entity.getPurchasedDate());
		            dto.setAuthorisationCertificate(entity.getAuthorisationCertificate());

		            dto.setDateOfDepositInTreasury(entity.getDateOfDepositInTreasury());

		            dto.setOutstandingCollectionNumber(entity.getOutstandingCollectionNumber());

		            dto.setRemark(entity.getRemark());

		            dto.setWrittenOffAmount(entity.getWrittenOffAmount());
		            dto.setCollectedAmount(entity.getCollectedAmount());
		            dto.setOutstandingCollectionAmount(entity.getOutstandingCollectionAmount());

		            dto.setCreatedAt(entity.getCreatedAt());
		            dto.setUpdatedAt(entity.getUpdatedAt());

		            dto.setActiveFlag(entity.getActiveFlag());

		            response.add(dto);
		        }

		        return response;
		    
	}
    
    
    
}