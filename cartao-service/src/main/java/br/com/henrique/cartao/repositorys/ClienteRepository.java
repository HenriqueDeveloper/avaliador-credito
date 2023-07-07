package br.com.henrique.cartao.repositorys;

import br.com.henrique.cartao.entitys.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByCpf(String cpf);
}
