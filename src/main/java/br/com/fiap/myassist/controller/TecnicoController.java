package br.com.fiap.myassist.controller;

import br.com.fiap.myassist.entity.Servico;
import br.com.fiap.myassist.entity.Tecnico;
import br.com.fiap.myassist.repository.ServicoRepository;
import br.com.fiap.myassist.repository.TecnicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoController {

    @Autowired
    private TecnicoRepository tecnicoRepository;


    @GetMapping
    public List<Tecnico> findAll(@RequestParam(required = false) String email) {

        if(email==null || email.isBlank()) return tecnicoRepository.findAll();
        return tecnicoRepository.findByEmail(email);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tecnico> findById(@PathVariable Long id) {
        var resultado = tecnicoRepository.findById(id);
        if(resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado.get());

    }




    @PostMapping
    public ResponseEntity<Tecnico> insert(@RequestBody @Valid Tecnico body) {
        var salvo = tecnicoRepository.save(body);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tecnico> update(@PathVariable Long id, @RequestBody Tecnico body) {
        var resultado = tecnicoRepository.findById(id);
        if(resultado.isEmpty()) return ResponseEntity.notFound().build();

        body.setId(id);

        var salvo = tecnicoRepository.save(body);
        return ResponseEntity.ok(salvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Tecnico> delete(@PathVariable Long id) {

        var resultado = tecnicoRepository.findById(id);
        if(resultado.isEmpty()) return ResponseEntity.notFound().build();

        try
        {
            tecnicoRepository.deleteById(id);
        }
        catch (org.springframework.dao.DataIntegrityViolationException ex) {
                System.out.println((ex.toString()));
               throw new RuntimeException("Existem registros dependentes");
        }
        return ResponseEntity.ok().build();


    }

}
