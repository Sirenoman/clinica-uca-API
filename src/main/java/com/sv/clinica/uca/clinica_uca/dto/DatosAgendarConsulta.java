package com.sv.clinica.uca.clinica_uca.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record DatosAgendarConsulta(
		Long id,
		@NotNull @JsonAlias({"id_paciente", "idPaciente"}) Long idPaciente,
		@JsonAlias({"id_medico", "idMedico"}) Long idMedico,
		@NotNull @Future LocalDateTime fecha,
		Especialidad especialidad) {

}
