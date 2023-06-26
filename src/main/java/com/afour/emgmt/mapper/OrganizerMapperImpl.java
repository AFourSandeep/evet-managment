/**
 * 
 */
package com.afour.emgmt.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afour.emgmt.entity.Organizer;
import com.afour.emgmt.model.OrganizerDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Component
@Slf4j
public class OrganizerMapperImpl implements OrganizerMapper {

	@Autowired
	ModelMapper modelMapper;

	@Override
	public OrganizerDTO entityToDTO(Organizer entity) {
		log.info("Organizer entity to DTO conversion for : {}",entity);
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

	@Override
	public Organizer prepareForUpdate(Organizer entity, OrganizerDTO orgDTO) {
		
		if (null != orgDTO.getFirstName())
			entity.setFirstName(orgDTO.getFirstName());
		if (null != orgDTO.getLastName())
			entity.setLastName(orgDTO.getLastName());
		if (null != orgDTO.getPassword())
			entity.setPassword(orgDTO.getPassword());
		if (null != orgDTO.getIsActive())
			entity.setActive(orgDTO.getIsActive());
		
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy("System");
		return entity;
	}

}
