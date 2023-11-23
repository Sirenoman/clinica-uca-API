package com.sv.clinica.uca.clinica_uca.dto;

import com.sv.clinica.uca.clinica_uca.model.Paciente;

public record DatosDetalladoPaciente(
		Long id,
		String nombre,
		String apellido,
		String documentoId,
		String telefono) {
	
	public DatosDetalladoPaciente(Paciente paciente) {
		this(paciente.getId(),  paciente.getNombre(), paciente.getApellido(), paciente.getDocumentoId(),
				 paciente.getTelefono());
	}

}
