package br.com.fiap.myassist.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ServicoInsertDTO {

    @NotBlank(message = "Descricao obrigatoria")
    @Size(max=80, message="Descricao max 80 char")
    private String descricao;

    @DecimalMin(value="20.00",message = "Valor minimo 20 reais")
    @Digits(fraction = 2, integer = 4,message = "Valor invalido")
    private BigDecimal valor;

}
