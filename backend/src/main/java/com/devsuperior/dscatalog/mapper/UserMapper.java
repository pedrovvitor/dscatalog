package com.devsuperior.dscatalog.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.dto.UserInsertDTO;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.RoleRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Component
public class UserMapper {
	
	@Autowired
	RoleMapper roleMapper;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	public UserDTO toDto(User entity) {
		return  UserDTO.builder()
				.id(entity.getId())
				.firstName(entity.getFirstName())
				.lastName(entity.getLastName())
				.email(entity.getEmail())
				.roles(entity
						.getRoles()
						.stream()
						.map((role) -> roleMapper.toDto(role))
						.collect(Collectors.toSet()))
				.build();
	}
	
	public User toEntity(UserDTO dto) {
		return User.builder()
				.id(dto.getId())
				.firstName(dto.getFirstName())
				.lastName(dto.getLastName())
				.email(dto.getEmail())
				.roles(dto
						.getRoles()
						.stream()
						.map((roleDto) -> roleRepository
								.findById(roleDto
										.getId())
								.orElseThrow(() -> new ResourceNotFoundException("Role not Found! id = " + roleDto.getId())))
						.collect(Collectors.toSet()))
				.build();
	}
	
	public User toEntityWithPassword(UserInsertDTO dto) {
		return User.builder()
				.id(dto.getId())
				.firstName(dto.getFirstName())
				.lastName(dto.getLastName())
				.email(dto.getEmail())
				.password(passwordEncoder.encode(dto.getPassword()))
				.roles(dto
						.getRoles()
						.stream()
						.map((roleDto) -> roleRepository
								.findById(roleDto
										.getId())
								.orElseThrow(() -> new ResourceNotFoundException("Role not Found! id = " + roleDto.getId())))
						.collect(Collectors.toSet()))
				.build();
	}

}
