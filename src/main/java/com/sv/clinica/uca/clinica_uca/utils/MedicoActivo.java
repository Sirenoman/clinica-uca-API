package com.sv.clinica.uca.clinica_uca.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sv.clinica.uca.clinica_uca.dto.DatosAgendarConsulta;
import com.sv.clinica.uca.clinica_uca.repositories.MedicoRepository;

import jakarta.validation.ValidationException;

@Component
public class MedicoActivo implements ValidadorDeConsulta{
	
	@Autowired
	private MedicoRepository medicoRepo;

	@Override
	public void validar(DatosAgendarConsulta datos) {
		// VALIDAMOS SI SE RECIBE UN MEDICO
		if(datos.idMedico() == null) {
			return;
		}
		/**
		 * SE VERIFICA MEDIANTE UNA QUERY PERSONALIZADA SI EL MEDICO SE ENCUENTRA ACTIVO CON SU ID
		 * DESPUES SE VALIDA CON UNA NEGACION SI SE ENCUENTRA INACTIVO Y SE LANZA UNA EXCEPCION
		 */
		var medicoActivo = medicoRepo.findActivoByid(datos.idMedico());
		if(!medicoActivo) {
			throw new ValidationException("No se puede agendar con tal medico");
		}
	}
	

}
