package dev.gabbriellps.gestao.hospitalar.api.controller;

import dev.gabbriellps.gestao.hospitalar.api.configuration.security.TokenService;
import dev.gabbriellps.gestao.hospitalar.api.dto.request.AuthenticationDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.request.RegisterDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.LoginResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.model.Usuario;
import dev.gabbriellps.gestao.hospitalar.api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository repository;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.geraToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario novoUsuario = new Usuario(data.login(), encryptedPassword, data.role());

        this.repository.save(novoUsuario);

        return ResponseEntity.ok().build();
    }
}