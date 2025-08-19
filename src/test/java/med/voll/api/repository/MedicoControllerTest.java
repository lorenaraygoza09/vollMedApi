package med.voll.api.repository;

import med.voll.api.dto.DtoDetallesMedico;
import med.voll.api.dto.DtoDireccion;
import med.voll.api.dto.DtoRegistrosMedicos;
import med.voll.api.model.Direccion;
import med.voll.api.model.Especialidad;
import med.voll.api.model.Medico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class MedicoControllerTest {


        @Autowired
        private MockMvc mvc;

        @Autowired
        private JacksonTester<DtoRegistrosMedicos> datosRegistroMedicoJson;

        @Autowired
        private JacksonTester<DtoDetallesMedico> datosDetalleMedicoJson;

        private IMedicoRepository repository;

    @Test
    @DisplayName("Debería devolver código http 400 cuando las informaciones son inválidas")
    @WithMockUser
    void registrar_escenario1() throws Exception {
        var response = mvc
                .perform(post("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debería devolver código http 200 cuando las informaciones son válidas")
    @WithMockUser
    void registrar_escenario2() throws Exception {
        var datosRegistro = new DtoRegistrosMedicos(
                "Medico",
                "medico@voll.med",
                "61999999999",
                "123456",
                Especialidad.CARDIOLOGIA,
                datosDireccion()
        );

        when(repository.save(any())).thenReturn(new Medico(datosRegistro));

        var response = mvc
                .perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(datosRegistroMedicoJson.write(datosRegistro).getJson()))
                .andReturn().getResponse();

        var datosDetalle= new DtoDetallesMedico(
                null,
                datosRegistro.nombre(),
                datosRegistro.email(),
                datosRegistro.documento(),
                datosRegistro.telefono(),
                datosRegistro.especialidad(),
                new Direccion(datosRegistro.direccion())
        );
        var jsonEsperado = datosDetalleMedicoJson.write(datosDetalle).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
    private DtoDireccion datosDireccion() {
        return new DtoDireccion(
                "1",
                "2",
                "3",
                "4",
                "5",
                "43500",
                "7"
        );
    }
    }

