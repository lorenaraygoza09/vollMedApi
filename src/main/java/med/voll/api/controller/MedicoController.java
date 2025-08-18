package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.DtoActualizarMedicos;
import med.voll.api.dto.DtoDetallesMedico;
import med.voll.api.dto.DtoListaMedicos;
import med.voll.api.dto.DtoRegistrosMedicos;
import med.voll.api.model.Medico;
import med.voll.api.repository.IMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private IMedicoRepository repositoryMedico;

    @Transactional
    @PostMapping
    public ResponseEntity registrarMedico(@RequestBody @Valid DtoRegistrosMedicos datosRegistrosMedicos, UriComponentsBuilder uriComponentsBuilder){
        var medico = new Medico(datosRegistrosMedicos);
        repositoryMedico.save(medico);

        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(new DtoDetallesMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DtoListaMedicos>> listarMedicos(@PageableDefault(size=5, sort = {"nombre"}) Pageable paginacion){
        var page = repositoryMedico.findAllByActivoTrue(paginacion)
                .map(DtoListaMedicos::new);
        return ResponseEntity.ok(page);
    }

    @Transactional
    @PutMapping
    public ResponseEntity actualizarMedicos(@RequestBody @Valid DtoActualizarMedicos datosActualizarMedico){
        var medico = repositoryMedico.getReferenceById(datosActualizarMedico.id());
        medico.actualizarInformacion(datosActualizarMedico);
        return ResponseEntity.ok(new DtoDetallesMedico(medico));
    }

    @Transactional
    @DeleteMapping("/{id}")
    //pathvariable indica que la variable id es la que se encunetra
    //mapeada y es un elemento dinamico
    public ResponseEntity eliminar(@PathVariable Long id){
        //eliminacion fisica, elimina por completo de la bd
        // repositoryMedico.deleteById(id);
        //eliminacion logica, desactiva la informacion
        //pero no la elimina para evitar errores de negocio
        var medico = repositoryMedico.getReferenceById(id);
        medico.eliminar();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity retornaDtoMedicos(@PathVariable Long id){
        var medico = repositoryMedico.getReferenceById(id);

        return ResponseEntity.ok(new DtoDetallesMedico(medico));
    }
}

