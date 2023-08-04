package br.com.fiap.myassist;

import br.com.fiap.myassist.entity.Equipamento;
import br.com.fiap.myassist.entity.Servico;
import br.com.fiap.myassist.enums.TipoEquipamentoEnum;
import br.com.fiap.myassist.repository.EquipamentoRepository;
import br.com.fiap.myassist.repository.OrdemServicoRepository;
import br.com.fiap.myassist.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Optional;


@SpringBootApplication
public class MyAssistApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MyAssistApplication.class, args);
	}

	@Autowired
	EquipamentoRepository equipamentoRepository;
	@Autowired
	ServicoRepository servicoRepository;

	@Autowired
	OrdemServicoRepository ordemServicoRepository;


	@Override
	public void run(String... args) throws Exception {

		var resultado = servicoRepository.findByValorGreaterThanEqual(new BigDecimal(("80")));
		resultado.forEach(e -> System.out.println(e.getDescricao()));

		System.out.println(ordemServicoRepository.countConcluidas());

	}
}
