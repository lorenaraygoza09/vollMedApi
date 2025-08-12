package med.voll.api.dto;

import jakarta.validation.constraints.NotNull;

public record DtoActualizarMedicos(
        @NotNull Long id,
        String nombre,
        String telefono,
        DtoDireccion direccion
) {
}
