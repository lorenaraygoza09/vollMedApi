package med.voll.api.dto;

import med.voll.api.model.Consulta;

import java.time.LocalDateTime;

public record DtoDetallesConsulta(Long id, Long idMedico, Long idPaciente, LocalDateTime fecha) {
    public DtoDetallesConsulta(Consulta consulta) {
        this(
                consulta.getId(),
                consulta.getMedico().getId(),
                consulta.getPaciente().getId(),
                consulta.getFecha()
        );
    }
}
