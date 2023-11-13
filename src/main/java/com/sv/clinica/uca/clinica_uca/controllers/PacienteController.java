package com.sv.clinica.uca.clinica_uca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sv.clinica.uca.clinica_uca.dto.DatosActualizarPaciente;
import com.sv.clinica.uca.clinica_uca.dto.DatosDetalladoPaciente;
import com.sv.clinica.uca.clinica_uca.dto.DatosRegistroPaciente;
import com.sv.clinica.uca.clinica_uca.dto.MessageDTO;
import com.sv.clinica.uca.clinica_uca.model.Paciente;
import com.sv.clinica.uca.clinica_uca.repositories.PacienteRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	// REGISTRAR PACIENTE
	@PostMapping
	@Transactional
	public ResponseEntity<?> registrar(@RequestBody @Valid DatosRegistroPaciente datos,
			UriComponentsBuilder uriBuilder){
		Paciente paciente = pacienteRepository.save(new Paciente(datos));
		// Se instancia y crea la expancion del enlace URI para identificar el paciente creado
		var uri = uriBuilder.path("/paciente/{id}").buildAndExpand(paciente.getId()).toUri();
		return ResponseEntity.created(uri).body(new DatosDetalladoPaciente(paciente));
	}
	
	// LISTAR PACIENTES EN BDD
	@GetMapping
	public ResponseEntity<Page<DatosDetalladoPaciente>> listar(@PageableDefault(page=0, size=10, sort={"nombre"}) 
			Pageable paginacion){
		return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion).map(DatosDetalladoPaciente::new));
	}
	
	// ACTUALIZAR INFORMACION DE UN PACIENTE
	@PutMapping
	@Transactional
	public ResponseEntity<?> actualizar(@RequestBody @Valid DatosActualizarPaciente datos){
		Paciente paciente = pacienteRepository.getReferenceById(datos.id());
		paciente.actualizarInfo(datos);
		
		return new ResponseEntity<>(new DatosDetalladoPaciente(paciente), HttpStatus.OK);
	}
	
	// ELIMINADO LOGICO
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> removerPaciente(@PathVariable Long id){
		Paciente paciente = pacienteRepository.getReferenceById(id);
		paciente.inactivar();
		
		return new ResponseEntity<>(new MessageDTO("Se ha removido el paciente"), HttpStatus.OK);
	}
	
	// DETALLAR DATOS DE UN PACIENTE
	@GetMapping("/{id}")
	public ResponseEntity<?> detallarPaciente(@PathVariable Long id){
		Paciente paciente = pacienteRepository.getReferenceById(id);
		
		return new ResponseEntity<>(new DatosDetalladoPaciente(paciente), HttpStatus.OK);
	}
}
