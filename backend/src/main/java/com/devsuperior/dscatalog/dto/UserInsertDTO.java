package com.devsuperior.dscatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public @Data class UserInsertDTO extends UserDTO {
	private static final long serialVersionUID = 1L;

	private String password;
}
