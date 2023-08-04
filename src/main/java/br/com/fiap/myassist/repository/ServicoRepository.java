package br.com.fiap.myassist.repository;

import br.com.fiap.myassist.entity.Servico;
import br.com.fiap.myassist.enums.TipoEquipamentoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico,Long> {

    List<Servico> findByValorGreaterThanEqual(BigDecimal Valor);

}
