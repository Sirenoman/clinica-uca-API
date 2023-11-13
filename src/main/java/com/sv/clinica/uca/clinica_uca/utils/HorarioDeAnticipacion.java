package com.sv.clinica.uca.clinica_uca.utils;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.sv.clinica.uca.clinica_uca.dto.DatosAgendarConsulta;

import jakarta.validation.ValidationException;

@Component
public class HorarioDeAnticipacion implements ValidadorDeConsulta{

	@Override
	public void validar(DatosAgendarConsulta datos) {
		var ahora = LocalDateTime.now();
		var horaConsulta = datos.fecha();
		
		// SE CORROBORA LA DIFERENCIA DE MAS DE 30 MIN ENTRE LA FECHA DE AGENDADA LA CONSULTA Y LA FECHA ACTUAL
		var diferencia30Min = Duration.between(ahora, horaConsulta).toMinutes()<30;
		
		// SI LA DIFERENCIA ES MENOR A 30 MIN SE LANZA EXCEPCION 
		if(diferencia30Min) {
			throw new ValidationException("La consulta debe programarse con al menos 30 min de anticipacion");
		}
		
		
	}

}
