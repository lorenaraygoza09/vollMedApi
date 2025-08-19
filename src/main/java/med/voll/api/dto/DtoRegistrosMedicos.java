package med.voll.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.model.Especialidad;

public record DtoRegistrosMedicos(
        //validacion, notblank incluye not null
        @NotBlank String nombre,
        //@email, especifica que debe tener formato de email
        @NotBlank @Email String email,
        @NotBlank String telefono,
        //@Pattern, patron del documentom entre llaves esta el rango
        //de caracteres que debe de tener
        @NotBlank @Pattern(regexp = "\\d{6,9}") String documento,
        //not blank no funciona con enums
        @NotNull Especialidad especialidad,
        //@valid pq es una entidad embebida
        @NotNull @Valid DtoDireccion direccion
) {
}
