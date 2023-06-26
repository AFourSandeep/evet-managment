/**
 * 
 */
package com.afour.emgmt.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.emgmt.entity.Visitor;
import com.afour.emgmt.mapper.VisitorMapper;
import com.afour.emgmt.model.VisitorDTO;
import com.afour.emgmt.repository.VisitorRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Service
@Slf4j
public class VisitorServiceImpl implements VisitorService {
	
	@Autowired
	VisitorMapper mapper;
	
	@Autowired
	VisitorRepository repository;

	@Override
	public List<VisitorDTO> fetchAllVisitors() {
		List<Visitor> entities = repository.findAll();
		log.info("DB operation success!");
		if (null == entities)
			return null;
		return mapper.entityToDTO(entities);
	}

	@Override
	public VisitorDTO findVisitorByID(final Integer ID) {
		Visitor entity = repository.findById(ID).get();
		log.info("DB operation success!");
		if (null == entity)
			return null;
		else
			return mapper.entityToDTO(entity);
	}
	
	@Override
	public VisitorDTO findVisitorByUserName(final String USERNAME) {
		Visitor entity = repository.findByUserName(USERNAME);
		log.info("DB operation success!");
		if (null == entity)
			return null;
		else
			return mapper.entityToDTO(entity);
	}

	@Override
	public VisitorDTO addVisitor(final VisitorDTO dto) {
		
		Visitor entity = mapper.DTOToEntity(dto);
		if (null == dto.getVisitorId()) {
			entity.setCreatedAt(LocalDateTime.now());
			entity.setCreatedBy("System");
		}
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy("System");

		entity = repository.save(entity);
		log.info("DB operation success!");
		return mapper.entityToDTO(entity);
	}

	@Override
	public VisitorDTO updateVisitor(final VisitorDTO dto) {
		Visitor entity = repository.findById(dto.getVisitorId()).get();

		log.info("DB operation success! Fetched Visitor : {}", entity);

		if (null == entity)
			return null;
		
		entity = mapper.prepareForUpdate(entity,dto);
		entity = repository.save(entity);

		return mapper.entityToDTO(entity);
	}

	@Override
	public Boolean deleteVisitorByID(final Integer ID) {
		Boolean exist = repository.existsById(ID);
		
		if(exist)
		repository.deleteById(ID);
		
		log.info("DB operation success! Deleted the visitor : {}", ID);
		
		exist = repository.existsById(ID);
		
		return !exist;
	}

}
