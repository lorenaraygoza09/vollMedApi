package med.voll.api.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.model.MotivoCancelacion;

public record DtoCancelarConsulta(
        @NotNull
        Long idConsulta,
        @NotNull
        MotivoCancelacion motivo
) {
}
