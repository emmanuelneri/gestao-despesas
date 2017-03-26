package br.com.despesas.model;

import br.com.despesas.exception.BusinessException;
import org.junit.Test;

import java.math.BigDecimal;

public class DespesaTest {

    @Test
    public void validarValorMaiorQueZero() {
        final Despesa despesaValorDez = Despesa.builder()
                .valor(BigDecimal.TEN)
                .build();

        despesaValorDez.validarValor();

        final Despesa despesaValorUm = Despesa.builder()
                .valor(BigDecimal.ONE)
                .build();

        despesaValorUm.validarValor();

        final Despesa despesaValorZeroPontoUm = Despesa.builder()
                .valor(BigDecimal.valueOf(0.1))
                .build();

        despesaValorZeroPontoUm.validarValor();
    }

    @Test(expected = BusinessException.class)
    public void validarValorZero() {
        final Despesa despesaValorZero = Despesa.builder()
                .valor(BigDecimal.ZERO)
                .build();

        despesaValorZero.validarValor();
    }

}
