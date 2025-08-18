package med.voll.api.dto;

import java.time.LocalDateTime;

public record DtoDetallesConsulta(Long id, Long idMedico, Long idPaciente, LocalDateTime fecha) {
}
