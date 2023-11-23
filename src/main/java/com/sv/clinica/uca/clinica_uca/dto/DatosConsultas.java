package com.sv.clinica.uca.clinica_uca.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sv.clinica.uca.clinica_uca.model.Consulta;

public record DatosConsultas(
		Long id,
		String nombrePaciente,
		@JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime fecha,
		Especialidad especialidad) {
	
	public DatosConsultas(Consulta consulta) {
		this(consulta.getId(), consulta.getPaciente().getNombre(), consulta.getFecha(), 
				consulta.getMedico().getEspecialidad());
	}

}
