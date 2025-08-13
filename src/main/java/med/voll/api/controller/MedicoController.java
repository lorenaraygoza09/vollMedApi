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

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private IMedicoRepository repository;

    @Transactional
    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid DtoRegistrosMedicos datos, UriComponentsBuilder uriComponentsBuilder){
        var medico = new Medico(datos);
        repository.save(medico);

        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DtoDetallesMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DtoListaMedicos>> listar(@PageableDefault(size=10, sort = {"nombre"}) Pageable paginacion){
        var page = repository.findAllByActivoTrue(paginacion)
                .map(DtoListaMedicos::new);
        return ResponseEntity.ok(page);
    }

    @Transactional
    @PutMapping
    public ResponseEntity actualizar(@RequestBody @Valid DtoActualizarMedicos datos){
        var medico = repository.getReferenceById(datos.id());
        medico.actualizarInformacion(datos);
        return ResponseEntity.ok(new DtoDetallesMedico(medico));
    }

    @Transactional
    @DeleteMapping("/{id}")
    //pathvariable indica que la variable id es la que se encunetra
    //mapeada y es un elemento dinamico
    public ResponseEntity eliminar(@PathVariable Long id){
        //eliminacion fisica, elimina por completo de la bd
        // repository.deleteById(id);
        //eliminacion logica, desactiva la informacion
        //pero no la elimina para evitar errores de negocio
        var medico = repository.getReferenceById(id);
        medico.eliminar();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id){
        var medico = repository.getReferenceById(id);

        return ResponseEntity.ok(new DtoDetallesMedico(medico));
    }
}

