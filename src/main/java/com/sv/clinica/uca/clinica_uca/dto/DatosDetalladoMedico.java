package com.sv.clinica.uca.clinica_uca.dto;

import com.sv.clinica.uca.clinica_uca.model.Medico;

public record DatosDetalladoMedico(Long id, String nombre, String documento, String especialidad) {
	
	public DatosDetalladoMedico(Medico medico) {
		this(medico.getId(), medico.getNombre(), medico.getDocumento(), 
				medico.getEspecialidad().toString());
	}

}
