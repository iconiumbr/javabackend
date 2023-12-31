package br.com.fiap.myassist.repository;

import br.com.fiap.myassist.entity.Observacao;
import br.com.fiap.myassist.entity.OrdemServico;
import br.com.fiap.myassist.enums.PrioridadeExecucaoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico,Long> {

    @Query("select os from OrdemServico os where os.prioridade = br.com.fiap.myassist.enums.PrioridadeExecucaoEnum.CRITICA")
    List<OrdemServico> findCriticas();

    @Query("select count (os) from OrdemServico  os where os.status = br.com.fiap.myassist.enums.StatusExecucaoEnum.CONCLUIDO")
    Long countConcluidas();

    @Query("select os from OrdemServico os where os.equipamento.numeroSerie = :numeroSerie")
    List<OrdemServico> findByNumeroSerie(String numeroSerie);

    @Query("select obs from Observacao obs where OrdemServico = : idOS")
    List<Observacao> findObservacoes(Long idOS);

}
