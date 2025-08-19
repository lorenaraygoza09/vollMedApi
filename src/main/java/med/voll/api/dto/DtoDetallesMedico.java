package med.voll.api.dto;

import med.voll.api.model.Direccion;
import med.voll.api.model.Especialidad;
import med.voll.api.model.Medico;

public record DtoDetallesMedico(
    Long id,
    String nombre,
    String email,
    String documento,
    String telefono,
    Especialidad especialidad,
    Direccion direccion
) {
    public DtoDetallesMedico(Medico medico) {
        this(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getDocumento(),
                medico.getTelefono(),
                medico.getEspecialidad(),
                medico.getDireccion()
        );
    }
}
