package br.com.henrique.cartao.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BandeiraCartao {
  MASTERCARD, VISA;

  @JsonCreator
  public static BandeiraCartao fromString(String value) {
    if (value != null && !value.isEmpty()) {
      for (BandeiraCartao bandeira : BandeiraCartao.values()) {
        if (value.equalsIgnoreCase(bandeira.name())) {
          return bandeira;
        }
      }
    }
    return null;
  }
}
