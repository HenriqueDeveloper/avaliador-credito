package br.com.henrique.cliente.controllers;

import br.com.henrique.cliente.dtos.ClienteDto;
import br.com.henrique.cliente.entitys.Cliente;
import br.com.henrique.cliente.services.ClienteService;
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
  private final Logger logger = LoggerFactory.getLogger(ClienteController.class);

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

  @GetMapping(path = "/getCliente", params = "cpf")
  public ResponseEntity dadosCliente(@RequestParam("cpf") String cpf) {
    Optional<Cliente> cliente = this.clienteService.getByCpf(cpf);
    if (cliente.isEmpty()) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(cliente);
    }
  }
}
