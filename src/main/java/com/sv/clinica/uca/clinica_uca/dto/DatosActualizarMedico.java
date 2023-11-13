package com.sv.clinica.uca.clinica_uca.dto;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarMedico(
		@NotNull Long id,
		String nombre,
		String telefono,
		String email,
		String documento
		) {

}
