package com.sv.clinica.uca.clinica_uca.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sv.clinica.uca.clinica_uca.model.Usuario;

@Service
public class TokenService {
	
	@Value("${clinica_uca.security.secret}")
	private String apiSecret;
	
	/** 
	 * Fucion lista para la generacion de TOKEN tomando en cuenta el 
	 * apiSecret y los datos del usuario que desea ingresar
	 * 
	 * @param usuario
	 * @return
	 */
	public String generarToken(Usuario usuario) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(apiSecret);
			return JWT.create()
					.withIssuer("clinica uca")
					.withSubject(usuario.getUsername())
					.withClaim("id", usuario.getId())
					.withExpiresAt(generarFechaExpiracion())
					.sign(algorithm);
		} catch (JWTCreationException e) {
			throw new RuntimeException("Error al crear el token JWT", e);
		}
	}
	
	/**
	 * Funcion creada para validar token al momento de ingresar sesion dentro de la API
	 * O para realizar otra accion dentro del servicio.
	 * 
	 * @param token
	 * @return
	 */
	public String getSubject(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(apiSecret);
			return JWT.require(algorithm)
					.withIssuer("clinica uca")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			throw new RuntimeException("Token invalido o expirado");
		}
	}
	
	// Funcion privada para la generacion de fecha de expiracion
	private Instant generarFechaExpiracion() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-08:00"));
	}

}
