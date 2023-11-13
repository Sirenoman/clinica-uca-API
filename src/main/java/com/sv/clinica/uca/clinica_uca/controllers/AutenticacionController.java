package com.sv.clinica.uca.clinica_uca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sv.clinica.uca.clinica_uca.dto.DatosAutenticacionUsuario;
import com.sv.clinica.uca.clinica_uca.dto.DatosJWTtoken;
import com.sv.clinica.uca.clinica_uca.model.Usuario;
import com.sv.clinica.uca.clinica_uca.services.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

	@Autowired
	private AuthenticationManager manager;
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosUsuario){
		//System.out.println("Carnet: "+datosUsuario.carnet()+" Clave: "+datosUsuario.clave());
		var authToken = new UsernamePasswordAuthenticationToken(datosUsuario.carnet(), datosUsuario.clave());
		var usuarioAutenticado = manager.authenticate(authToken);
		var JwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
		
		return new ResponseEntity<>(new DatosJWTtoken(JwtToken), HttpStatus.OK);
	}
}
