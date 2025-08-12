package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.DtoActualizarMedicos;
import med.voll.api.dto.DtoListaMedicos;
import med.voll.api.dto.DtoRegistrosMedicos;
import med.voll.api.model.Medico;
import med.voll.api.repository.IMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private IMedicoRepository repository;

    @Transactional
    @PostMapping
    public void registrar(@RequestBody @Valid DtoRegistrosMedicos datos){
        repository.save(new Medico(datos));
    }

    @GetMapping
    public Page<DtoListaMedicos> listar(@PageableDefault(size=10, sort = {"nombre"}) Pageable paginacion){
        return repository.findAllByActivoTrue(paginacion)
                .map(DtoListaMedicos::new);
    }

    @Transactional
    @PutMapping
    public void actualizar(@RequestBody @Valid DtoActualizarMedicos datos){
        var medico = repository.getReferenceById(datos.id());
        medico.actualizarInformacion(datos);
    }

    @Transactional
    @DeleteMapping("/{id}")
    //pathvariable indica que la variable id es la que se encunetra
    //mapeada y es un elemento dinamico
    public void eliminar(@PathVariable Long id){
        //eliminacion fisica, elimina por completo de la bd
        // repository.deleteById(id);
        //eliminacion logica, desactiva la informacion
        //pero no la elimina para evitar errores de negocio
        var medico = repository.getReferenceById(id);
        medico.eliminar();
    }
}

