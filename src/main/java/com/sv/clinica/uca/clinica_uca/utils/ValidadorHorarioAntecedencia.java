package com.sv.clinica.uca.clinica_uca.utils;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sv.clinica.uca.clinica_uca.dto.DatosCancelamientoConsulta;
import com.sv.clinica.uca.clinica_uca.repositories.ConsultaRepository;

import jakarta.validation.ValidationException;

@Component("ValidadorHorarioAntecedenciaCancelamiento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamientoConsulta{

	@Autowired
	private ConsultaRepository consultaRepo;
	
	@Override
	public void validar(DatosCancelamientoConsulta datos) {
		// OBTENEMOS LA INSTANCIA DE UNA CONSULTA CON ID_CONSULTA
		var consulta = consultaRepo.getReferenceById(datos.idConsulta());
		var ahora = LocalDateTime.now();
		/**
		 * CORROBORAMOS SI EXISTE UNA DIFERENCIA DE HORARIO DE MAS DE 24 HORAS ENTRE 
		 * LA FECHA EN QUE SE VALIDA ESTA CANCELACION DE CITA Y LA CITA QUE SE HA GENERADO
		 * ANTERIORMENTE
		 */
		var diferenciaEnHoras = Duration.between(ahora, consulta.getFecha()).toHours();
		
		if(diferenciaEnHoras < 24) {
			throw new ValidationException("Las consultas solo puede ser cancelada en un intervalo "
					+ "menor a 24 hrs");
		}
	}

}
