/**
 * 
 */
package com.afour.emgmt.service;

import java.util.List;

import com.afour.emgmt.model.EsessionDTO;

/**
 * 
 */
public interface SessionService {

	List<EsessionDTO> findSessionEventByID(Integer eventId);

	EsessionDTO findSessionByID(Integer ID);

	EsessionDTO addSession(EsessionDTO dto);

	EsessionDTO updateSession(EsessionDTO dto);

	Boolean deleteSessionByID(Integer iD);

}
