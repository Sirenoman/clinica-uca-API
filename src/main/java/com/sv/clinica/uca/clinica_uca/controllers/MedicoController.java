package com.sv.clinica.uca.clinica_uca.controllers;

import java.net.URI;

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

import com.sv.clinica.uca.clinica_uca.dto.DatosActualizarMedico;
import com.sv.clinica.uca.clinica_uca.dto.DatosDetalladoMedico;
import com.sv.clinica.uca.clinica_uca.dto.DatosListadoMedico;
import com.sv.clinica.uca.clinica_uca.dto.DatosRegistroMedico;
import com.sv.clinica.uca.clinica_uca.dto.MessageDTO;
import com.sv.clinica.uca.clinica_uca.model.Medico;
import com.sv.clinica.uca.clinica_uca.repositories.MedicoRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@PostMapping
	public ResponseEntity<?> registrarMedico(@RequestBody @Valid DatosRegistroMedico registroMedico,
				UriComponentsBuilder uriComponentsBuilder){
		Medico medico = medicoRepository.save(new Medico(registroMedico));
		URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		
		return ResponseEntity.created(url).body(medico);
	}
	
	@GetMapping
	public ResponseEntity<Page<DatosListadoMedico>> listadoMedico(@PageableDefault(size = 2) Pageable paginacion){
		// mediante una Query Personalizada realizamos la paginacion de todos los medicos ACTIVOS.
		return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<?> actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosMedico) {
		Medico medico = medicoRepository.getReferenceById(datosMedico.id());
		medico.actualizarDatos(datosMedico);
		// LA ETIQUETA TRANSACTIONAL AYUDA A APLICAR EL METODO DE PUT EN LA BASE DE DATOS 
		// YA QUE REALIZA UN ROLLBACK AL MOMENTO DE TERMINAR EL METODO DE ACTUALIZACION
		return new ResponseEntity<>(new DatosDetalladoMedico(medico), HttpStatus.OK);
	}
	
	// DELETE LOGICO
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> eliminarMedico(@PathVariable Long id) {
		Medico medico = medicoRepository.getReferenceById(id);
		medico.desactivarMedico();
		return new ResponseEntity<>(new MessageDTO("Medico eliminado"), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> retornarMedico(@PathVariable Long id){
		Medico medico = medicoRepository.getReferenceById(id);
		return new ResponseEntity<>(new DatosDetalladoMedico(medico), HttpStatus.OK);
	}

}
