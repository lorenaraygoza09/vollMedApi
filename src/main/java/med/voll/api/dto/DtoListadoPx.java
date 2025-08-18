package med.voll.api.dto;

import med.voll.api.model.Paciente;

public record DtoListadoPx(Long id, String nombre, String email, String documento) {

    public DtoListadoPx(Paciente paciente) {
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getDocumento());
    }
}
