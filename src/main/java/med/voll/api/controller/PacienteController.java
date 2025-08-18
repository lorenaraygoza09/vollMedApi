package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.DtoActualizarPx;
import med.voll.api.dto.DtoListadoPx;
import med.voll.api.dto.DtoRegistroPx;
import med.voll.api.model.Paciente;
import med.voll.api.repository.IPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private IPacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public void registrarPx(@RequestBody @Valid DtoRegistroPx datos){
        pacienteRepository.save(new Paciente(datos));
    }

    @GetMapping
    public Page<DtoListadoPx> listarPx(@PageableDefault(size = 5, sort = {"nombre"})Pageable paginacion){
        return pacienteRepository.findAllByActivoTrue(paginacion).map(DtoListadoPx::new);
    }

    @PutMapping
    @Transactional
    public void actualizarPx(@RequestBody @Valid DtoActualizarPx datos){
        var paciente = pacienteRepository.getReferenceById(datos.id());
        paciente.actualizarInformaciones(datos);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarPx(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.eliminar();
    }
}
