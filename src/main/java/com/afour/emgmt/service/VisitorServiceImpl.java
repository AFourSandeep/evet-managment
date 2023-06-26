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
		if (null == entities)
			return null;
		log.info("DB operation success! Fetched {} visitors!",entities.size());
		return mapper.entityToDTO(entities);
	}

	@Override
	public VisitorDTO findVisitorByID(final Integer ID) {
		Visitor entity = repository.findById(ID).get();
		log.info("DB operation success!");
		if (null == entity)
			return null;
		log.info("DB operation success! Fetched Visitor:{} by ID: {}", entity.getVisitorId(), ID);
			return mapper.entityToDTO(entity);
	}

	@Override
	public VisitorDTO findVisitorByUserName(final String USERNAME) {
		Visitor entity = repository.findByUserName(USERNAME);
		if (null == entity)
			return null;
		
		log.info("DB operation success! Fetched Visitor:{} by username: {}", entity.getVisitorId(), USERNAME);
		return mapper.entityToDTO(entity);
	}

	@Override
	public VisitorDTO addVisitor(final VisitorDTO dto) {

		Visitor entity = mapper.DTOToEntity(dto);

		entity.setCreatedAt(LocalDateTime.now());
		entity.setCreatedBy("System");
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy("System");

		entity = repository.save(entity);
		log.info("DB operation success! Added Visitor : {}", entity.getVisitorId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public VisitorDTO updateVisitor(final VisitorDTO dto) {
		Visitor entity = repository.findById(dto.getVisitorId()).get();

		if (null == entity)
			return null;

		entity = mapper.prepareForUpdate(entity, dto);
		entity = repository.save(entity);

		log.info("DB operation success! Fetched Visitor : {}", entity);
		return mapper.entityToDTO(entity);
	}

	@Override
	public Boolean deleteVisitorByID(final Integer ID) {
		Boolean exist = repository.existsById(ID);

		if (exist)
			repository.deleteById(ID);

		exist = repository.existsById(ID);
		log.info("DB operation success! Deleted the visitor : {}", !exist);

		return !exist;
	}

}
