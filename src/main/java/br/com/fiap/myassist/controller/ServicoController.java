package br.com.fiap.myassist.controller;

import br.com.fiap.myassist.entity.Servico;
import br.com.fiap.myassist.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;


    @GetMapping
    public List<Servico> findAll() {
        return servicoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> findById(@PathVariable Long id) {
        var resultado = servicoRepository.findById(id);
        if(resultado.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(resultado.get());

    }

    @PostMapping
    public ResponseEntity<Servico> insert(@RequestBody Servico body) {
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

}
