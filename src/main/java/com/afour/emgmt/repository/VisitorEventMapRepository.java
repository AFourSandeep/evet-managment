/**
 * 
 */
package com.afour.emgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afour.emgmt.entity.UserEventMap;

/**
 * 
 */
@Repository
public interface VisitorEventMapRepository extends JpaRepository<UserEventMap, Integer>{

}
