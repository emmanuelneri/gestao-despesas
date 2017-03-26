package br.com.despesas.model.enums;

import br.com.despesas.to.StatusDespesaTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusDespesa {

    PENDENTE("Pendente", 1),
    PAGO("Pago", 2);

    private final String descricao;
    private final int ordem;

    StatusDespesa(String descricao, int ordem) {
        this.descricao = descricao;
        this.ordem = ordem;
    }

    public String getStatus() {
        return this.name();
    }

    public String getDescricao() {
        return descricao;
    }

    public int getOrdem() {
        return ordem;
    }

    @JsonIgnore
    public static List<StatusDespesaTO> getStatusTOSortedByOrdem() {
        return Stream.of(StatusDespesa.values())
                .sorted(Comparator.comparing(StatusDespesa::getOrdem))
                .map(s -> new StatusDespesaTO(s.name(), s.getDescricao()))
                .collect(Collectors.toList());
    }
}
