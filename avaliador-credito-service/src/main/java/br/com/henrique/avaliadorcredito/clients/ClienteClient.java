package br.com.henrique.avaliadorcredito.clients;

import br.com.henrique.avaliadorcredito.models.DadosCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cliente-service", path = "/cliente")
public interface ClienteClient {
    @GetMapping(path = "/getCliente" , params = "cpf")
    ResponseEntity<DadosCliente> dadosCliente(@RequestParam("cpf") String cpf);

    }
