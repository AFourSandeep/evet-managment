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

import com.afour.emgmt.entity.Esession;
import com.afour.emgmt.model.EsessionDTO;

/**
 * 
 */
@Component
public class SessionMapperImpl implements SessionMapper {

	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public EsessionDTO entityToDTO(Esession entity) {
		return modelMapper.map(entity, EsessionDTO.class);
	}

	@Override
	public Esession DTOToEntity(EsessionDTO dto) {
		return modelMapper.map(dto, Esession.class);
	}

	@Override
	public List<EsessionDTO> entityToDTO(List<Esession> entities) {
		return entities
				.stream()
				.map(entity -> entityToDTO(entity))
				.collect(Collectors.toList());
	}

	@Override
	public List<Esession> DTOToEntity(List<EsessionDTO> dtos) {
		return dtos
				.stream()
				.map(dto -> DTOToEntity(dto))
				.collect(Collectors.toList());
	}

	@Override
	public Esession prepareForUpdate(Esession entity, EsessionDTO dto) {
		if (null != dto.getEsessionTitle())
			entity.setEsessionTitle(dto.getEsessionTitle());
		if (null != dto.getStartAt())
			entity.setStartAt(dto.getStartAt());
		if (null != dto.getEndAt())
			entity.setEndAt(dto.getEndAt());
		
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy("System");
		return entity;
	}

}
