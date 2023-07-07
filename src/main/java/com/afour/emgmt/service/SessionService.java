/**
 * 
 */
package com.afour.emgmt.service;

import java.util.List;

import com.afour.emgmt.exception.NoDataFoundException;
import com.afour.emgmt.model.EsessionDTO;

/**
 * 
 */
public interface SessionService {

	List<EsessionDTO> findSessionEventByID(Integer eventId) throws NoDataFoundException;

	EsessionDTO findSessionByID(Integer ID) throws NoDataFoundException;

	EsessionDTO addSession(EsessionDTO dto) throws NoDataFoundException;

	EsessionDTO updateSession(EsessionDTO dto) throws NoDataFoundException;

	Boolean deleteSessionByID(Integer iD) throws NoDataFoundException;

}
