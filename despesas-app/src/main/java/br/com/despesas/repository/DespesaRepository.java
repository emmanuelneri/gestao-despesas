package br.com.despesas.repository;

import br.com.despesas.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface DespesaRepository extends JpaRepository<Despesa, Long>, QueryDslPredicateExecutor<Despesa>, DespesaRepositoryCustom {

    BigDecimal findTotalByDatas(LocalDate startDate, LocalDate finishDate);

}
