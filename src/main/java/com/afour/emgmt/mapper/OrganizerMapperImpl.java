/**
 * 
 */
package com.afour.emgmt.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afour.emgmt.entity.Organizer;
import com.afour.emgmt.model.OrganizerDTO;

/**
 * 
 */
@Component
public class OrganizerMapperImpl implements OrganizerMapper {

	@Autowired
	ModelMapper modelMapper;

	@Override
	public OrganizerDTO entityToDTO(Organizer entity) {
		return modelMapper.map(entity, OrganizerDTO.class);
	}

	@Override
	public Organizer DTOToEntity(OrganizerDTO dto) {
		return modelMapper.map(dto, Organizer.class);
	}

	@Override
	public List<OrganizerDTO> entityToDTO(List<Organizer> entities) {
		return entities
				.stream()
				.map(entity -> entityToDTO(entity))
				.collect(Collectors.toList());
	}

	@Override
	public List<Organizer> DTOToEntity(List<OrganizerDTO> dtos) {
		return dtos
				.stream()
				.map(dto -> DTOToEntity(dto))
				.collect(Collectors.toList());
	}

}
