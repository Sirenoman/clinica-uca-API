package com.sv.clinica.uca.clinica_uca.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sv.clinica.uca.clinica_uca.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{
	
	Page<Paciente> findByActivoTrue(Pageable paginacion);
	
	@Query("""
			select p.activo from Paciente p
			where p.id=:idPaciente
			""")
	Boolean findActivoById(Long idPaciente);

}
