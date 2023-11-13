package com.sv.clinica.uca.clinica_uca.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sv.clinica.uca.clinica_uca.model.Consulta;

public record DatosDetalleConsulta(
		Long id,
		String nombrePaciente,
		Long idMedico,
		String nombreMedico,
		@JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime fecha) {
	
	public DatosDetalleConsulta(Consulta consulta) {
		this(consulta.getId(), consulta.getPaciente().getNombre(), consulta.getMedico().getId(),
				 consulta.getMedico().getNombre(), consulta.getFecha());
	}

}
