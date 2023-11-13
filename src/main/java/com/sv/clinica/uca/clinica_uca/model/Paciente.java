package com.sv.clinica.uca.clinica_uca.model;

import com.sv.clinica.uca.clinica_uca.dto.DatosActualizarPaciente;
import com.sv.clinica.uca.clinica_uca.dto.DatosRegistroPaciente;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Paciente")
@Table(name = "pacientes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Paciente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String apellido;
	private String documentoId;
	private String telefono;
	private Boolean activo;
	
	public Paciente(DatosRegistroPaciente paciente) {
		this.activo = true;
		this.nombre = paciente.nombre();
		this.apellido = paciente.apellido();
		this.documentoId = paciente.documentoId();
		this.telefono = paciente.telefono();
	}
	
	public void actualizarInfo(@Valid DatosActualizarPaciente paciente) {
		if(paciente.nombre() != null) {
			this.nombre = paciente.nombre();
		}
		if(paciente.apellido() != null) {
			this.apellido = paciente.apellido();
		}
		if(paciente.telefono() != null) {
			this.telefono = paciente.telefono();
		}
	}
	
	public void inactivar() {
		this.activo = false;
	}
}
