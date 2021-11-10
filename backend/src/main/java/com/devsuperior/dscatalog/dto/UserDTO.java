package com.devsuperior.dscatalog.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public @Data class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	
private Set<RoleDTO> roles;
	
	public Set<RoleDTO> getRoles() {
		if (roles == null) {
			roles = new HashSet<>();
		}
		return roles;
	}
	
}
