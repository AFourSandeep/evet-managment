/**
 * 
 */
package com.afour.emgmt.mapper;

import java.util.List;
import java.util.Set;

import com.afour.emgmt.entity.Esession;
import com.afour.emgmt.model.EsessionDTO;

/**
 * 
 */
public interface SessionMapper {
	
	EsessionDTO entityToDTO(Esession entity);
	
	Esession DTOToEntity(EsessionDTO dto);
	
	List<EsessionDTO> entityToDTO(List<Esession> entities);
	
	List<Esession> DTOToEntity(List<EsessionDTO> dtos);

	Esession prepareForUpdate(Esession entity, EsessionDTO dto);

	Set<EsessionDTO> entityToDTO(Set<Esession> sessions);
	
	Set<Esession> DTOToEntity(Set<EsessionDTO> sessions);

	Esession prepareForCreate(EsessionDTO dto);

}
