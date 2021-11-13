package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.services.validation.UserUpdateValid;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@UserUpdateValid
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public @Data class UserUpdateDTO extends UserDTO {
	private static final long serialVersionUID = 1L;

}
