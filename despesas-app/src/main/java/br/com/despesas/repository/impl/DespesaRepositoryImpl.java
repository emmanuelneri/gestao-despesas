package br.com.despesas.repository.impl;

import br.com.despesas.model.Despesa;
import br.com.despesas.model.QDespesa;
import br.com.despesas.model.enums.Categoria;
import br.com.despesas.repository.DespesaRepositoryCustom;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.*;
import static com.querydsl.core.group.GroupBy.groupBy;

public class DespesaRepositoryImpl implements DespesaRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public Map<Categoria, BigDecimal> findTotalByCategoria(LocalDate startDate, LocalDate finishDate) {
        final QDespesa qDespesa = QDespesa.despesa;

        @SuppressWarnings("unchecked")
        final JPAQuery<Despesa> query = new JPAQuery(entityManager);

        return query
                .from(qDespesa)
                .where(qDespesa.data.between(startDate, finishDate))
                .transform(groupBy(qDespesa.categoria).as(sum(qDespesa.valor)));

    }

    public Map<Boolean, BigDecimal> findTotalByPago(LocalDate startDate, LocalDate finishDate) {
        final QDespesa qDespesa = QDespesa.despesa;

        @SuppressWarnings("unchecked")
        final JPAQuery<Despesa> query = new JPAQuery(entityManager);

        return query
                .from(qDespesa)
                .where(qDespesa.data.between(startDate, finishDate))
                .transform(groupBy(qDespesa.paga).as(sum(qDespesa.valor)));

    }
}
