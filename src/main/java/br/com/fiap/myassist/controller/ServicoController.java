package br.com.fiap.myassist.controller;

import br.com.fiap.myassist.dto.ServicoResponseDTO;
import br.com.fiap.myassist.entity.Servico;
import br.com.fiap.myassist.repository.ServicoRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;
    private ModelMapper mapper = new ModelMapper();


    @GetMapping
    public List<Servico> findAll() {
        return servicoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> findById(@PathVariable Long id) {

        var resultado = servicoRepository.findById(id);
        if(resultado.isEmpty()) return ResponseEntity.notFound().build();

        var conteudo = resultado.get();
        var response = mapper.map(conteudo, ServicoResponseDTO.class);

        return ResponseEntity.ok(response);

    }

    @PostMapping
    public ResponseEntity<Servico> insert(@RequestBody @Valid Servico body) {
        var salvo = servicoRepository.save(body);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servico> update(@PathVariable Long id, @RequestBody Servico body) {
        var resultado = servicoRepository.findById(id);
        if(resultado.isEmpty()) return ResponseEntity.notFound().build();

        body.setId(id);

        var salvo = servicoRepository.save(body);
        return ResponseEntity.ok(salvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Servico> delete(@PathVariable Long id) {

        var resultado = servicoRepository.findById(id);
        if(resultado.isEmpty()) return ResponseEntity.notFound().build();

        try
        {
            servicoRepository.deleteById(id);
        }
        catch (org.springframework.dao.DataIntegrityViolationException ex) {
                System.out.println((ex.toString()));
               throw new RuntimeException("Existem registros dependentes");
        }
        return ResponseEntity.ok().build();


    }

}
