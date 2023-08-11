package br.com.fiap.myassist.service;

import br.com.fiap.myassist.dto.ServicoInsertDTO;
import br.com.fiap.myassist.dto.ServicoResponseDTO;
import br.com.fiap.myassist.entity.Servico;
import br.com.fiap.myassist.repository.ServicoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;
    private ModelMapper mapper = new ModelMapper();

    public List<ServicoResponseDTO> findAll() {

        return servicoRepository.findAll().stream()
                .map(e -> mapper.map(e, ServicoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<ServicoResponseDTO> findById(Long id) {

        return servicoRepository.findById(id).
                map(e -> mapper.map(e, ServicoResponseDTO.class));
    }

    public ServicoResponseDTO insert(ServicoInsertDTO insertDTO) {

        return mapper.map(
                servicoRepository.save(
                    mapper.map(insertDTO,Servico.class)),
                ServicoResponseDTO.class);
    }

    public Optional<ServicoResponseDTO> update(ServicoResponseDTO updateDTO) {

        if (findById(updateDTO.getId()).isEmpty()) return Optional.empty();

        return Optional.of(
                mapper.map(
                servicoRepository.save(
                        mapper.map(updateDTO,Servico.class))
                        ,ServicoResponseDTO.class));

    }

    public Boolean delete(Long id) {

        if (servicoRepository.findById(id).isEmpty()) return false;

        try
        {
            servicoRepository.deleteById(id);
        }
        catch (org.springframework.dao.DataIntegrityViolationException ex) {
            System.out.println((ex.toString()));
            throw new RuntimeException("Existem registros dependentes");
        }
        return true;

    }


}



