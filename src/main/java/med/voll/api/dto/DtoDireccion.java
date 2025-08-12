package med.voll.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DtoDireccion(
        @NotBlank String calle,
        @NotBlank String numero,
        String complemento,
        @NotBlank String colonia,
        @NotBlank String ciudad,
        @NotBlank @Pattern(regexp = "\\d{5}") String codigoPostal,
        @NotBlank String estado
) {
}
