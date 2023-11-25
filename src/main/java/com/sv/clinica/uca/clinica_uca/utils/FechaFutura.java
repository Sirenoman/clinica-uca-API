package com.sv.clinica.uca.clinica_uca.utils;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.sv.clinica.uca.clinica_uca.dto.DatosAgendarConsulta;

import jakarta.validation.ValidationException;

@Component
public class FechaFutura implements ValidadorDeConsulta {

	@Override
	public void validar(DatosAgendarConsulta datos) {
		var ahora = LocalDateTime.now();
		var horaConsulta = datos.fecha();

		if (ahora == horaConsulta) {
			throw new ValidationException("La cita debe ser para mañana o en adelante");
		}
	}

}
