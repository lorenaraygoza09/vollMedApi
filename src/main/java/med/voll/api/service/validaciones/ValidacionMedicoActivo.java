package med.voll.api.service.validaciones;

import med.voll.api.config.infra.ValidacionException;
import med.voll.api.dto.DtoReservarConsulta;
import med.voll.api.repository.IMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionMedicoActivo implements IValidarConsultas{

    @Autowired
    private IMedicoRepository repository;

    public void validar(DtoReservarConsulta datos){
        //eleccion del medico opcional
        if (datos.idMedico() == null){
            return;
        }
        var medicoActivo = repository.findActivoById(datos.idMedico());
        if (!medicoActivo){
            throw new ValidacionException("La consulta no se puede agendar con médicos inactivos");
        }
    }
}
