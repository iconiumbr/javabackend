package br.com.fiap.myassist.service;

import br.com.fiap.myassist.dto.FiltroOsDTO;
import br.com.fiap.myassist.dto.OrdemServicoAtualizacaoDTO;
import br.com.fiap.myassist.dto.OrdemServicoInsercaoDTO;
import br.com.fiap.myassist.dto.OrdemServicoResponseDTO;
import br.com.fiap.myassist.entity.Cliente;
import br.com.fiap.myassist.entity.Equipamento;
import br.com.fiap.myassist.entity.Observacao;
import br.com.fiap.myassist.entity.OrdemServico;
import br.com.fiap.myassist.enums.PrioridadeExecucaoEnum;
import br.com.fiap.myassist.enums.StatusExecucaoEnum;
import br.com.fiap.myassist.enums.TipoDocumentoEnum;
import br.com.fiap.myassist.repository.ClienteRepository;
import br.com.fiap.myassist.repository.EquipamentoRepository;
import br.com.fiap.myassist.repository.ObservacaoRepository;
import br.com.fiap.myassist.repository.OrdemServicoRepository;
import jakarta.persistence.criteria.*;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrdemServicoService {

    private static final Set<StatusExecucaoEnum> STATUS_PERMITEM_FINALIZACAO = Set.of(StatusExecucaoEnum.ABERTO, StatusExecucaoEnum.EM_ANDAMENTO);

    @Autowired
    private OrdemServicoRepository osRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private ObservacaoRepository observacaoRepository;

    private ModelMapper mapper = new ModelMapper();


    public OrdemServicoResponseDTO inserir(OrdemServicoInsercaoDTO request) {
        var cliente = Cliente.builder().tipoDocumento(TipoDocumentoEnum.CPF)
                                       .nome(request.getCliente())
                             .build();
        clienteRepository.save(cliente);

        var equipamento = mapper.map(request.getEquipamento(), Equipamento.class);
        equipamento.setNumeroSerie(String.valueOf(System.currentTimeMillis()));
        equipamentoRepository.save(equipamento);

        var entity = mapper.map(request, OrdemServico.class);
        entity.setCliente(cliente);
        entity.setEquipamento(equipamento);
        entity.setStatus(StatusExecucaoEnum.ABERTO);
        entity.setPrioridade(PrioridadeExecucaoEnum.BAIXA);
        osRepository.save(entity);

        if (StringUtils.isNotBlank(request.getObservacoes())) {
            var observacao = Observacao.builder().ordemServico(entity)
                                                 .texto(request.getObservacoes())
                                                 .data(entity.getDataEntrada())
                                       .build();
            observacaoRepository.save(observacao);
        }

        return mapper.map(entity, OrdemServicoResponseDTO.class);
    }

    public Optional<OrdemServicoResponseDTO> buscarPorId(Integer id) {
        var resultado = osRepository.findById(id);
        if (resultado.isEmpty()) {
            return Optional.empty();
        }
        var dto = mapper.map(resultado.get(), OrdemServicoResponseDTO.class);
        var observaoes = osRepository.findObservacoes(id).stream()
                                                         .map(Observacao::getTexto)
                                                         .collect(Collectors.joining(" | "));
        dto.setObservacoes(observaoes);
        return Optional.of(dto);
    }

    public List<OrdemServicoResponseDTO> listarTodos(FiltroOsDTO filtro) {
        var resultado = osRepository.findAll(getSpecification(filtro));
        return resultado.stream().map(e -> mapper.map(e, OrdemServicoResponseDTO.class)).toList();
    }

    private Specification<OrdemServico> getSpecification(FiltroOsDTO filtro) {
        return new Specification<OrdemServico>() {
            @Override
            public Predicate toPredicate(Root<OrdemServico> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicados = new ArrayList<>();
                if (filtro.getDataInicio() !=null) {
                    var predicateDataInicio = criteriaBuilder
                            .greaterThanOrEqualTo(root.get("dataEntrada"),filtro.getDataInicio().atStartOfDay());
                    predicados.add(predicateDataInicio);
                }

                if (filtro.getDataFim() !=null) {
                    var predicateDataFim = criteriaBuilder
                            .lessThanOrEqualTo(root.get("dataPrevisao"),filtro.getDataInicio().atTime(LocalTime.MAX));
                    predicados.add(predicateDataFim);
                }

                if(StringUtils.isNotBlank((filtro.getDocumento()))) {
                    Join<OrdemServico,Cliente> clienteJoin = root.join("cliente");
                    var predicadoDocumento = criteriaBuilder.equal(clienteJoin.get("documento"),filtro.getDocumento());
                    predicados.add(predicadoDocumento);
                }

                if(StringUtils.isNotBlank((filtro.getNome()))) {
                    Join<OrdemServico,Cliente> clienteJoin = root.join("cliente");
                    var predicadoDocumento = criteriaBuilder.like(
                            criteriaBuilder.upper(clienteJoin.get("nome")),
                            "%" + filtro.getNome().toUpperCase() + "%");
                    predicados.add(predicadoDocumento);
                }


                return criteriaBuilder.and(predicados.toArray((new Predicate[]{})));
            }
        };


    }



    public Optional<OrdemServicoResponseDTO> finalizar(Integer id) {
        var resultado = osRepository.findById(id);
        if (resultado.isEmpty()) {
            return Optional.empty();
        }

        var entity = resultado.get();
        if (!STATUS_PERMITEM_FINALIZACAO.contains(entity.getStatus())) {
            throw new RuntimeException("Ordem de serviço com status " + entity.getStatus());
        }

        entity.setStatus(StatusExecucaoEnum.CONCLUIDO);
        entity.setDataSaida(LocalDateTime.now());
        osRepository.save(entity);

        return Optional.of(mapper.map(entity, OrdemServicoResponseDTO.class));
    }

    public OrdemServicoResponseDTO atualizar(Integer id,
                                             OrdemServicoAtualizacaoDTO request) {
        var entity = osRepository.findById(id).orElseThrow();
        if (!STATUS_PERMITEM_FINALIZACAO.contains(entity.getStatus())) {
            throw new RuntimeException("Ordem de serviço com status " + entity.getStatus());
        }

        BeanUtils.copyProperties(request, entity);

        entity.getCliente().setNome(request.getCliente());
        clienteRepository.save(entity.getCliente());

        var equipamento = entity.getEquipamento();
        BeanUtils.copyProperties(request.getEquipamento(), equipamento);
        equipamentoRepository.save(equipamento);

        osRepository.save(entity);

        if (StringUtils.isNotBlank(request.getObservacoes())) {
            var observacao = Observacao.builder().ordemServico(entity)
                                                 .texto(request.getObservacoes())
                                                 .data(entity.getDataEntrada())
                                       .build();
            observacaoRepository.save(observacao);
        }

        return mapper.map(entity, OrdemServicoResponseDTO.class);
    }

}
