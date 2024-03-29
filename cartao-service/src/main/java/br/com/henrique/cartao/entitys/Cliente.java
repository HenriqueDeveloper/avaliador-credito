package br.com.henrique.cartao.entitys;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Data
public class Cliente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String cpf;
  @ManyToOne
  @JoinColumn(name = "id_cartao")
  private Cartao cartao;
  private BigDecimal limite;
}
