package br.com.despesas.to;

import br.com.despesas.model.enums.Categoria;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public final class ValorPorDescricaoTO {

    private final String descricao;
    private final String valor;

    public ValorPorDescricaoTO(Categoria categoria, BigDecimal valor) {
        this.descricao = categoria.getDescricao();
        this.valor = valor.toPlainString();
    }

    public ValorPorDescricaoTO(boolean pago, BigDecimal valor) {
        this.descricao = pago ? "Pago" : "Pendente";
        this.valor = valor.toPlainString();
    }
}
