package br.com.henrique.cliente.dtos;

import br.com.henrique.cliente.entitys.Cliente;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class ClienteDto {
    @NotEmpty(message = "Campo cpf não pode ser vazio.")
    @NotNull(message = "Campo cpf não pode ser nulo.")
    @CPF(message = "Insira um cpf válido")
    private String cpf;
    @NotEmpty(message = "Campo nome não pode ser vazio.")
    @NotNull(message = "Campo nome não pode ser nulo.")
    private String nome;
    @NotNull(message = "Campo idade não pode ser nulo.")
    private Integer idade;

    public Cliente toModel() {
         return new Cliente(cpf, nome, idade);
    }
}
