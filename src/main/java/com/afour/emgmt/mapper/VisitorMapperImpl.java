/**
 * 
 */
package com.afour.emgmt.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afour.emgmt.entity.Visitor;
import com.afour.emgmt.model.VisitorDTO;

/**
 * 
 */
@Component
public class VisitorMapperImpl implements VisitorMapper {

	@Autowired
	ModelMapper modelMapper;

	@Override
	public VisitorDTO entityToDTO(Visitor entity) {
		return modelMapper.map(entity, VisitorDTO.class);
	}

	@Override
	public Visitor DTOToEntity(VisitorDTO dto) {
		return modelMapper.map(dto, Visitor.class);
	}

	@Override
	public List<VisitorDTO> entityToDTO(List<Visitor> entities) {
		return entities
				.stream()
				.map(entity -> entityToDTO(entity))
				.collect(Collectors.toList());
	}

	@Override
	public List<Visitor> DTOToEntity(List<VisitorDTO> dtos) {
		return dtos
				.stream()
				.map(dto -> DTOToEntity(dto))
				.collect(Collectors.toList());
	}

	@Override
	public Visitor prepareForUpdate(Visitor entity, VisitorDTO dto) {
		if (null != dto.getFirstName())
			entity.setFirstName(dto.getFirstName());
		if (null != dto.getLastName())
			entity.setLastName(dto.getLastName());
		if (null != dto.getPassword())
			entity.setPassword(dto.getPassword());
		if (null != dto.getIsActive())
			entity.setActive(dto.getIsActive());
		
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy("System");
		return entity;
	}

	@Override
	public Set<VisitorDTO> entityToDTO(Set<Visitor> entities) {
		return entities
				.stream()
				.map(entity -> entityToDTO(entity))
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Visitor> DTOToEntity(Set<VisitorDTO> dtos) {
		return dtos
				.stream()
				.map(dto -> DTOToEntity(dto))
				.collect(Collectors.toSet());
	}

}
