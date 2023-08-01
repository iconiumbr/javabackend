package br.com.fiap.myassist;

import br.com.fiap.myassist.entity.Equipamento;
import br.com.fiap.myassist.enums.TipoEquipamentoEnum;
import br.com.fiap.myassist.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Optional;


@SpringBootApplication
public class MyAssistApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MyAssistApplication.class, args);
	}

	@Autowired
	EquipamentoRepository equipamentoRepository;


	@Override
	public void run(String... args) throws Exception {

		var resultado = equipamentoRepository.findEquipamentoByTipoEquals(TipoEquipamentoEnum.NOTEBOOK);
		//Optional<Equipamento> equipamento = equipamentoRepository.findById(24L);

		// equipamento.setTipo(TipoEquipamentoEnum.NOTEBOOK);
		//equipamento.setModelo("latitude 3400");
		//equipamento.setMarca("DELL");
		//equipamento.setNumeroSerie("XYZ122");

		//equipamentoRepository.save(equipamento);
		//equipamentoRepository.findById((long)22 );

		for(Equipamento e: resultado) {


			System.out.println("Equipamento " + e.getId().toString() + " ID=" + e.getMarca() );
		}
	}
}
