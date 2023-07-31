package br.com.fiap.myassist.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

@Table(name="TBL_SERVICO")
public class Servico {

    @Id
    @Column(name="ID_EQUIPAMENTO")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="TX_DESCRICAO")
    private String descricao;

    @Column(name="NR_VALOR")
    private BigDecimal valor;

}
