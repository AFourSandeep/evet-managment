/**
 * 
 */
package com.afour.emgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.emgmt.entity.Esession;
import com.afour.emgmt.mapper.SessionMapper;
import com.afour.emgmt.model.EsessionDTO;
import com.afour.emgmt.repository.SessionRepository;

/**
 * 
 */
@Service
public class SessionServiceImpl implements SessionService {
	
	@Autowired
	SessionRepository repository;
	
	@Autowired
	SessionMapper mapper;

	@Override
	public List<EsessionDTO> findSessionEventByID(final Integer eventId) {
		List<Esession> entities= repository.findSessionEventByID(eventId);
		if(entities ==null)
		return null;
		
		return mapper.entityToDTO(entities);
	}

}
