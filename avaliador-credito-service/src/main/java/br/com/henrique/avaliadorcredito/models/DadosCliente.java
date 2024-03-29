package br.com.henrique.avaliadorcredito.models;

import lombok.Data;

@Data
public class DadosCliente {
  private Long id;
  private String nome;
  private Integer idade;
}
