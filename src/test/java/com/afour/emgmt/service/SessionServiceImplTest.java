package com.afour.emgmt.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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
import com.afour.emgmt.entity.Esession;
import com.afour.emgmt.entity.Event;
import com.afour.emgmt.entity.Organizer;
import com.afour.emgmt.model.EsessionDTO;
import com.afour.emgmt.model.EventDTO;
import com.afour.emgmt.repository.EventRepository;
import com.afour.emgmt.repository.OrganizerRepository;
import com.afour.emgmt.repository.SessionRepository;
import com.afour.emgmt.util.MySQLTestImage;
import com.afour.emgmt.util.TestUtils;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringDataJPAConfiguration.class })
@Testcontainers
class SessionServiceImplTest {
	
	@Autowired
	SessionService service;
	
	@Autowired
	SessionRepository repository;
	
	@Autowired
	OrganizerRepository organizerRepository;
	
	@Autowired
	SessionRepository sessionRepository;

	@Autowired
	EventRepository eventRepository;
	
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

	@DisplayName("findSessionEventByID")
	@ParameterizedTest
	@ValueSource(strings = { "Session-101", "Session-201" })
	void findSessionEventByID(String title) {
		
		Organizer owner = TestUtils.buildOrganizer("Event Owner");
		owner = organizerRepository.saveAndFlush(owner);

		Event event = TestUtils.buildEvent("Some Event", owner);
		event = eventRepository.saveAndFlush(event);
		
		List<Esession> sessions = List.of(TestUtils.buildEsession(title, event),
				TestUtils.buildEsession(title+"-22", event));
		
		sessions = repository.saveAll(sessions);
		
		List<EsessionDTO> resultDTO = service.findSessionEventByID(event.getEventId());
		
		assertNotNull(resultDTO);
		assertEquals(sessions.size(), resultDTO.size());
	}
	
	@DisplayName("findSessionByID")
	@ParameterizedTest
	@ValueSource(strings = { "Session-101", "Session-201" })
	void findSessionByID(String title) {
		
		Organizer owner = TestUtils.buildOrganizer("Event Owner");
		owner = organizerRepository.saveAndFlush(owner);

		Event event = TestUtils.buildEvent("Some Event", owner);
		event = eventRepository.saveAndFlush(event);
		
		List<Esession> sessions = List.of(TestUtils.buildEsession(title, event),
				TestUtils.buildEsession(title+"-22", event));
		
		sessions = repository.saveAll(sessions);
		
		EsessionDTO resultDTO = service.findSessionByID(sessions.get(0).getEsessionId());
		
		assertNotNull(resultDTO);
		assertEquals(sessions.get(0).getEsessionTitle(), resultDTO.getEsessionTitle());
	}
	
	@DisplayName("addSession")
	@ParameterizedTest
	@ValueSource(strings = { "Session-101", "Session-201" })
	void addSession(String title) {
		
		Organizer owner = TestUtils.buildOrganizer("Event Owner");
		owner = organizerRepository.saveAndFlush(owner);

		Event event = TestUtils.buildEvent("Some Event", owner);
		event = eventRepository.saveAndFlush(event);
		
		EventDTO eventDTO = EventDTO.builder().eventId(event.getEventId()).build();
		
		EsessionDTO requestDTO = TestUtils.buildEsessionDTO(title, eventDTO);
		
		EsessionDTO resultDTO = service.addSession(requestDTO);
		
		assertNotNull(resultDTO);
		assertNotNull(resultDTO.getEvent());
	}
	
	@DisplayName("updateSession")
	@ParameterizedTest
	@ValueSource(strings = { "Session-101", "Session-201" })
	void updateSession(String title) {
		
		Organizer owner = TestUtils.buildOrganizer("Event Owner");
		owner = organizerRepository.saveAndFlush(owner);

		Event event = TestUtils.buildEvent("Some Event", owner);
		event = eventRepository.saveAndFlush(event);
		
		Esession session = TestUtils.buildEsession(title, event);
		session = sessionRepository.save(session);
		
		
		EventDTO eventDTO = EventDTO.builder().eventId(event.getEventId()).build();
		
		EsessionDTO requestDTO = TestUtils.buildEsessionDTO(title, eventDTO);
		requestDTO.setEsessionId(session.getEsessionId());
		requestDTO.setEsessionTitle(title+"Updated");
		
		EsessionDTO resultDTO = service.updateSession(requestDTO);
		
		assertNotNull(resultDTO);
		assertNotNull(resultDTO.getEvent());
	}
	
	@DisplayName("findSessionByID")
	@ParameterizedTest
	@ValueSource(strings = { "Session-101", "Session-201" })
	void deleteSessionByID(String title) {
		
		Organizer owner = TestUtils.buildOrganizer("Event Owner");
		owner = organizerRepository.saveAndFlush(owner);

		Event event = TestUtils.buildEvent("Some Event", owner);
		event = eventRepository.saveAndFlush(event);
		
		Esession session = TestUtils.buildEsession(title, event);
		session = sessionRepository.save(session);
		
		boolean resultDTO = service.deleteSessionByID(session.getEsessionId());
		
		assertTrue(resultDTO);
	}
	
	@AfterAll
	public static void tearDown() {
		mySQLContainer.stop();
	}

}
