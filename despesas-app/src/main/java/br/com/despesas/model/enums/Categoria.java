package br.com.despesas.model.enums;

import br.com.despesas.to.CategoriaTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Categoria {

    ALIMENTACAO("Alimentação"),
    EDUCACAO("Educação"),
    SAUDE("Saúde"),
    TRANSPORTE("Transporte"),
    MORARIA("Moradia"),
    OUTROS("Outros"), ;

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @JsonIgnore
    public static List<CategoriaTO> getCategoriasTOSortedByDescricao() {
        return Stream.of(Categoria.values())
                .map(c -> new CategoriaTO(c.name(), c.getDescricao()))
                .sorted(Comparator.comparing(CategoriaTO::getDescricao))
                .collect(Collectors.toList());
    }
}
