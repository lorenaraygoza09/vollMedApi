package med.voll.api.service.validaciones;

import med.voll.api.config.infra.ValidacionException;
import med.voll.api.dto.DtoReservarConsulta;
import med.voll.api.repository.IPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarPacienteActivo implements IValidarConsultas {

    @Autowired
    private IPacienteRepository repository;
    public void validar(DtoReservarConsulta datos) {
var pacienteActivo = repository.findActivoById(datos.idPaciente());
if (!pacienteActivo){
    throw new ValidacionException("La consulta no puede agendarse sin los datos del paciente");
}
    }
}
