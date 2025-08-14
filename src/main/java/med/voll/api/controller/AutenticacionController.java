package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.DtoAutenticacion;
import med.voll.api.dto.DtoTokenJWT;
import med.voll.api.model.Usuario;
import med.voll.api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private TokenService tokenService;


    @Autowired
    private AuthenticationManager manager;


    @PostMapping
    public ResponseEntity iniciarSesion(@RequestBody @Valid DtoAutenticacion datos) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(datos.userName(), datos.contrasenia());
            var autenticacion = manager.authenticate(authenticationToken);

            var tokenJWT = tokenService.generarToken((Usuario) autenticacion.getPrincipal());

            return ResponseEntity.ok(new DtoTokenJWT(tokenJWT));


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}