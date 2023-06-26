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

}
