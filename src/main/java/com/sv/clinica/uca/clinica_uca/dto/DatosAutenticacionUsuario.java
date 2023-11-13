package com.sv.clinica.uca.clinica_uca.dto;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacionUsuario(
		@NotBlank String carnet,
		@NotBlank String clave) {

}
