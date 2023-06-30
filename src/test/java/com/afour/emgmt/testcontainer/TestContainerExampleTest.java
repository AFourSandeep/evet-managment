/**
 * 
 */
package com.afour.emgmt.testcontainer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.afour.emgmt.config.EventMgmtConfiguration;
import com.afour.emgmt.config.SpringDataJPAConfiguration;
import com.afour.emgmt.config.SpringMVCWebAppInitializer;
import com.afour.emgmt.entity.Organizer;
import com.afour.emgmt.entity.Role;
import com.afour.emgmt.repository.OrganizerRepository;
import com.afour.emgmt.util.ActorEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringMVCWebAppInitializer.class,EventMgmtConfiguration.class,
		SpringDataJPAConfiguration.class } /* , loader = AnnotationConfigContextLoader.class */)
@Testcontainers
@Slf4j
public class TestContainerExampleTest {
	
	
	@Container
	private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest"); 
	
	@DynamicPropertySource
	public static void overrideContainerProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
	    dynamicPropertyRegistry.add("jdbc.url",mySQLContainer::getJdbcUrl);
	    dynamicPropertyRegistry.add("jdbc.username",mySQLContainer::getUsername);
	    dynamicPropertyRegistry.add("jdbc.password",mySQLContainer::getPassword);
	}
	
	@Autowired
	private OrganizerRepository repository;
	
	@Test
	void test() {
		
	}
	
	@Test
	void addNewUser_andGetCount() {
		Role role = Role.builder().roleId(1).build();
		Organizer organizer = Organizer.builder().userName("User101")
		.firstName("User")
		.lastName("lastname")
		.createdAt(LocalDateTime.now())
		.updatedAt(LocalDateTime.now())
		.password("password")
		.role(role)
		.createdBy(ActorEnum.DEFAULT_USER.getUser())
		.updatedBy(ActorEnum.DEFAULT_USER.getUser())
		.isActive(true)
		.build();
		
//		Organizer savedUser = repository.save(organizer);

//	    List<Organizer> userList = repository.findAll();
	    log.info(mySQLContainer.getContainerId());
	    log.info(mySQLContainer.getDatabaseName());
	    log.info(mySQLContainer.getJdbcUrl());
//	    assertNotNull(savedUser);

//	    assertEquals(1, userList.size());

	}

}
