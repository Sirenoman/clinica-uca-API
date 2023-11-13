package com.sv.clinica.uca.clinica_uca.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroMedico(
		@NotBlank(message = "Nombre es requerido")
		String nombre,
		@NotBlank(message = "Email es requerido")
		@Email
		String email,
		@NotBlank
		String telefono,
		@NotBlank
		String documento,
		@NotNull
		Especialidad especialidad) {

}
