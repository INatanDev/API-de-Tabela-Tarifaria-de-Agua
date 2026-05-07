package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.service.tarifario;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.categoria.CategoriaCadastroDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.categoria.CategoriaDetalheDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.faixa_consumo.FaixaCadastroDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.faixa_consumo.FaixaDetalheDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.tarifario.TabelaTarifariaCadastroDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.tarifario.TabelaTarifariaDetalheDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.tarifario.TabelaTarifariaDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.categoria.Categoria;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.faixa_consumo.FaixaConsumo;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.tarifario.TabelaTarifario;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.repository.categoria.CategoriaRepository;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.repository.tarifario.TabelaTarifariaRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.utils.Utilities.log;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TabelaTarifariaService {

    @Autowired
    private TabelaTarifariaRepository repository;

    @Autowired 
    private CategoriaRepository categoriaRepository;

    @Transactional()
    public TabelaTarifariaDto criarTabelaTarifaria(TabelaTarifariaCadastroDto dto){

        log.info("Inicio da criação da tabela Tarifaria::TabelaTarifariaService");

        validarCategorias(dto.getCategorias());
        repository.findByAtivoTrue().ifPresent(atual -> {
            atual.setAtivo(false); 
            atual.setDataFim(LocalDate.now()); 
            repository.save(atual); });
        
        TabelaTarifario salva = repository.save(new TabelaTarifario(dto.getNome()));

        for (CategoriaCadastroDto categoriaDto : dto.getCategorias()) {
            List<FaixaCadastroDto> faixasOrdenadas = categoriaDto.getFaixas().stream()
                    .sorted(Comparator.comparing(FaixaCadastroDto::getInicio))
                    .toList();
            validarFaixas(faixasOrdenadas);
            Categoria categoria = new Categoria(normalizarCategoria(categoriaDto.getNome()), salva);
            for (FaixaCadastroDto faixaDto : faixasOrdenadas) {
                categoria.getFaixas().add(new FaixaConsumo(faixaDto.getInicio(), faixaDto.getFim(), faixaDto.getValorUnitario(), categoria));
            }
            categoriaRepository.save(categoria);
        }

        log.info("Fim da criação da tabela Tarifaria::TabelaTarifariaService");

        return new TabelaTarifariaDto(
            salva.getId(), 
            salva.getNome(), 
            salva.getDataInicio(), 
            salva.getDataFim(), 
            salva.getAtivo());
    }

    @Transactional(readOnly = true)
    public List<TabelaTarifariaDetalheDto> listar() {
        log.info("Inicio da listagem das tabelas tarifárias::TabelaTarifariaService");
        return repository.findAll().stream()
                .map(t -> new TabelaTarifariaDetalheDto(
                        t.getId(),
                        t.getNome(),
                        t.getDataInicio(),
                        t.getDataFim(),
                        t.getAtivo(),
                        categoriaRepository.findByTarifarioIdOrderByNomeAsc(t.getId()).stream()
                                .map(c -> new CategoriaDetalheDto(
                                        c.getId(),
                                        c.getNome(),
                                        c.getFaixas().stream()
                                                .sorted((a, b) -> a.getInicio().compareTo(b.getInicio()))
                                                .map(f -> new FaixaDetalheDto(f.getId(), f.getInicio(), f.getFim(), f.getValorUnitario()))
                                                .toList()))
                                .toList()))
                .toList();
    }

    @Transactional
    public void excluir(Long id) {
        log.info("Inicio da exclusão da tabela Tarifaria::TabelaTarifariaService");
        TabelaTarifario tabela = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tabela tarifária não encontrada"));
        tabela.setAtivo(false);
        tabela.setDataFim(LocalDate.now());
        repository.save(tabela);
    }

    private void validarCategorias(List<CategoriaCadastroDto> categorias) {
        log.info("Validando categorias da tabela Tarifaria::TabelaTarifariaService");
        Set<String> obrigatorias = Set.of("COMERCIAL", "INDUSTRIAL", "PARTICULAR", "PÚBLICO");
        Set<String> nomes = categorias.stream().map(c -> normalizarCategoria(c.getNome())).collect(Collectors.toSet());
        if (categorias.size() != obrigatorias.size() || !nomes.equals(obrigatorias)) throw new IllegalArgumentException("A tabela deve conter exatamente as categorias COMERCIAL, INDUSTRIAL, PARTICULAR e PÚBLICO");
    }

    private void validarFaixas(List<FaixaCadastroDto> faixas) {
        log.info("Validando faixas da tabela Tarifaria::TabelaTarifariaService");
        if (faixas == null || faixas.isEmpty() || faixas.get(0).getInicio() != 0) throw new IllegalArgumentException("As faixas devem iniciar em 0");
        for (int i = 0; i < faixas.size(); i++) {
            FaixaCadastroDto atual = faixas.get(i);
            if (atual.getInicio() >= atual.getFim()) throw new IllegalArgumentException("Inicio da faixa deve ser menor que o fim");
            if (atual.getValorUnitario().signum() <= 0) throw new IllegalArgumentException("Valor unitário deve ser maior que zero");
            if (i > 0 && atual.getInicio() != faixas.get(i - 1).getFim() + 1) throw new IllegalArgumentException("As faixas devem ser contínuas e sem sobreposição");
        }
        if (faixas.get(faixas.size() - 1).getFim() < 99999) throw new IllegalArgumentException("A última faixa deve cobrir consumos altos, ex: 99999");
    }

    private String normalizarCategoria(String nome) {
        String normalizado = Normalizer.normalize(nome.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toUpperCase();
        return "PUBLICO".equals(normalizado) ? "PÚBLICO" : normalizado;
    }
}
