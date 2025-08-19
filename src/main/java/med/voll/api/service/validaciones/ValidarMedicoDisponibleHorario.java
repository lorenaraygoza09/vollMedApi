package med.voll.api.service.validaciones;

import med.voll.api.config.infra.ValidacionException;
import med.voll.api.dto.DtoReservarConsulta;
import med.voll.api.repository.IConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarMedicoDisponibleHorario implements IValidarConsultas{
    @Autowired
    private IConsultaRepository repository;

    public void validar(DtoReservarConsulta datos){
        var medicoEnConsulta = repository.existsByMedicoIdAndFecha(datos.idMedico(), datos.fecha());
        if (medicoEnConsulta){
            throw new ValidacionException("El médico seleccionado ya tiene una consulta agendada en el horario seleccionado, por favor seleccione otro horario u otro médico");
        }
    }
}
