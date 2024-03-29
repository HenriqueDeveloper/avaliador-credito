package br.com.henrique.avaliadorcredito.exception;

public class DadosClienteNotFoundException extends Exception {
  public DadosClienteNotFoundException() {
    super("Dados do cliente não encontrados para o CPF informado.");
  }
}
