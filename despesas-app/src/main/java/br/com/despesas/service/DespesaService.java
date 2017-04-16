package br.com.despesas.service;

import br.com.despesas.model.Despesa;
import br.com.despesas.model.enums.Categoria;
import br.com.despesas.repository.DespesaRepository;
import br.com.despesas.to.DespesaBuscaTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

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

    public Map<Categoria, BigDecimal> findTotalMesAtualByCategoria() {
        final LocalDate startDate = LocalDate.now().with(firstDayOfMonth());
        final LocalDate finishDate = startDate.with(lastDayOfMonth());
        return despesaRepository.findTotalByCategoria(startDate, finishDate);
    }

    @Transactional
    public Despesa pagar(Despesa despesa) {
        despesa.pagar();
        return save(despesa);
    }

    public BigDecimal findTotalAPagarMesAtual() {
        return findTotalAPagarMesAtual(false);
    }

    public BigDecimal findTotalPagoMesAtual() {
        return findTotalAPagarMesAtual(true);
    }

    private BigDecimal findTotalAPagarMesAtual(boolean pago) {
        final LocalDate startDate = LocalDate.now().with(firstDayOfMonth());
        final LocalDate finishDate = startDate.with(lastDayOfMonth());
        final BigDecimal valor = despesaRepository.findTotalByDatas(pago, startDate, finishDate);
        return Optional.ofNullable(valor).orElse(BigDecimal.ZERO);
    }

    public List<Despesa> findProximasCincoDespesasAVencer() {
        return despesaRepository.findDespesasAVencerAPartirDaData(LocalDate.now(), new PageRequest(0, 5));
    }

    public List<Despesa> findDespesaAtrasadas() {
        return despesaRepository.findDespesaAtrasadas();
    }

    public Map<Boolean, BigDecimal> findTotalByPago() {
        final LocalDate startDate = LocalDate.now().with(firstDayOfMonth());
        final LocalDate finishDate = startDate.with(lastDayOfMonth());
        return despesaRepository.findTotalByPago(startDate, finishDate);
    }
}
