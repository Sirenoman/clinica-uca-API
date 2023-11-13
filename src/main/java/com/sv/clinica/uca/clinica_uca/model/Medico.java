package com.sv.clinica.uca.clinica_uca.model;

import com.sv.clinica.uca.clinica_uca.dto.DatosActualizarMedico;
import com.sv.clinica.uca.clinica_uca.dto.DatosRegistroMedico;
import com.sv.clinica.uca.clinica_uca.dto.Especialidad;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Medico")
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	private String email;
	private String telefono;
	private String documento;
	private Boolean activo;
	@Enumerated(EnumType.STRING)
	private Especialidad especialidad;
	
	public Medico(DatosRegistroMedico medico) {
		this.activo = true;
		this.nombre = medico.nombre();
		this.email = medico.email();
		this.telefono = medico.telefono();
		this.documento = medico.documento();
		this.especialidad = medico.especialidad();
	}
	
	public void actualizarDatos(DatosActualizarMedico medico) {
		if(medico.nombre() != null) {
			this.nombre = medico.nombre();
		}
		if(medico.email() != null) {
			this.email = medico.email();
		}
		if(medico.telefono() != null) {
			this.telefono = medico.telefono();
		}
		if(medico.documento() != null) {
			this.documento = medico.documento(); 
		}
	}
	
	public void desactivarMedico() {
		this.activo = false;
	}

}
