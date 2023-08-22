package br.com.fiap.myassist.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class FiltroOsDTO {

    private String documento;
    private String nome;
    private String modelo;
    private LocalDate dataInicio;
    private LocalDate dataFim;

}
