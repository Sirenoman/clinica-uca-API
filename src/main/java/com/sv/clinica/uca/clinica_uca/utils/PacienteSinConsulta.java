package com.sv.clinica.uca.clinica_uca.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sv.clinica.uca.clinica_uca.dto.DatosAgendarConsulta;
import com.sv.clinica.uca.clinica_uca.repositories.ConsultaRepository;

import jakarta.validation.ValidationException;

@Component
public class PacienteSinConsulta implements ValidadorDeConsulta{

	@Autowired
	private ConsultaRepository consultaRepo;
	
	@Override
	public void validar(DatosAgendarConsulta datos) {
		// SE CREAN VARIABLES PARA VALIDAR FECHA DENTRO DE HORARIO LABORAL
		var primerHorario = datos.fecha().withHour(7);
		var ultimoHorario = datos.fecha().withHour(18);
		
		/**
		 * MEDIANTE UNA QUERY PERSONALIZADA SE VERIFICA QUE EL PACIENTE SE ENCUENTRA CON CITA 
		 * YA GENERADA EN TAL FECHA CON HORARIO DE INICIO A FIN SEGUN HORARIO LABORAL
		 * AL SER VERDADERO QUE SE ENCUENTRA CON CONSULTA LANZARA UNA EXCEPCION.
		 */
		var pacienteConConsulta = consultaRepo.existsByPacienteIdAndFechaBetween(datos.idPaciente(), 
				primerHorario, ultimoHorario);
		if(pacienteConConsulta) {
			throw new ValidationException("El paciente ya se encuentra con cita para esta fecha");
		}
	}

}
