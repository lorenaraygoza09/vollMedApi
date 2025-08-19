package med.voll.api.service;

import med.voll.api.config.infra.ValidacionException;
import med.voll.api.dto.DtoCancelarConsulta;
import med.voll.api.dto.DtoDetallesConsulta;
import med.voll.api.dto.DtoReservarConsulta;
import med.voll.api.model.Consulta;
import med.voll.api.model.Medico;
import med.voll.api.repository.IConsultaRepository;
import med.voll.api.repository.IMedicoRepository;
import med.voll.api.repository.IPacienteRepository;
import med.voll.api.service.validaciones.IValidarCancelamientoDeConsulta;
import med.voll.api.service.validaciones.IValidarConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendarConsultasService {

    @Autowired
    private IConsultaRepository consultaRepository;
    @Autowired
    private IMedicoRepository medicoRepository;
    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private List<IValidarConsultas> validaciones;

    @Autowired
    private List<IValidarCancelamientoDeConsulta> validarCancelamiento;


    public DtoDetallesConsulta reservar(DtoReservarConsulta datos){
        if (!pacienteRepository.existsById(datos.idPaciente())){
            throw new ValidacionException("No existe un paciente con el ID ingresado en nuestra base de datos");
        }
        if (datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionException("No existe un medico con el ID ingresado en nuestra base de datos");
        }
        //validaciones
        validaciones.forEach(v -> v.validar(datos));


        var medico = asignarMedico(datos) ;
        if (medico == null){
            throw new ValidacionException("No existe un medico disponible en ese horario");
        }
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
       var consulta = new Consulta(null, medico, paciente, datos.fecha(), null);
        consultaRepository.save(consulta);
        return new DtoDetallesConsulta(consulta);
    }

    private Medico asignarMedico(DtoReservarConsulta datos) {
    if (datos.idMedico() != null){
        return medicoRepository.getReferenceById(datos.idMedico());
    } if (datos.especialidad() == null){
        throw new ValidacionException("Por favor ingresa una especialidad para la consulta medica");
        }
    return medicoRepository.medicoAleatorioDisponible(datos.especialidad(), datos.fecha());
    }

    public void cancelar(DtoCancelarConsulta datos) {
        if (!consultaRepository.existsById(datos.idConsulta())) {
            throw new ValidacionException("El Id de la consulta no existe");
        }
        validarCancelamiento.forEach(v -> v.validar(datos));

        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }

}
