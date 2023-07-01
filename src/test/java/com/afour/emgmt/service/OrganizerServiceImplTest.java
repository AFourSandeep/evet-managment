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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.afour.emgmt.config.SpringDataJPAConfiguration;
import com.afour.emgmt.entity.Organizer;
import com.afour.emgmt.model.OrganizerDTO;
import com.afour.emgmt.repository.OrganizerRepository;
import com.afour.emgmt.repository.RoleRepository;
import com.afour.emgmt.util.MySQLTestImage;
import com.afour.emgmt.util.TestUtils;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringDataJPAConfiguration.class })
@Testcontainers
class OrganizerServiceImplTest {

	@Autowired
	OrganizerService service;

	@Autowired
	OrganizerRepository repository;

	@Autowired
	RoleRepository roleRepository;

	@SuppressWarnings("rawtypes")
	@Container
	private static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer(MySQLTestImage.MYSQL_80_IMAGE)
			.withDatabaseName("event_management").withInitScript("event_management.sql");

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
	void fetchAllOrganizers() {
		List<Organizer> organizers = List.of(TestUtils.buildOrganizer("User1"),
				TestUtils.buildOrganizer("User2"),
				TestUtils.buildOrganizer("User3"),
				TestUtils.buildOrganizer("User4"),
				TestUtils.buildOrganizer("User5"));
		repository.saveAll(organizers);
		List<OrganizerDTO> dtos = service.fetchAllOrganizers();
		assertNotNull(dtos);
		assertTrue(!dtos.isEmpty());
	}

	@DisplayName("findOrganizerByID")
	@ParameterizedTest
	@ValueSource(strings = { "USER101", "USER201" })
	void findOrganizerByID(String userName) {
		Organizer organizer = TestUtils.buildOrganizer(userName);
		organizer = repository.saveAndFlush(organizer);
		Integer id = organizer.getOrganizerId();
		OrganizerDTO resultDTO = service.findOrganizerByID(id);
		assertNotNull(resultDTO);
		assertEquals(organizer.getOrganizerId(), resultDTO.getOrganizerId());
		assertEquals(organizer.getUserName(), resultDTO.getUserName());
	}

	@DisplayName("findOrganizerByUserName")
	@ParameterizedTest
	@ValueSource(strings = { "USER1011", "USER2011" })
	void findOrganizerByUserName(String userName) {
		Organizer organizer = TestUtils.buildOrganizer(userName);
		organizer = repository.saveAndFlush(organizer);
		OrganizerDTO resultDTO = service.findOrganizerByUserName(userName);
		assertNotNull(resultDTO);
		assertEquals(organizer.getOrganizerId(), resultDTO.getOrganizerId());
		assertEquals(organizer.getUserName(), resultDTO.getUserName());
	}

	@DisplayName("addOrganizer")
	@ParameterizedTest
	@ValueSource(strings = { "USER1101", "USER2201" })
	void addOrganizer(String userName) {
		OrganizerDTO inputDTO = TestUtils.buildOrganizerDTO(userName);
		OrganizerDTO resultDTO = service.addOrganizer(inputDTO);
		assertNotNull(resultDTO);
		assertNotNull(resultDTO.getOrganizerId());
		assertEquals(inputDTO.getUserName(), resultDTO.getUserName());
	}

	@DisplayName("updateOrganizer")
	@ParameterizedTest
	@ValueSource(strings = { "USER3101", "USER3201" })
	void updateOrganizer(String userName) {
		Organizer exist = TestUtils.buildOrganizer(userName);
		exist = repository.saveAndFlush(exist);

		OrganizerDTO updateResuest = OrganizerDTO.builder().organizerId(exist.getOrganizerId())
				.firstName(userName + "ABCD").password(userName + "456").build();

		OrganizerDTO resultDTO = service.updateOrganizer(updateResuest);
		assertNotNull(resultDTO);
		assertEquals(exist.getOrganizerId(), resultDTO.getOrganizerId());
		assertEquals(updateResuest.getFirstName(), resultDTO.getFirstName());
		assertEquals(updateResuest.getPassword(), resultDTO.getPassword());
	}

	@DisplayName("deleteOrganizerByID")
	@ParameterizedTest
	@ValueSource(strings = { "USER1301", "USER2301" })
	void deleteOrganizerByID(String userName) {
		Organizer input = TestUtils.buildOrganizer(userName);
		repository.saveAndFlush(input);

		Integer id = input.getOrganizerId();

		boolean result = service.deleteOrganizerByID(id);
		assertTrue(result);
	}

	@AfterAll
	public static void tearDown() {
		mySQLContainer.stop();
	}

}
