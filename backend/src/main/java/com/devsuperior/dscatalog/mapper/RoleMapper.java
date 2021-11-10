package com.devsuperior.dscatalog.mapper;

import org.springframework.stereotype.Component;

import com.devsuperior.dscatalog.dto.RoleDTO;
import com.devsuperior.dscatalog.entities.Role;

@Component
public class RoleMapper {

	public RoleDTO toDto(Role role) {
		return  RoleDTO.builder()
				.id(role.getId())
				.authority(role.getAuthority())
				.build();
	}
	
	public Role toEntity(RoleDTO dto) {
		return Role.builder()
				.id(dto.getId())
				.authority(dto.getAuthority())
				.build();
	}
}
