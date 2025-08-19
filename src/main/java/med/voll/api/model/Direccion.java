package med.voll.api.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dto.DtoDireccion;

@Getter
@NoArgsConstructor
@AllArgsConstructor

@Embeddable //indica que es una tabla embebida
public class Direccion {
    private String calle;
    private String numero;
    private String complemento;
    private String colonia;
    private String ciudad;
    private String codigoPostal;
    private String estado;

    public Direccion(DtoDireccion dtoDireccion) {
        this.calle = dtoDireccion.calle();
        this.numero = dtoDireccion.numero();
        this.complemento = dtoDireccion.complemento();
        this.colonia = dtoDireccion.colonia();
        this.ciudad = dtoDireccion.ciudad();
        this.codigoPostal = dtoDireccion.codigoPostal();
        this.estado = dtoDireccion.estado();
    }

    public Direccion(Direccion direccion) {
    }

    public void actualizarDireccion(DtoDireccion datos) {
        if (datos.calle() != null){
            this.calle = datos.calle();
        }
        if (datos.numero() != null){
            this.numero = datos.numero();
        }
        if (datos.complemento() != null){
            this.complemento = datos.complemento();
        }
        if (datos.colonia() != null){
            this.colonia = datos.colonia();
        }
        if (datos.ciudad() != null){
            this.ciudad = datos.ciudad();
        }
        if (datos.codigoPostal() != null){
            this.codigoPostal = datos.codigoPostal();
        }
        if (datos.estado() != null){
            this.estado = datos.estado();
        }
    }
}
