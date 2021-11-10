package com.devsuperior.dscatalog.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
	
	@NotBlank(message = "Campo Obrigatório")
	private String firstName;
	private String lastName;
	
	@Email(message = "Favor entrar email válido")
	private String email;
	
private Set<RoleDTO> roles;
	
	public Set<RoleDTO> getRoles() {
		if (roles == null) {
			roles = new HashSet<>();
		}
		return roles;
	}
	
}
