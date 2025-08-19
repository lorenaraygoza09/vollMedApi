package med.voll.api.service.validaciones;

import med.voll.api.config.infra.ValidacionException;
import med.voll.api.dto.DtoReservarConsulta;
import med.voll.api.repository.IConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionMultilplesConsultasMismoDia implements IValidarConsultas{
    @Autowired
    private IConsultaRepository repository;

    public void validar(DtoReservarConsulta datos){
        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorarrio = datos.fecha().withHour(18);
        var pacienteTieneOtraConsulta = repository.existsByPacienteIdAndFechaBetween(datos.idPaciente(), primerHorario, ultimoHorarrio);
        if (pacienteTieneOtraConsulta){
            throw new ValidacionException("No se pueden agendar más de 1 consulta para el mismo paciente el mismo día");
        }
    }
}
