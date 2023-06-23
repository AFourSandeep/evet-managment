/**
 * 
 */
package com.afour.emgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.emgmt.entity.Organizer;
import com.afour.emgmt.mapper.OrganizerMapper;
import com.afour.emgmt.model.OrganizerDTO;
import com.afour.emgmt.repository.OrganizerRepository;

/**
 * 
 */
@Service
public class OgranizerServiceImpl implements OrganizerService {

	@Autowired
	OrganizerMapper mapper;

	@Autowired
	OrganizerRepository repository;

	@Override
	public List<OrganizerDTO> fetchAllOrganizers() {
		List<Organizer> entities = repository.findAll();
		if (null != entities && entities.size() > 0)
			return mapper.entityToDTO(entities);
		else
			return null;
	}

}
