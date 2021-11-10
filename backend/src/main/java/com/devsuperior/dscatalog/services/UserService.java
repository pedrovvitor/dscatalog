package com.devsuperior.dscatalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.dto.UserInsertDTO;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.mapper.UserMapper;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;

	@Transactional(readOnly = true)
	public Page<UserDTO> findAll(Pageable pageable) {
		return userRepository.findAll(pageable).map(user -> userMapper.toDto(user));
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		return userMapper.toDto(userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found! Id = " + id)));
	}

	@Transactional
	public UserDTO insert(UserInsertDTO dto) {
		return userMapper.toDto(userRepository.save(userMapper.toEntityWithPassword(dto)));
	}

	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
			User user = userRepository.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("User not found! Id = " + id));
			dto.setId(user.getId());
			return userMapper
					.toDto(userRepository
							.save(userMapper
									.toEntity(dto)));
	}

	public void delete(Long id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("User not found! Id = " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

}
