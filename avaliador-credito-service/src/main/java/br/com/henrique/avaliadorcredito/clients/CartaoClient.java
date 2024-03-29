package br.com.henrique.avaliadorcredito.clients;

import br.com.henrique.avaliadorcredito.models.Cartao;
import br.com.henrique.avaliadorcredito.models.CartaoCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "cartao-service", path = "/cartao")
public interface CartaoClient {
  @GetMapping(path = "/getCartoesByCliente", params = "cpf")
  ResponseEntity<List<CartaoCliente>> getCartoesByCliente(
    @RequestParam("cpf") String cpf);

  @GetMapping(path = "/getCartoesRendaAteh", params = "renda")
  ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam("renda") Long renda);
}
