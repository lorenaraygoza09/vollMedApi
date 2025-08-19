package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.dto.DtoCancelarConsulta;
import med.voll.api.dto.DtoDetallesConsulta;
import med.voll.api.dto.DtoReservarConsulta;
import med.voll.api.service.AgendarConsultasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendarConsultasService agendarConsulta;

    @PostMapping
    @Transactional
    public ResponseEntity agendarConsulta(@RequestBody @Valid DtoReservarConsulta datos){
        var detallesConsulta = agendarConsulta.reservar(datos);

        return ResponseEntity.ok(detallesConsulta);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelarConsulta(@RequestBody @Valid DtoCancelarConsulta datos){
        agendarConsulta.cancelar(datos);
        return ResponseEntity.noContent().build();
    }
}
