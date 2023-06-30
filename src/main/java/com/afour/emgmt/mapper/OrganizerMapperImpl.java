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
import com.afour.emgmt.util.ActorEnum;

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

	@Override
	public Organizer prepareForUpdate(Organizer entity, OrganizerDTO dto) {
		
		if (null != dto.getFirstName())
			entity.setFirstName(dto.getFirstName());
		if (null != dto.getLastName())
			entity.setLastName(dto.getLastName());
		if (null != dto.getPassword())
			entity.setPassword(dto.getPassword());
		if (null != dto.getIsActive())
			entity.setActive(dto.getIsActive());
		
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy(ActorEnum.DEFAULT_USER.getUser());
		return entity;
	}

}
