package br.com.despesas.repository;

import br.com.despesas.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface DespesaRepository extends JpaRepository<Despesa, Long>, QueryDslPredicateExecutor<Despesa> {
}
