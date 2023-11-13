package com.sv.clinica.uca.clinica_uca.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sv.clinica.uca.clinica_uca.dto.DatosAgendarConsulta;
import com.sv.clinica.uca.clinica_uca.repositories.ConsultaRepository;

import jakarta.validation.ValidationException;

@Component
public class MedicoConConsulta implements ValidadorDeConsulta{
	
	@Autowired
	private ConsultaRepository consultaRepo;

	@Override
	public void validar(DatosAgendarConsulta datos) {
		// VALIDAMOS SI SE RECIBE UN MEDICO
		if(datos.idMedico() == null) {
			return;
		}
		/**
		 * SE VERIFICA SI EL MEDICO YA SE ENCUENTRA CON CITA AGENDADA EN TAL FECHA
		 * SI SE ENCUENTRA YA CON CITA AGENDADA, SE LANZARA UNA EXCEPCION.
		 */
		var medicoConConsulta = consultaRepo.existsByMedicoIdAndFecha(datos.idMedico(), datos.fecha());
		if(medicoConConsulta) {
			throw new ValidationException("Este medico ya tiene una consulta en este horario");
		}
	}

}
