package br.com.despesas.to;

import br.com.despesas.model.QDespesa;
import br.com.despesas.model.enums.Categoria;
import br.com.despesas.model.enums.StatusDespesa;
import com.google.common.base.Strings;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
@AllArgsConstructor
public class DespesaBuscaTO {

    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private String descricao;
    private Categoria categoria;
    private StatusDespesa status;

    private int pagina;
    private int quantidade;

    public Predicate toPredicate() {
        final QDespesa qDespesa = QDespesa.despesa;
        final BooleanBuilder predicate = new BooleanBuilder();

        if(dataInicial != null) {
            predicate.and(qDespesa.data.goe(dataInicial));
        }

        if(dataFinal != null) {
            predicate.and(qDespesa.data.loe(dataFinal));
        }

        if(!Strings.isNullOrEmpty(descricao)) {
            predicate.and(qDespesa.descricao.contains(descricao));
        }

        if(categoria != null) {
            predicate.and(qDespesa.categoria.eq(categoria));
        }

        if(status != null) {
            predicate.and(qDespesa.status.eq(status));
        }

        return predicate;
    }

}
