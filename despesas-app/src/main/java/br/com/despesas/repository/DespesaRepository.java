package br.com.despesas.repository;

import br.com.despesas.model.Despesa;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long>, QueryDslPredicateExecutor<Despesa>, DespesaRepositoryCustom {

    BigDecimal findTotalByDatas(boolean pago, LocalDate startDate, LocalDate finishDate);

    @Query("select d from Despesa d where d.paga = false and d.data >= :#{#date} order by d.data, d.id")
    List<Despesa> findDespesasAVencerAPartirDaData(@Param("date") LocalDate date, Pageable pageable);

    @Query("select d from Despesa d where d.paga = false and d.data < CURRENT_DATE order by d.data, d.id")
    List<Despesa> findDespesaAtrasadas();

}
