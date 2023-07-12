/**
 *
 */
package com.afour.emgmt.service;

import java.util.List;
import java.util.Optional;

import com.afour.emgmt.common.RoleEnum;
import org.springframework.stereotype.Service;

import com.afour.emgmt.entity.Role;
import com.afour.emgmt.entity.User;
import com.afour.emgmt.exception.NoDataFoundException;
import com.afour.emgmt.exception.UndefinedRoleException;
import com.afour.emgmt.exception.UserAlreadyExistException;
import com.afour.emgmt.mapper.UserMapper;
import com.afour.emgmt.model.UserDTO;
import com.afour.emgmt.repository.RoleRepository;
import com.afour.emgmt.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@Service
public class OrganizerServiceImpl implements OrganizerService {

    private final
    UserMapper mapper;

    private final
    UserRepository repository;

    private final
    RoleRepository roleRepository;

    public OrganizerServiceImpl(UserMapper mapper, UserRepository repository, RoleRepository roleRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserDTO> fetchAllOrganizers() {
        List<User> entities = repository.findAll();

        log.info("DB operation success! Fetched {} Organizers!", entities.size());
        return mapper.entityToDTO(entities);
    }

    @Override
    public UserDTO findOrganizerByID(final Integer ID) {
        Optional<User> optional = repository.findById(ID);
        return optional.map(entity -> {
            log.info("DB operation success! Fetched User:{} ", entity.getUserId());
            return mapper.entityToDTO(entity);
        }).orElseThrow(NoDataFoundException::new);
    }

    @Override
    public UserDTO findOrganizerByUserName(final String USERNAME) {
        Optional<User> optional = repository.findByUserName(USERNAME);
        return optional.map(entity -> {
            log.info("DB operation success! Fetched User:{} by username: {}", entity.getUserId(), USERNAME);
            return mapper.entityToDTO(entity);
        }).orElseThrow(NoDataFoundException::new);
    }

    @Override
    public UserDTO addOrganizer(final UserDTO dto) {
		Optional<User> user = repository.findByUserName(dto.getUserName());
        if (user.isPresent()) {
            throw new UserAlreadyExistException();
        }

		Role role = roleRepository.findById(RoleEnum.ORGANIZER)
				.orElseThrow(UndefinedRoleException::new);

		User entity = mapper.prepareForCreate(dto);
        entity.setRole(role);

        entity = repository.save(entity);
        log.info("DB operation success! Added User : {}", entity.getUserId());
        return mapper.entityToDTO(entity);
    }

    @Override
    public UserDTO updateOrganizer(final UserDTO dto) {
		User user = repository.findById(dto.getUserId())
				.map(e -> mapper.prepareForUpdate(e, dto))
				.orElseThrow(NoDataFoundException::new);

		user = repository.save(user);
		log.info("DB operation success! Updated Organizer : {}", user.getUserId());
		return mapper.entityToDTO(user);
    }

    @Override
    public boolean deleteOrganizerByID(final Integer ID) {
        boolean exist = repository.existsById(ID);
        if (!exist) throw new NoDataFoundException();

        repository.deleteById(ID);

        exist = repository.existsById(ID);
        log.info("DB operation success! Deleted the Organizer : {}", !exist);
        return !exist;
    }

}
