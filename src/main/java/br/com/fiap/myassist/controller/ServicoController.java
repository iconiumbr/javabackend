package br.com.fiap.myassist.controller;

import br.com.fiap.myassist.dto.ServicoInsertDTO;
import br.com.fiap.myassist.dto.ServicoResponseDTO;
import br.com.fiap.myassist.entity.Servico;
import br.com.fiap.myassist.repository.ServicoRepository;
import br.com.fiap.myassist.service.ServicoService;
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
    private ServicoService servicoService;

    private ModelMapper mapper = new ModelMapper();

    @GetMapping
    public List<ServicoResponseDTO> findAll() {

        return servicoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> findById(@PathVariable Long id) {

        var resultado = servicoService.findById(id);
        if(resultado.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(resultado.get());

    }

    @PostMapping
    public ResponseEntity<ServicoResponseDTO> insert(@RequestBody @Valid ServicoInsertDTO body) {

        return ResponseEntity.ok(servicoService.insert(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> update(@PathVariable Long id, @RequestBody ServicoResponseDTO body) {

        body.setId(id);
        var resultado = servicoService.update(body);
        if(resultado.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(resultado.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Servico> delete(@PathVariable Long id) {

        if (!servicoService.delete(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();

    }

}
