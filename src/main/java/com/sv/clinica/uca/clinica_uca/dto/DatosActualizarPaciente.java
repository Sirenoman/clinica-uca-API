package com.sv.clinica.uca.clinica_uca.dto;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarPaciente(
		@NotNull Long id,
		String nombre,
		String apellido,
		String telefono) {

}
