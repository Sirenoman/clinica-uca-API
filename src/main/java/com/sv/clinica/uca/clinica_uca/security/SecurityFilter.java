package com.sv.clinica.uca.clinica_uca.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sv.clinica.uca.clinica_uca.repositories.UsuarioRepository;
import com.sv.clinica.uca.clinica_uca.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter  extends  OncePerRequestFilter{
	
	/***
	 * Instancia para los servicios del uso de Token 
	 * Agregando tambien la Instrancia del Rpeositorio de usuarios de la base de datos. 
	 */
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Obtener el TOKEN del Header
		var authHeader = request.getHeader("Authorization");
		if(authHeader != null) {
			// Eliminamos la palabra de bearer para que solo se envie el token
			var token = authHeader.replace("Bearer ", "");
			var nombreUsuario = tokenService.getSubject(token);
			if(nombreUsuario != null) {
				// TOKEN VALIDO
				var usuario = usuarioRepository.findByCarnet(nombreUsuario);
				var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
						usuario.getAuthorities()); // Forzando el inicio de sesion
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);
	}

}
