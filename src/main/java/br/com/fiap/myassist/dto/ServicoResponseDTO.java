package br.com.fiap.myassist.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ServicoResponseDTO {

    private Long id;
    private String descricao;
    private BigDecimal valor;

}
