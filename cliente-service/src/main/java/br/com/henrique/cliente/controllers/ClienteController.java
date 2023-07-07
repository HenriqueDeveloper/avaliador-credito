package br.com.henrique.cliente.controllers;

import br.com.henrique.cliente.dtos.ClienteDto;
import br.com.henrique.cliente.dtos.UserDto;
import br.com.henrique.cliente.entitys.Cliente;
import br.com.henrique.cliente.exception.KeycloakException;
import br.com.henrique.cliente.exception.UserNotFoundException;
import br.com.henrique.cliente.services.ClienteService;
import br.com.henrique.cliente.services.KeycloakTokenService;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
@RestController
@RequestMapping("cliente")
@AllArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;
    private final KeycloakTokenService tokenService;

    private final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @PostMapping("/login")
    @Retry(name = "default")
    public ResponseEntity getToken(@RequestBody UserDto userDto) throws KeycloakException, UserNotFoundException {
        return ResponseEntity.ok(tokenService.getToken(userDto));
    }

    @PostMapping("/create")
    public ResponseEntity save(@Valid @RequestBody ClienteDto clienteDto) {
       this.clienteService.save(clienteDto);
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(clienteDto.getCpf())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }
    @GetMapping(path = "/getCliente",params = "cpf")
    public ResponseEntity dadosCliente(@RequestParam("cpf") String cpf) {
        Optional<Cliente> cliente = this.clienteService.getByCpf(cpf);
        if(cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(cliente);
        }
    }

    public ResponseEntity teste(KeycloakException ex) {
        return ResponseEntity.ok().body(new KeycloakException("teste"));
    }
}
