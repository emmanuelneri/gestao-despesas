package br.com.despesas.repository;

import br.com.despesas.model.enums.Categoria;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface DespesaRepositoryCustom {

    Map<Categoria, BigDecimal> findTotalByCategoria(LocalDate startDate, LocalDate finishDate);

    Map<Boolean, BigDecimal> findTotalByPago(LocalDate startDate, LocalDate finishDate);

}
