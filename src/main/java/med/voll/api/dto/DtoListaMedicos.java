package med.voll.api.dto;

import med.voll.api.model.Medico;

public record DtoListaMedicos(
        Long id,
        String nombre,
        String email,
        String documento,
        Especialidad especialidad
) {
    public DtoListaMedicos(Medico medico) {
        this(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getDocumento(),
                medico.getEspecialidad()
        );
    }
}
