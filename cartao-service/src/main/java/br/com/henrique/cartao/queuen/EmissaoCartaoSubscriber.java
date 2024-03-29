package br.com.henrique.cartao.queuen;

import br.com.henrique.cartao.entitys.Cartao;
import br.com.henrique.cartao.entitys.Cliente;
import br.com.henrique.cartao.entitys.DadosSolicitacaoEmissaoCartao;
import br.com.henrique.cartao.repositorys.CartaoRepository;
import br.com.henrique.cartao.repositorys.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {
  @Autowired
  private final CartaoRepository cartaoRepository;
  @Autowired
  private ClienteRepository clienteCartaoRepository;

  @RabbitListener(queues = "${mq.queue.emissao-cartoes}")
  public void receberSolicitacaoEmissao(@Payload String payload) {
    try {
      var mapper = new ObjectMapper();
      DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
      Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();
      Cliente clienteCartao = new Cliente();
      clienteCartao.setCartao(cartao);
      clienteCartao.setCpf(dados.getCpf());
      clienteCartao.setLimite(dados.getLimiteLiberado());
      clienteCartaoRepository.save(clienteCartao);
    } catch (Exception e) {
      log.error("Erro ao receber solicitação de emissão de cartão: {} ", e.getMessage());
    }
  }
}
