package br.com.despesas.service;

import br.com.despesas.model.Despesa;
import br.com.despesas.repository.DespesaRepository;
import br.com.despesas.to.DespesaBuscaTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    public List<Despesa> findAll() {
        return despesaRepository.findAll();
    }

    public Page<Despesa> findAll(DespesaBuscaTO despesaBuscaTO) {
        return despesaRepository.findAll(despesaBuscaTO.toPredicate(), getPaginacaoOrdenada(despesaBuscaTO));
    }

    private PageRequest getPaginacaoOrdenada(DespesaBuscaTO despesaBuscaTO) {
        return new PageRequest(despesaBuscaTO.getPagina(), despesaBuscaTO.getQuantidade(),
                new Sort(Sort.Direction.DESC, "data")
                        .and(new Sort(Sort.Direction.ASC, "categoria"))
                        .and(new Sort(Sort.Direction.ASC, "descricao")));
    }

    @Transactional
    public Despesa save(Despesa despesa) {
        despesa.validarValor();
        return despesaRepository.save(despesa);
    }

    @Transactional
    public void delete(Long idDespesa) {
        despesaRepository.delete(idDespesa);
    }

    public Despesa findById(Long id) {
        return despesaRepository.findOne(id);
    }
}
