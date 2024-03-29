package br.com.henrique.cartao.services;

import br.com.henrique.cartao.dtos.CartaoDto;
import br.com.henrique.cartao.dtos.CartoesPorClienteDto;
import br.com.henrique.cartao.entitys.Cartao;
import br.com.henrique.cartao.entitys.Cliente;
import br.com.henrique.cartao.repositorys.CartaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartaoService {
  @Autowired
  private CartaoRepository cartaoRepository;

  @Autowired
  private ClienteService clienteCartaoService;

  @Transactional
  public Cartao save(CartaoDto cartaoDto) {
    Cartao cartao = new Cartao();
    BeanUtils.copyProperties(cartaoDto, cartao);
    return this.cartaoRepository.save(cartao);
  }

  public List<Cartao> getCartoesRendaMenorIgual(Long renda) {
    var rendaBigDecimal = BigDecimal.valueOf(renda);
    return this.cartaoRepository.findByRendaLessThanEqual(rendaBigDecimal);
  }

  public List<CartoesPorClienteDto> getCartoesByCliente(String cpf) {
    List<Cliente> lista = clienteCartaoService.listCartoesByCpf(cpf);

    List<CartoesPorClienteDto> resolveList = lista.stream()
      .map(CartoesPorClienteDto::fromModel)
      .collect(Collectors.toList());

    return resolveList;
  }

}
