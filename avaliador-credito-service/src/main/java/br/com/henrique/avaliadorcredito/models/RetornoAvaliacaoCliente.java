package br.com.henrique.avaliadorcredito.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder()
public class RetornoAvaliacaoCliente {
    private List<CartaoAprovado> cartoes;
}
