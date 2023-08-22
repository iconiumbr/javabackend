package br.com.fiap.myassist.controller;

import br.com.fiap.myassist.dto.FiltroOsDTO;
import br.com.fiap.myassist.dto.OrdemServicoAtualizacaoDTO;
import br.com.fiap.myassist.dto.OrdemServicoInsercaoDTO;
import br.com.fiap.myassist.dto.OrdemServicoResponseDTO;
import br.com.fiap.myassist.service.OrdemServicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/ordens-servico")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService osService;




    @GetMapping
    public List<OrdemServicoResponseDTO> listarTodos(@RequestParam(required = false) String documento,
                                                     @RequestParam(required = false) String nome,
                                                     @RequestParam(required = false) String modelo,
                                                     @RequestParam(required = false) LocalDate dataInicio,
                                                     @RequestParam(required = false) LocalDate dataFim)

    {
        var filtro = FiltroOsDTO.builder().documento(documento)
                .nome(nome)
                .modelo(modelo)
                .dataInicio(dataInicio)
                .dataFim(dataFim).build();


        return osService.listarTodos(filtro);
    }

    @PostMapping
    public ResponseEntity<OrdemServicoResponseDTO> inserir(@RequestBody @Valid OrdemServicoInsercaoDTO body) {
        var salvo = osService.inserir(body);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoResponseDTO> buscarPorId(@PathVariable Integer id) {
        var resultado = osService.buscarPorId(id);
        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado.get());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrdemServicoResponseDTO> finalizar(@PathVariable Integer id) {
        var resultado = osService.finalizar(id);
        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdemServicoResponseDTO> atualizar(@PathVariable Integer id,
                                                             @RequestBody @Valid OrdemServicoAtualizacaoDTO body) {
        var resultado = osService.atualizar(id, body);
        return ResponseEntity.ok(resultado);
    }



}
