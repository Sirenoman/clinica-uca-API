package com.sv.clinica.uca.clinica_uca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sv.clinica.uca.clinica_uca.dto.DatosAgendarConsulta;
import com.sv.clinica.uca.clinica_uca.dto.DatosCancelamientoConsulta;
import com.sv.clinica.uca.clinica_uca.dto.MessageDTO;
import com.sv.clinica.uca.clinica_uca.services.AgendaDeConsultaService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

	@Autowired
	private AgendaDeConsultaService agendaConsultaService;
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> agendar(@RequestBody @Valid DatosAgendarConsulta datos){
		// USO DE SERVICIO E INSTANCIA DE RESPUESTA DEL FLUJO DE NEGOCIO
		var response = agendaConsultaService.agendar(datos);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	// CANCELADO LOGICO
	
	@DeleteMapping
	@Transactional
	public ResponseEntity<?> cancelar(@RequestBody @Valid DatosCancelamientoConsulta datos){
		// USO DE SERVICIO E INSTANCIA DE RESPUESTA DE CANCELAMIENTO DE CONSULTA DEL FLUJO DE NEGOCIO
		agendaConsultaService.cancelar(datos);
		
		return new ResponseEntity<>(new MessageDTO("Consulta cancelada"), HttpStatus.OK);
	}
}
