package br.com.henrique.avaliadorcredito.services;

import br.com.henrique.avaliadorcredito.exception.DadosClienteNotFoundException;
import br.com.henrique.avaliadorcredito.exception.ErroComunicacaoMicroservicesException;
import br.com.henrique.avaliadorcredito.exception.ErroSolicitacaoCartaoException;
import br.com.henrique.avaliadorcredito.clients.CartaoClient;
import br.com.henrique.avaliadorcredito.clients.ClienteClient;
import br.com.henrique.avaliadorcredito.models.*;
import br.com.henrique.avaliadorcredito.queue.SolicitacaoEmissaoCartaoPublisher;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteClient clientesClient;
    private final CartaoClient cartoesClient;
    private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
      try {
          ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
          ResponseEntity<List<CartaoCliente>> dadosCartaoResponse = cartoesClient.getCartoesByCliente(cpf);
          return SituacaoCliente
                  .builder()
                  .cliente(dadosClienteResponse.getBody())
                  .cartoes(dadosCartaoResponse.getBody())
                  .build();

      } catch (FeignException.FeignClientException e) {
          int status = e.status();
          if(HttpStatus.NOT_FOUND.value() == status) {
              throw new DadosClienteNotFoundException();
          }
          throw new ErroComunicacaoMicroservicesException(e.contentUTF8(), status);
      }
    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRendaAteh(renda);
            List<Cartao> cartoes = cartoesResponse.getBody();
           var listaCartoesAprovados = cartoes.stream().map(cartao -> {

                DadosCliente dadosCliente = dadosClienteResponse.getBody();
                BigDecimal limite = cartao.getLimite();
                BigDecimal idadeBd = BigDecimal.valueOf(dadosCliente.getIdade());
                var fator = idadeBd.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limite);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);

                return aprovado;
            }).collect(Collectors.toList());

           return new RetornoAvaliacaoCliente(listaCartoesAprovados);


        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.contentUTF8(), status);
        }

    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados) {
        try {
          emissaoCartaoPublisher.solicitarCartao(dados);
          var protocolo = UUID.randomUUID().toString();
          return new ProtocoloSolicitacaoCartao(protocolo);
        } catch (Exception e) {
            throw new ErroSolicitacaoCartaoException(e.getMessage());

        }
    }

}
