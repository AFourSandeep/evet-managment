/**
 * 
 */
package com.afour.emgmt.util;

import java.time.LocalDateTime;
import java.util.Random;

import com.afour.emgmt.entity.Esession;
import com.afour.emgmt.entity.Event;
import com.afour.emgmt.entity.Role;
import com.afour.emgmt.entity.User;
import com.afour.emgmt.model.EsessionDTO;
import com.afour.emgmt.model.EventDTO;
import com.afour.emgmt.model.UserDTO;

/**
 * 
 */
public class TestUtils {

	private static final Integer ORGANIZER = 1;
	private static final Integer VISITOR = 1;

	private static final LocalDateTime NOW = LocalDateTime.now();

	private static final LocalDateTime PAST10DAYS = LocalDateTime.now().minusDays(10);

	private static final LocalDateTime FUTURE10DAYS = LocalDateTime.now().plusDays(10);
	private static final LocalDateTime FUTURE15DAYS = LocalDateTime.now().plusDays(15);

	public static Integer getRandomNumber() {
		Random random = new Random();
		return random.nextInt(100);
	}

	public static Role buildRole(Integer roleId) {
		return Role.builder().roleId(roleId).build();
	}

	public static User buildOrganizer(String userName) {
		return User.builder().userName(userName).firstName("First").lastName("Last").createdAt(PAST10DAYS)
				.updatedAt(NOW).password("password").role(buildRole(ORGANIZER))
				.createdBy(UtilConstant.DEFAULT_USER).updatedBy(UtilConstant.DEFAULT_USER).isActive(true)
				.build();
	}

	public static UserDTO buildOrganizerDTO(String userName) {
		return UserDTO.builder().userName(userName).firstName("First").lastName("Last").password("password")
				.build();
	}

	public static User buildVisitor(String UserName) {
		return User.builder().userName(UserName).firstName("First").lastName("Last").createdAt(PAST10DAYS)
				.updatedAt(NOW).password("password").role(buildRole(VISITOR))
				.createdBy(UtilConstant.DEFAULT_USER).updatedBy(UtilConstant.DEFAULT_USER).isActive(true)
				.build();
	}

	public static UserDTO buildVisitorDTO(String userName) {
		return UserDTO.builder().userName(userName).firstName("First").lastName("Last").password("password").build();
	}

	public static Event buildEvent(String eventName, User owner) {
		return Event.builder().eventName(eventName).owner(owner).location("Pune, India").startAt(FUTURE10DAYS).endAt(FUTURE15DAYS)
				.createdAt(PAST10DAYS).updatedAt(NOW).createdBy(UtilConstant.DEFAULT_USER)
				.updatedBy(UtilConstant.DEFAULT_USER).isClosed(false).build();
	}
	
	public static Esession buildEsession(String title, Event event) {
		return Esession.builder().esessionTitle(title).event(event)
				.startAt(FUTURE10DAYS).endAt(FUTURE15DAYS).createdAt(PAST10DAYS).updatedAt(NOW)
				.createdBy(UtilConstant.DEFAULT_USER).updatedBy(UtilConstant.DEFAULT_USER)
				.build();
	} 
	
	public static EsessionDTO buildEsessionDTO(String title, EventDTO event) {
		return EsessionDTO.builder().esessionTitle(title).event(event).build();
	}

}
