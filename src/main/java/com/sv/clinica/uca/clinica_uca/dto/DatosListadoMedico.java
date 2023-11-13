package com.sv.clinica.uca.clinica_uca.dto;

import com.sv.clinica.uca.clinica_uca.model.Medico;

public record DatosListadoMedico(
		Long id,
		String nombre,
		String documento,
		String email,
		String especialidad) {
	
	public DatosListadoMedico(Medico medico) {
		this(medico.getId(), medico.getNombre(), medico.getDocumento(), 
				medico.getEmail(), medico.getEspecialidad().toString());
	}

}
