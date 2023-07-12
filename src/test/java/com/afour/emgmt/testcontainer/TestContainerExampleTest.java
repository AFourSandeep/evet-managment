/**
 * 
 */
package com.afour.emgmt.testcontainer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.afour.emgmt.common.Actor;
import com.afour.emgmt.common.RoleEnum;
import com.afour.emgmt.config.SpringDataJPAConfiguration;
import com.afour.emgmt.entity.Role;
import com.afour.emgmt.entity.User;
import com.afour.emgmt.repository.RoleRepository;
import com.afour.emgmt.repository.UserRepository;
import com.afour.emgmt.util.MySQLTestImage;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringDataJPAConfiguration.class })
@Testcontainers
@Slf4j
public class TestContainerExampleTest {

	@Container
	private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>(MySQLTestImage.MYSQL_80_IMAGE)
			.withDatabaseName("event_mgmt").withInitScript("event_mgmt.sql");

	@DynamicPropertySource
	public static void overrideContainerProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("jdbc.url", mySQLContainer::getJdbcUrl);
		dynamicPropertyRegistry.add("jdbc.username", mySQLContainer::getUsername);
		dynamicPropertyRegistry.add("jdbc.password", mySQLContainer::getPassword);
	}

	@BeforeAll
	public static void setUp() {
		mySQLContainer.withReuse(true);
		mySQLContainer.start();
	}

	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleRepository roleRepository;

	@Test
	void addNewOrganizer_andGetCount() {
		Role role = Role.builder().roleId(RoleEnum.ORGANIZER).build();
		User organizer = User.builder().userName("User1011").firstName("User").lastName("lastname")
				.createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).password("password").role(role)
				.createdBy(Actor.DEFAULT_USER).updatedBy(Actor.DEFAULT_USER).isActive(true)
				.build();

		log.info(mySQLContainer.getJdbcUrl());
		log.info(String.valueOf(mySQLContainer.getJdbcDriverInstance()));
		log.info(String.valueOf(mySQLContainer.getPortBindings()));

		User savedUser = repository.save(organizer);

		List<User> userList = repository.findAll();

		assertNotNull(savedUser.getUserId());

		assertEquals(1, userList.size());

		assertEquals("User", userList.get(0).getFirstName());

	}

	@Test
	void testExplicitInitScript() {

		List<Role> roleList = roleRepository.findAll();

		log.info(roleList.toString());
		assertEquals(2, roleList.size());

	}

	@AfterAll
	public static void tearDown() {
		mySQLContainer.stop();
//		mySQLContainer.stop();
	}

}
