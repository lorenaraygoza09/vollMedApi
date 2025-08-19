package med.voll.api.service.validaciones;

import med.voll.api.config.infra.ValidacionException;
import med.voll.api.dto.DtoReservarConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidacionConsultaAnticipada implements IValidarConsultas{

    public void validar(DtoReservarConsulta datos) {
        var fechaConsulta = datos.fecha();
        var ahora = LocalDateTime.now();
        var diferenciaEnMinutos = Duration.between(ahora, fechaConsulta).toMinutes();
        if (diferenciaEnMinutos < 30) {
            throw new ValidacionException("No se pueden agendar consultas con menos de 30 minutos de anticipacion, por favor escoja otro horario");
        }
    }

}
