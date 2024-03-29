package br.com.henrique.cartao.dtos;

import br.com.henrique.cartao.entitys.Cartao;
import br.com.henrique.cartao.enums.BandeiraCartao;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoDto {
  @NotEmpty(message = "Campo nome não pode ser vazio.")
  private String nome;
  @NotNull(message = "o campo bandeira precisa ser um valor válido")
  private BandeiraCartao bandeira;
  @NotNull(message = "Campo renda não pode ser vazio.")
  private BigDecimal renda;
  @NotNull(message = "Campo limite não pode ser vazio.")
  private BigDecimal limite;

  public Cartao toModel() {
    return new Cartao(nome, bandeira, renda, limite);
  }
}
