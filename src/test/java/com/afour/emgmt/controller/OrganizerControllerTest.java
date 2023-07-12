/**
 *
 */
package com.afour.emgmt.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.afour.emgmt.common.AppResponse;
import com.afour.emgmt.common.GenericResponse;
import com.afour.emgmt.model.UserDTO;
import com.afour.emgmt.service.OrganizerService;
import com.afour.emgmt.util.TestUtils;

/**
 *
 */
@Disabled
@ExtendWith(MockitoExtension.class)
class OrganizerControllerTest {

    @Mock
    OrganizerService orgService;

    @Mock
    MessageSource messages;

    @Mock
    GenericResponse genericResponse;

    @InjectMocks
    OrganizerController orgController;

    @DisplayName("fetchAllOrganizers_for_data")
    @Test
    void fetchAllOrganizers_for_data() {
        // given
        List<UserDTO> dtos = List.of(UserDTO.builder().userId(1).userName("User1").build(),
                UserDTO.builder().userId(2).userName("User2").build(),
                UserDTO.builder().userId(3).userName("User3").build(),
                UserDTO.builder().userId(4).userName("User4").build());
        when(orgService.fetchAllOrganizers()).thenReturn(dtos);
        when(genericResponse.getSuccessDataFoundResponse(dtos, dtos.size())).thenCallRealMethod();
        when(messages.getMessage(anyString(), any(Object[].class), any()))
                .thenReturn("");
        // when
        ResponseEntity<AppResponse> response = orgController.fetchAllOrganizers();

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        @SuppressWarnings("unchecked")
        List<UserDTO> resultDtos = (List<UserDTO>) response.getBody().getBody();
        assertNotNull(resultDtos);
        assertEquals(HttpStatus.OK, response.getBody().getStatus());
        assertEquals(dtos.size(), resultDtos.size());
        assertAll(() -> assertEquals(1, resultDtos.get(0).getUserId()),
                () -> assertEquals("User1", resultDtos.get(0).getUserName()),
                () -> assertEquals(2, resultDtos.get(1).getUserId()),
                () -> assertEquals("User2", resultDtos.get(1).getUserName()));
    }

    @DisplayName("fetchAllOrganizers_for_no_data")
    @Test
    void fetchAllOrganizers_for_no_data() {
        //given
        when(orgService.fetchAllOrganizers()).thenReturn(null);

        //when
        var result = orgController.fetchAllOrganizers();
        //then
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @DisplayName("findOrganizerByID_for_data")
    @ParameterizedTest
    @ValueSource(ints = {101, 102, 103, 104})
    void findOrganizerByID_for_data(Integer ID) {
        // given
        when(orgService.findOrganizerByID(ID)).thenReturn(UserDTO.builder().userId(ID).userName("User" + ID).build());

        // when
        var response = orgController.findOrganizerByID(ID);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserDTO resultDto = (UserDTO) response.getBody().getBody();
        assertNotNull(resultDto);
        assertEquals(HttpStatus.OK, response.getBody().getStatus());
        assertAll(() -> assertEquals(ID, resultDto.getUserId()),
                () -> assertEquals("User" + ID, resultDto.getUserName()));
    }

    @DisplayName("findOrganizerByID_for_no_data")
    @ParameterizedTest
    @ValueSource(ints = {101, 102})
    void findOrganizerByID_for_no_data(Integer ID) {
        //given
        when(orgService.findOrganizerByID(ID)).thenReturn(null);

        //when
        var result = orgController.findOrganizerByID(ID);
        //then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody().getBody());
    }

    @DisplayName("findOrganizerByID_for_empty_param")
    @Test
    void findOrganizerByID_for_empty_param() {
        // given

        // when
        var result = orgController.findOrganizerByID(null);
        // then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNull(result.getBody().getBody());
    }

    @DisplayName("findOrganizerByUserName_for_data")
    @ParameterizedTest
    @ValueSource(strings = {"User1", "User2", "User3", "User4"})
    void findOrganizerByUserName_for_data(String username) {
        // given
        Integer ID = TestUtils.getRandomNumber();
        when(orgService.findOrganizerByUserName(username))
                .thenReturn(UserDTO.builder().userId(ID).userName(username).build());

        // when
        var response = orgController.findOrganizerByUserName(username);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserDTO resultDto = (UserDTO) response.getBody().getBody();
        assertNotNull(resultDto);
        assertEquals(HttpStatus.OK, response.getBody().getStatus());
        assertAll(() -> assertEquals(ID, resultDto.getUserId()),
                () -> assertEquals(username, resultDto.getUserName()));
    }

    @DisplayName("findOrganizerByUserName_for_no_data")
    @ParameterizedTest
    @ValueSource(strings = {"User1", "User2"})
    void findOrganizerByUserName_for_no_data(String username) {
        //given
        when(orgService.findOrganizerByUserName(username)).thenReturn(null);

        //when
        var result = orgController.findOrganizerByUserName(username);
        //then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody().getBody());
    }

    @DisplayName("findOrganizerByUserName_for_empty_param")
    @Test
    void findOrganizerByUserName_for_empty_param() {
        // given

        // when
        var result = orgController.findOrganizerByUserName(null);
        // then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNull(result.getBody().getBody());
    }

    @DisplayName("addOrganizer_for_data")
    @ParameterizedTest
    @CsvSource({"User1,User101", "User2,User202", "User3,User303"})
    void addOrganizer_for_data(String firstName, String username) {
        // given
        Integer ID = TestUtils.getRandomNumber();
        UserDTO request = UserDTO.builder()
                .firstName(firstName)
                .userName(username)
                .build();
        when(orgService.addOrganizer(request))
                .thenReturn(UserDTO.builder()
                        .userId(ID)
                        .firstName(firstName)
                        .userName(username)
                        .build());

        // when
        var response = orgController.addOrganizer(request);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserDTO resultDto = (UserDTO) response.getBody().getBody();
        assertNotNull(resultDto);
        assertEquals(HttpStatus.CREATED, response.getBody().getStatus());
        assertAll(() -> assertEquals(ID, resultDto.getUserId()),
                () -> assertEquals(firstName, resultDto.getFirstName()),
                () -> assertEquals(username, resultDto.getUserName()));
    }

    @DisplayName("addOrganizer_for_no_data")
    @ParameterizedTest
    @CsvSource({"User1,User101", "User2,User202", "User3,User303"})
    void addOrganizer_for_no_data(String firstName, String username) {
        //given
        UserDTO request = UserDTO.builder()
                .firstName(firstName)
                .userName(username)
                .build();
        when(orgService.addOrganizer(any())).thenReturn(null);

        //when
        var result = orgController.addOrganizer(request);
        //then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNull(result.getBody().getBody());
    }

    @DisplayName("addOrganizer_for_empty_param")
    @Test
    void addOrganizer_for_empty_param() {
        // given

        // when
        var result = orgController.addOrganizer(null);
        // then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNull(result.getBody().getBody());
    }

}
