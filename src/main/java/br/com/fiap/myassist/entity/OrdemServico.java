package br.com.fiap.myassist.entity;

import br.com.fiap.myassist.enums.PrioridadeExecucaoEnum;
import br.com.fiap.myassist.enums.StatusExecucaoEnum;
import br.com.fiap.myassist.enums.TipoTelefoneEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="TBL_ORDEM_SERVICO")
public class OrdemServico {

    @Column(name="ID_ORDEM_SERVICO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Equipamento equipamento;

    @ManyToOne
    private Cliente cliente;

    private LocalDateTime dataEntrada;

    private LocalDateTime dataPrevisao;

    @Column(name="TX_DEFEITO")
    private String defeito;

    @Column(name="NR_PRIORIDADE")
    @Enumerated(EnumType.ORDINAL)
    private PrioridadeExecucaoEnum prioridade;

    private List<Tecnico> responsaveis;

    private LocalDateTime dataSaida;

    @Column(name="TX_STATUS")
    @Enumerated(EnumType.STRING)
    private StatusExecucaoEnum status;

}
