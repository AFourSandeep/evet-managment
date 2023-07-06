package com.afour.emgmt.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.afour.emgmt.entity.Event;
import com.afour.emgmt.entity.User;
import com.afour.emgmt.model.UserDTO;
import com.afour.emgmt.model.UserRegistrationDTO;
import com.afour.emgmt.repository.EventRepository;
import com.afour.emgmt.repository.RoleRepository;
import com.afour.emgmt.repository.UserRepository;
import com.afour.emgmt.util.MySQLTestImage;
import com.afour.emgmt.util.TestUtils;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringDataJPAConfiguration.class })
@Testcontainers
class VisitorServiceImplTest {

	@Autowired
	VisitorService service;

	@Autowired
	UserRepository repository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	RoleRepository roleRepository;

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

	@DisplayName("fetchAllVisitors")
	@Test
	void fetchAllVisitors() {
		List<User> visitors = List.of(TestUtils.buildVisitor("User1"), TestUtils.buildVisitor("User2"),
				TestUtils.buildVisitor("User3"), TestUtils.buildVisitor("User4"), TestUtils.buildVisitor("User5"));
		repository.saveAll(visitors);
		List<UserDTO> dtos = service.fetchAllVisitors();
		assertNotNull(dtos);
		assertTrue(!dtos.isEmpty());
	}

	@DisplayName("findVisitorByID")
	@ParameterizedTest
	@ValueSource(strings = { "USER101", "USER201" })
	void findVisitorByID(String userName) {
		User entity = TestUtils.buildVisitor(userName);
		entity = repository.saveAndFlush(entity);
		Integer id = entity.getUserId();
		UserDTO resultDTO = service.findVisitorByID(id);
		assertNotNull(resultDTO);
		assertEquals(id, resultDTO.getUserId());
		assertEquals(entity.getUserName(), resultDTO.getUserName());
	}

	@DisplayName("findVisitorByUserName")
	@ParameterizedTest
	@ValueSource(strings = { "USER1011", "USER2011" })
	void findVisitorByUserName(String userName) {
		User entity = TestUtils.buildVisitor(userName);
		entity = repository.saveAndFlush(entity);
		UserDTO resultDTO = service.findVisitorByUserName(userName);
		assertNotNull(resultDTO);
		assertEquals(entity.getUserId(), resultDTO.getUserId());
		assertEquals(entity.getUserName(), resultDTO.getUserName());
	}

	@DisplayName("addVisitor")
	@ParameterizedTest
	@ValueSource(strings = { "USER1101", "USER2201" })
	void addVisitor(String userName) {
		UserDTO inputDTO = TestUtils.buildVisitorDTO(userName);
		UserDTO resultDTO = service.addVisitor(inputDTO);
		assertNotNull(resultDTO);
		assertNotNull(resultDTO.getUserId());
		assertEquals(inputDTO.getUserName(), resultDTO.getUserName());
	}

	@DisplayName("updateVisitor")
	@ParameterizedTest
	@ValueSource(strings = { "USER3101", "USER3201" })
	void updateVisitor(String userName) {
		User exist = TestUtils.buildVisitor(userName);
		exist = repository.saveAndFlush(exist);

		UserDTO updateResuest = UserDTO.builder().userId(exist.getUserId()).firstName(userName + "ABCD")
				.password(userName + "456").build();

		UserDTO resultDTO = service.updateVisitor(updateResuest);
		assertNotNull(resultDTO);
		assertEquals(exist.getUserId(), resultDTO.getUserId());
		assertEquals(updateResuest.getFirstName(), resultDTO.getFirstName());
		assertNotNull(resultDTO.getPassword());
	}

	@DisplayName("deleteVisitorByID")
	@ParameterizedTest
	@ValueSource(strings = { "USER1301", "USER2301" })
	void deleteVisitorByID(String userName) {
		User input = TestUtils.buildVisitor(userName);
		repository.saveAndFlush(input);

		Integer id = input.getUserId();

		boolean result = service.deleteVisitorByID(id);
		assertTrue(result);
	}

	@DisplayName("registerVisitorForEvent")
	@ParameterizedTest
	@ValueSource(strings = { "USER3101", "USER3201" })
	void registerVisitorForEvent_mutliple_events(String userName) {
		// Given
		User owner = TestUtils.buildOrganizer("Event Owner");
		owner = repository.saveAndFlush(owner);

		User visitor = TestUtils.buildVisitor(userName);
		visitor = repository.saveAndFlush(visitor);

		List<Event> events = List.of(TestUtils.buildEvent("Some Event", owner),
				TestUtils.buildEvent("Another Event", owner));
		events = eventRepository.saveAll(events);
		Set<Integer> eventIds = events.stream().map(e -> e.getEventId()).collect(Collectors.toSet());

		UserRegistrationDTO registrationRequest = UserRegistrationDTO.builder().userId(visitor.getUserId())
				.eventIds(eventIds).build();

		UserDTO resultDTO = service.registerVisitorForEvent(registrationRequest);
		assertNotNull(resultDTO);
		assertNotNull(resultDTO.getEvents());
		assertEquals(2, resultDTO.getEvents().size());
	}
	
	@AfterAll
	public static void tearDown() {
		mySQLContainer.stop();
	}

}
