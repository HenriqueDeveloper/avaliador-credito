package br.com.henrique.cartao.services;

import br.com.henrique.cartao.entitys.Cliente;
import br.com.henrique.cartao.repositorys.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    @Autowired
    private final ClienteRepository clienteCartaoRepository;

    public List<Cliente> listCartoesByCpf(String cpf) {
        return this.clienteCartaoRepository.findByCpf(cpf);

    }
}
