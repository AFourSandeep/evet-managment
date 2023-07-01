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
import com.afour.emgmt.entity.Organizer;
import com.afour.emgmt.entity.Visitor;
import com.afour.emgmt.model.VisitorDTO;
import com.afour.emgmt.model.VisitorRegistrationDTO;
import com.afour.emgmt.repository.EventRepository;
import com.afour.emgmt.repository.OrganizerRepository;
import com.afour.emgmt.repository.RoleRepository;
import com.afour.emgmt.repository.VisitorRepository;
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
	VisitorRepository repository;

	@Autowired
	OrganizerRepository organizerRepository;

	@Autowired
	EventRepository eventRepository;

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

	@DisplayName("fetchAllVisitors")
	@Test
	void fetchAllVisitors() {
		List<Visitor> visitors = List.of(TestUtils.buildVisitor("User1"), TestUtils.buildVisitor("User2"),
				TestUtils.buildVisitor("User3"), TestUtils.buildVisitor("User4"), TestUtils.buildVisitor("User5"));
		repository.saveAll(visitors);
		List<VisitorDTO> dtos = service.fetchAllVisitors();
		assertNotNull(dtos);
		assertTrue(!dtos.isEmpty());
	}

	@DisplayName("findVisitorByID")
	@ParameterizedTest
	@ValueSource(strings = { "USER101", "USER201" })
	void findVisitorByID(String userName) {
		Visitor entity = TestUtils.buildVisitor(userName);
		entity = repository.saveAndFlush(entity);
		Integer id = entity.getVisitorId();
		VisitorDTO resultDTO = service.findVisitorByID(id);
		assertNotNull(resultDTO);
		assertEquals(id, resultDTO.getVisitorId());
		assertEquals(entity.getUserName(), resultDTO.getUserName());
	}

	@DisplayName("findVisitorByUserName")
	@ParameterizedTest
	@ValueSource(strings = { "USER1011", "USER2011" })
	void findVisitorByUserName(String userName) {
		Visitor entity = TestUtils.buildVisitor(userName);
		entity = repository.saveAndFlush(entity);
		VisitorDTO resultDTO = service.findVisitorByUserName(userName);
		assertNotNull(resultDTO);
		assertEquals(entity.getVisitorId(), resultDTO.getVisitorId());
		assertEquals(entity.getUserName(), resultDTO.getUserName());
	}

	@DisplayName("addVisitor")
	@ParameterizedTest
	@ValueSource(strings = { "USER1101", "USER2201" })
	void addVisitor(String userName) {
		VisitorDTO inputDTO = TestUtils.buildVisitorDTO(userName);
		VisitorDTO resultDTO = service.addVisitor(inputDTO);
		assertNotNull(resultDTO);
		assertNotNull(resultDTO.getVisitorId());
		assertEquals(inputDTO.getUserName(), resultDTO.getUserName());
	}

	@DisplayName("updateVisitor")
	@ParameterizedTest
	@ValueSource(strings = { "USER3101", "USER3201" })
	void updateVisitor(String userName) {
		Visitor exist = TestUtils.buildVisitor(userName);
		exist = repository.saveAndFlush(exist);

		VisitorDTO updateResuest = VisitorDTO.builder().visitorId(exist.getVisitorId()).firstName(userName + "ABCD")
				.password(userName + "456").build();

		VisitorDTO resultDTO = service.updateVisitor(updateResuest);
		assertNotNull(resultDTO);
		assertEquals(exist.getVisitorId(), resultDTO.getVisitorId());
		assertEquals(updateResuest.getFirstName(), resultDTO.getFirstName());
		assertEquals(updateResuest.getPassword(), resultDTO.getPassword());
	}

	@DisplayName("deleteVisitorByID")
	@ParameterizedTest
	@ValueSource(strings = { "USER1301", "USER2301" })
	void deleteVisitorByID(String userName) {
		Visitor input = TestUtils.buildVisitor(userName);
		repository.saveAndFlush(input);

		Integer id = input.getVisitorId();

		boolean result = service.deleteVisitorByID(id);
		assertTrue(result);
	}

	@DisplayName("registerVisitorForEvent")
	@ParameterizedTest
	@ValueSource(strings = { "USER3101", "USER3201" })
	void registerVisitorForEvent_mutliple_events(String userName) {
		// Given
		Organizer owner = TestUtils.buildOrganizer("Event Owner");
		owner = organizerRepository.saveAndFlush(owner);

		Visitor visitor = TestUtils.buildVisitor(userName);
		visitor = repository.saveAndFlush(visitor);

		List<Event> events = List.of(TestUtils.buildEvent("Some Event", owner),
				TestUtils.buildEvent("Another Event", owner));
		events = eventRepository.saveAll(events);
		Set<Integer> eventIds = events.stream().map(e -> e.getEventId()).collect(Collectors.toSet());

		VisitorRegistrationDTO registrationRequest = VisitorRegistrationDTO.builder().visitorId(visitor.getVisitorId())
				.eventIds(eventIds).build();

		VisitorDTO resultDTO = service.registerVisitorForEvent(registrationRequest);
		assertNotNull(resultDTO);
		assertNotNull(resultDTO.getEvents());
		assertEquals(2, resultDTO.getEvents().size());
	}
	
	@AfterAll
	public static void tearDown() {
		mySQLContainer.stop();
	}

}
