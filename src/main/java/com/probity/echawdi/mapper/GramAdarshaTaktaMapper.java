package com.probity.echawdi.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.probity.echawdi.dao.GramAdarshaTaktaSaveDTO;
import com.probity.echawdi.entity.GramAdarshTaktaEntity;
//@Mapper(uses = VillageCropAreaMapper.class)
@Mapper(componentModel = "spring", uses = VillageCropAreaMapper.class)
public interface GramAdarshaTaktaMapper {
	
	
 
	    GramAdarshaTaktaSaveDTO toDto(GramAdarshTaktaEntity gramAdarshaTakta);

	    
	    GramAdarshTaktaEntity toEntity(GramAdarshaTaktaSaveDTO gramAdarshaTaktaDTO);
	    
	    
	    //just added for getapi
	    List<GramAdarshaTaktaSaveDTO> toDtoList(List<GramAdarshTaktaEntity> entities);

}
