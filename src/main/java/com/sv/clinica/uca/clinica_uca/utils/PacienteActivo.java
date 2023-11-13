package com.sv.clinica.uca.clinica_uca.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sv.clinica.uca.clinica_uca.dto.DatosAgendarConsulta;
import com.sv.clinica.uca.clinica_uca.repositories.PacienteRepository;

import jakarta.validation.ValidationException;

@Component
public class PacienteActivo implements ValidadorDeConsulta{

	@Autowired
	private PacienteRepository pacienteRepo;
	
	@Override
	public void validar(DatosAgendarConsulta datos) {
		// VALIDAMOS SI RECIBIMOS UN ID DE PACIENTE
		if(datos.idPaciente() == null) {
			return;
		}
		/**
		 * SE VALIDA SI EL PACIENTE SE ENCUENTRA ACTIVO EN LA BDD CON SU ID
		 * CON UNA NEGACION SE VERIFICA SI SE ENCUENTRA INACTIVO, EN DADO CASO DE SER TRUE
		 * SE LANZA UNA EXCEPCION.
		 */
		var pacienteActivo = pacienteRepo.findActivoById(datos.idPaciente());
		if(!pacienteActivo) {
			throw new ValidationException("No se puede agendar con este paciente");
		}
	}

}
