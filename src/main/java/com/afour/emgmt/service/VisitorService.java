/**
 * 
 */
package com.afour.emgmt.service;

import java.util.List;

import com.afour.emgmt.model.VisitorDTO;

/**
 * 
 */
public interface VisitorService {

	List<VisitorDTO> fetchAllVisitors();

	VisitorDTO findVisitorByID(Integer ID);

	VisitorDTO findVisitorByUserName(String USERNAME);

	VisitorDTO addVisitor(VisitorDTO orgDTO);

	VisitorDTO updateVisitor(VisitorDTO dto);

	Boolean deleteVisitorByID(Integer ID);

	List<VisitorDTO> findVisitorsByEventId(Integer eventId);

}
