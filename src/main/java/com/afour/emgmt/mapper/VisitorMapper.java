/**
 * 
 */
package com.afour.emgmt.mapper;

import java.util.List;
import java.util.Set;

import com.afour.emgmt.entity.Visitor;
import com.afour.emgmt.model.VisitorDTO;

/**
 * 
 */
public interface VisitorMapper {
	
	VisitorDTO entityToDTO(Visitor visitor);
	
	Visitor DTOToEntity(VisitorDTO dto);
	
	List<VisitorDTO> entityToDTO(List<Visitor> entities);
	
	List<Visitor> DTOToEntity(List<VisitorDTO> dtos);

	Visitor prepareForUpdate(Visitor entity, VisitorDTO orgDTO);

	Set<VisitorDTO> entityToDTO(Set<Visitor> entities);
	
	Set<Visitor> DTOToEntity(Set<VisitorDTO> dtos);
	
	Visitor prepareForCreate(VisitorDTO dto);

}
