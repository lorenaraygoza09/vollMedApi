package med.voll.api.service.validaciones;

import med.voll.api.config.infra.ValidacionException;
import med.voll.api.dto.DtoReservarConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidacionFueraDeHorario implements IValidarConsultas{

    public void validar(DtoReservarConsulta datos){
        var fechaConsulta = datos.fecha();

        var domingo = fechaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioAntesApertura = fechaConsulta.getHour() < 7;
        var horarioDespuesCierre = fechaConsulta.getHour() >18;
        if (domingo || horarioDespuesCierre || horarioAntesApertura){
            throw new ValidacionException("Horario seleccionado fuera de horario laboral");
        }

    }
}
