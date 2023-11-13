package com.sv.clinica.uca.clinica_uca.utils;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import com.sv.clinica.uca.clinica_uca.dto.DatosAgendarConsulta;

import jakarta.validation.ValidationException;

@Component
public class HorarioDeFuncionamientoClinica implements ValidadorDeConsulta{

	@Override
	public void validar(DatosAgendarConsulta datos) {
		// VERIFICAMOS SI EL DIA AGENDADO ES DOMINGO
		var domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());
		// VERIFICAMOS SI ES ANTES DE LAS 7 AM
		var antesApertura = datos.fecha().getHour()<7;
		// VERIFICAMOS SI ES DESPUES DE LAS 7 PM
		var despuesCierre = datos.fecha().getHour()>19;
		
		// SI CUMPLE ALGUNA DE LAS 3 VALIDACIONES ANTERIORES SE LANZARA UNA EXCEPCION
		if(domingo || antesApertura || despuesCierre) {
			throw new ValidationException("El horario de atencion de la clinica es de lunes a Sabado "
					+ "de 7:00 am a 7:00 pm");
		}
		
	}

}
