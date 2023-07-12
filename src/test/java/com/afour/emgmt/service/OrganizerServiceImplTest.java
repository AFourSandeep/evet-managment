package com.afour.emgmt.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.afour.emgmt.config.EventMgmtConfiguration;
import com.afour.emgmt.config.SpringDataJPAConfiguration;
import com.afour.emgmt.entity.User;
import com.afour.emgmt.exception.NoDataFoundException;
import com.afour.emgmt.exception.UndefinedRoleException;
import com.afour.emgmt.exception.UserAlreadyExistException;
import com.afour.emgmt.model.UserDTO;
import com.afour.emgmt.repository.RoleRepository;
import com.afour.emgmt.repository.UserRepository;
import com.afour.emgmt.util.MySQLTestImage;
import com.afour.emgmt.util.TestUtils;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringDataJPAConfiguration.class, EventMgmtConfiguration.class })
@Testcontainers
class OrganizerServiceImplTest {

	@Autowired
	OrganizerService service;

	@Autowired
	UserRepository repository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@SuppressWarnings("rawtypes")
	@Container
	private static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer(MySQLTestImage.MYSQL_80_IMAGE)
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

	@DisplayName("fetchAllOrganizers")
	@Test
	void fetchAllOrganizers() throws NoDataFoundException, Exception{
		List<User> organizers = List.of(TestUtils.buildOrganizer("User1"),
				TestUtils.buildOrganizer("User2"),
				TestUtils.buildOrganizer("User3"),
				TestUtils.buildOrganizer("User4"),
				TestUtils.buildOrganizer("User5"));
		repository.saveAll(organizers);
		List<UserDTO> dtos = service.fetchAllOrganizers();
		assertNotNull(dtos);
		assertTrue(!dtos.isEmpty());
	}

	@DisplayName("findOrganizerByID")
	@ParameterizedTest
	@ValueSource(strings = { "USER101", "USER201" })
	void findOrganizerByID(String userName) throws NoDataFoundException, Exception{
		User organizer = TestUtils.buildOrganizer(userName);
		organizer = repository.saveAndFlush(organizer);
		Integer id = organizer.getUserId();
		UserDTO resultDTO = service.findOrganizerByID(id);
		assertNotNull(resultDTO);
		assertEquals(organizer.getUserId(), resultDTO.getUserId());
		assertEquals(organizer.getUserName(), resultDTO.getUserName());
	}

	@DisplayName("findOrganizerByUserName")
	@ParameterizedTest
	@ValueSource(strings = { "USER1011", "USER2011" })
	void findOrganizerByUserName(String userName) throws NoDataFoundException, Exception{
		User organizer = TestUtils.buildOrganizer(userName);
		organizer = repository.saveAndFlush(organizer);
		UserDTO resultDTO = service.findOrganizerByUserName(userName);
		assertNotNull(resultDTO);
		assertEquals(organizer.getUserId(), resultDTO.getUserId());
		assertEquals(organizer.getUserName(), resultDTO.getUserName());
	}

	@DisplayName("addOrganizer")
	@ParameterizedTest
	@ValueSource(strings = { "USER1101", "USER2201" })
	void addOrganizer(String userName) throws UserAlreadyExistException, UndefinedRoleException, Exception {
		UserDTO inputDTO = TestUtils.buildOrganizerDTO(userName);
		UserDTO resultDTO = service.addOrganizer(inputDTO);
		assertNotNull(resultDTO);
		assertNotNull(resultDTO.getUserId());
		assertEquals(inputDTO.getUserName(), resultDTO.getUserName());
	}

	@DisplayName("updateOrganizer")
	@ParameterizedTest
	@ValueSource(strings = { "USER3101", "USER3201" })
	void updateOrganizer(String userName) throws NoDataFoundException, Exception{
		User exist = TestUtils.buildOrganizer(userName);
		exist = repository.saveAndFlush(exist);
		
		UserDTO updateResuest = UserDTO.builder().userId(exist.getUserId())
				.firstName(userName + "ABCD").password(userName + "456").build();

		UserDTO resultDTO = service.updateOrganizer(updateResuest);
		assertNotNull(resultDTO);
		assertEquals(exist.getUserId(), resultDTO.getUserId());
		assertEquals(updateResuest.getFirstName(), resultDTO.getFirstName());
		assertNotNull(resultDTO.getPassword());
	}

	@DisplayName("deleteOrganizerByID")
	@ParameterizedTest
	@ValueSource(strings = { "USER1301", "USER2301" })
	void deleteOrganizerByID(String userName) throws NoDataFoundException, Exception{
		User input = TestUtils.buildOrganizer(userName);
		repository.saveAndFlush(input);

		Integer id = input.getUserId();

		boolean result = service.deleteOrganizerByID(id);
		assertTrue(result);
	}

	@AfterAll
	public static void tearDown() {
		mySQLContainer.stop();
	}

}
