package br.com.fiap.myassist.entity;

import br.com.fiap.myassist.enums.TipoTelefoneEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Telefone {

    @Column(name="TX_AREA_FONE")
    private String area;

    @Column(name="TX_FONE")
    private String numero;

    @Column(name="TX_TIPO_FONE")
    @Enumerated(EnumType.STRING)
    private TipoTelefoneEnum tipo;

}


