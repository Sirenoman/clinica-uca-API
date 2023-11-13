package com.sv.clinica.uca.clinica_uca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.sv.clinica.uca.clinica_uca.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	UserDetails findByCarnet(String Carnet);

}
