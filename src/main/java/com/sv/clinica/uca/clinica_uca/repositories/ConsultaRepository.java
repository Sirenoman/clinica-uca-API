package com.sv.clinica.uca.clinica_uca.repositories;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sv.clinica.uca.clinica_uca.model.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
	
	Boolean existsByPacienteIdAndFechaBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);
	
	Boolean existsByMedicoIdAndFecha(Long idMedico, LocalDateTime fecha);
	
	// QUERY Personalizada para buscar todas las consutlas por IdPaciente
	@Query("""
			select * from consultas
			where paciente_id:idPaciente
			order by fecha desc;
			""")
	Page<Consulta> findAllByPacienteIdAndOrdenByFecha(Long idPaciente, Pageable paginacion);
	
	// QUERY PERSONALIZADA PARA BUSCAR CITA EN ESPECIFICO POR ID PACIENTE
	//Consulta getReferenceByPacienteId(Long idPaciente);

}
