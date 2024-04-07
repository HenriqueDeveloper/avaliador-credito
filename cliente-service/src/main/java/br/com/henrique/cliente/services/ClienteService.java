package br.com.henrique.cliente.services;

import br.com.henrique.cliente.dtos.ClienteDto;
import br.com.henrique.cliente.entitys.Cliente;
import br.com.henrique.cliente.repositorys.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
  @Autowired
  public final ClienteRepository clienteRepository;

  @Transactional
  public Cliente save(ClienteDto clienteDto) {
    Cliente cliente = new Cliente();
    String cpfSemMascara = clienteDto.getCpf().replaceAll("[.-]", "");
    clienteDto.setCpf(cpfSemMascara);
    BeanUtils.copyProperties(clienteDto, cliente);
    return this.clienteRepository.save(cliente);
  }

  public Optional<Cliente> getByCpf(String cpf) {
    return this.clienteRepository.findByCpf(cpf);
  }
}
