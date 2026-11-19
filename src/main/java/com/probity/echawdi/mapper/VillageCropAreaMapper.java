package com.probity.echawdi.mapper;

import org.mapstruct.Mapper;

import com.probity.echawdi.dao.VillageCropAreaDTO;
import com.probity.echawdi.entity.VillageCropArea;

@Mapper(componentModel = "spring")
public interface VillageCropAreaMapper {

	
	

	    VillageCropAreaDTO toDto(VillageCropArea villageCropArea);

	    VillageCropArea toEntity(VillageCropAreaDTO villageCropAreaDTO);
	
	

	   

	
}