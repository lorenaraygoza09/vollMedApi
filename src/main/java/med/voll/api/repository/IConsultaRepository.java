package med.voll.api.repository;

import med.voll.api.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface IConsultaRepository extends JpaRepository<Consulta, Long> {
    boolean existsByPacienteIdAndFechaBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorarrio);

    boolean existsByMedicoIdAndFecha(Long idMedico, LocalDateTime fecha);
}
