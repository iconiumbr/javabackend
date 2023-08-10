package br.com.fiap.myassist.repository;

import br.com.fiap.myassist.entity.OrdemServico;
import br.com.fiap.myassist.entity.Servico;
import br.com.fiap.myassist.entity.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface TecnicoRepository extends JpaRepository<Tecnico,Long> {

    @Query("select tec from Tecnico tec where tec.email = :email")
    List<Tecnico> findByEmail(String email);
}
