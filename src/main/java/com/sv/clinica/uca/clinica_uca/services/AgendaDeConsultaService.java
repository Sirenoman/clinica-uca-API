package com.sv.clinica.uca.clinica_uca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sv.clinica.uca.clinica_uca.dto.DatosAgendarConsulta;
import com.sv.clinica.uca.clinica_uca.dto.DatosCancelamientoConsulta;
import com.sv.clinica.uca.clinica_uca.dto.DatosDetalleConsulta;
import com.sv.clinica.uca.clinica_uca.model.Consulta;
import com.sv.clinica.uca.clinica_uca.model.Medico;
import com.sv.clinica.uca.clinica_uca.repositories.ConsultaRepository;
import com.sv.clinica.uca.clinica_uca.repositories.MedicoRepository;
import com.sv.clinica.uca.clinica_uca.repositories.PacienteRepository;
import com.sv.clinica.uca.clinica_uca.utils.ValidacionDeIntegridad;
import com.sv.clinica.uca.clinica_uca.utils.ValidadorCancelamientoConsulta;
import com.sv.clinica.uca.clinica_uca.utils.ValidadorDeConsulta;

@Service
public class AgendaDeConsultaService {
	
	// REPOSITORIOS A UTILIZARA PARA VALIDAR INFORMACION
	
	@Autowired
	private PacienteRepository pacienteRepo;
	
	@Autowired
	private MedicoRepository medicoRepo;
	
	@Autowired
	private ConsultaRepository consultaRepo;
	
	/**
	 * INSTANCIA DE VARIABLES "VALIDADORES" A UTILIZAR PARA 
	 * EL FLUJO DE NEGOCIO: VALIDACIONES
	 * - Validadores: INTERFAZ QUE IMPLEMENTA EL METODO "VALIDAR"
	 * - ValidadoresCancelamiento: INTERFAZ QUE IMPLEMENTA EL METODO "VALIDAR CANCELAMIENTO"
	 */
	@Autowired
	List<ValidadorDeConsulta> validadores;
	
	@Autowired
	List<ValidadorCancelamientoConsulta> validadoresCancelamiento;
	
	/**
	 * - METODOS PARA REALIZAR EL AGENDANDO DE LA CONSULTA
	 */
	public DatosDetalleConsulta agendar(DatosAgendarConsulta datos) {
		
		// Primero validamos si el paciente se encuentra o no dentro de la BDD
		
		if(!pacienteRepo.findById(datos.idPaciente()).isPresent()) {
			throw new ValidacionDeIntegridad("Este id de paciente no fue encontrado");
		}
		
		/**
		 *  SABIENDO QUE EL MEDICO SE PUEDE SELECCIONAR O ASIGNAR AUTOMATICAMENTE
		 *  SE VALIDA SI EL ID PROPORCIONADO SE ENCUENTRA EN LA BDD
		 */
		if(datos.idMedico() != null && !medicoRepo.existsById(datos.idMedico())) {
			throw new ValidacionDeIntegridad("Este id de medico no fue encontrado");
		}
		
		// VALIDACIONES
		validadores.forEach(v -> v.validar(datos));
		
		// DEFINIR VARIABLES PARA GUARDAR PACIENTE Y MEDICO PARA ASIGNAR
		var paciente = pacienteRepo.findById(datos.idPaciente()).get();
		var medico = seleccionarMedico(datos);
		
		if(medico == null) {
			throw new ValidacionDeIntegridad("No se encuentra Medico disponible para este horario y especialidad");
		}
		
		var consulta = new Consulta(medico, paciente, datos.fecha());
		consultaRepo.save(consulta);
		
		return new DatosDetalleConsulta(consulta);
	}
	
	// METODO PARA CANCELAR CONSULTA
	public void cancelar(DatosCancelamientoConsulta datos) {
		// VERIFICA SI EXISTE LA CONSULTA A CANCELAR
		if(!consultaRepo.existsById(datos.idConsulta())) {
			throw new ValidacionDeIntegridad("No existe tal consulta agendada");
		}
		
		// VALIDACIONES
		validadoresCancelamiento.forEach(v -> v.validar(datos));
		
		// OBTIENE EL OBJETO CONSULTA CON SU ID
		var consulta = consultaRepo.getReferenceById(datos.idConsulta());
		// AGREGA MOTIVO DE CANCELACION
		consulta.cancelar(datos.motivo());
	}
	
	/** 
	 * METODO PARA AUTOASIGNAR MEDICO A CONSULTA DE ACUERDO A 
	 * - ESPECIALIDAD
	 * - DISPONIBILIDAD DEL MEDICO
	 */
	private Medico seleccionarMedico(DatosAgendarConsulta datos) {
		// VERIFICA ID DE MEDICO
		if(datos.idMedico() != null) {
			return medicoRepo.getReferenceById(datos.idMedico());
		}
		// VERIFICA SI HA SELECCIONADO ESPECIALIDAD
		if(datos.especialidad() == null) {
			throw new ValidacionDeIntegridad("Debe seleecionar una ESPECIALIDAD del medico");
		}
		// VERIFICA SI EL MEDICO CON LA ESPECIALIDAD TIENE FECHA DISPONIBLE
		return medicoRepo.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(), datos.fecha());
	}

}
