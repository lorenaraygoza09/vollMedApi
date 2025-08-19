package med.voll.api.repository;

import jakarta.persistence.EntityManager;
import med.voll.api.controller.PacienteController;
import med.voll.api.dto.DtoDetallesMedico;
import med.voll.api.dto.DtoDireccion;
import med.voll.api.dto.DtoRegistroPx;
import med.voll.api.dto.DtoRegistrosMedicos;
import med.voll.api.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class IMedicoRepositoryTest {

    @Autowired
    private IMedicoRepository medicoRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Debe devolver null cuando el medico buscado existe pero no esta disponible en la fecha deseada")
    void medicoAleatorioDisponibleEscenario1() {
    var lunesProximoHora11 = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(11);
    var medico = new Medico();
    var paciente = registrarPaciente("2", "2@gmail.com", "12365", "8765564428", new Direccion());
    //var consulta = registrarConsulta(medico, paciente, lunesProximoHora11);
    var medicoLibre = medicoRepository.medicoAleatorioDisponible(Especialidad.GINECOLOGIA, lunesProximoHora11);
        assertThat(medicoLibre).isNull();
    }


    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha){
        entityManager.persist(new Consulta(null, medico, paciente, fecha, null));
    }

    /*
    private Medico registrarMedico(String nombre, String email, String documento, String telefono, Especialidad especialidad, Direccion direccion){
        var medico = new Medico(new DtoDetallesMedico(nombre, email, documento, telefono, especialidad, direccion));
        entityManager.persist(medico);
        return medico;
    }
     */

    private Paciente registrarPaciente(String nombre, String email, String documento, String telefono, Direccion direccion){
        var paciente = new Paciente(new DtoRegistroPx(nombre, email, documento, telefono, direccion));
        entityManager.persist(paciente);
        return paciente;
    }

    private DtoRegistrosMedicos datosMedico(String nombre, String email, String documento, Especialidad especialidad){
        return new DtoRegistrosMedicos(
                nombre,
                email,
                "6971125374",
                documento,
                especialidad,
                new DtoDireccion("1", "22", "34", "sur", "A", "2430", "Jal")
        );
    }

    private DtoRegistroPx datosPaciente(String nombre, String email, String documento){
        return new DtoRegistroPx(
                nombre,
                email,
                "6971125374",
                documento,
                new Direccion()
        );
    }
}