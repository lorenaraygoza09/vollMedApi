package med.voll.api.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dto.DtoActualizarMedicos;
import med.voll.api.dto.DtoRegistrosMedicos;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

@Table(name = "medicos")
@Entity(name = "Medico")
public class Medico{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean activo;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    //indica que los datos deben añadirse a la tabla medicos
    //y no crear una tabla aparte para direccion
    //añadir @Embedabble en la clase Direccion
    private Direccion direccion;

    public Medico(DtoRegistrosMedicos datos) {
        this.id = null;
        this.activo = true;
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.telefono = datos.telefono();
        this.documento = datos.documento();
        this.especialidad = datos.especialidad();
        this.direccion = new Direccion(datos.direccion());
    }

    public void actualizarInformacion(@Valid DtoActualizarMedicos datos) {
        if (datos.nombre() != null){
            this.nombre = datos.nombre();
        }
        if (datos.telefono() != null){
            this.telefono = datos.telefono();
        }
        if (datos.direccion() != null){
            this.direccion.actualizarDireccion(datos.direccion());
        }

    }

    public void eliminar() {
        this.activo = false;
    }
}
