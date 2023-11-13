package com.sv.clinica.uca.clinica_uca.dto;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroPaciente(
		@NotBlank(message = "Nombre es necesario")
		String nombre,
		@NotBlank(message = "apellido es necesario")
		String apellido,
		@NotBlank(message = "El documento es necesario")
		String documentoId,
		String telefono) {

}
