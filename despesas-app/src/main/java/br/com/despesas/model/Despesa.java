package br.com.despesas.model;

import br.com.despesas.exception.BusinessException;
import br.com.despesas.model.enums.Categoria;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "despesa_uk", columnNames = {"data", "descricao", "categoria"}))
@NamedQueries(value = {
        @NamedQuery(name="Despesa.findTotalByDatas", query = "select sum(d.valor) from Despesa d where d.paga = ?1 and d.data between ?2 and ?3")
})
@Builder
@EqualsAndHashCode(of = {"data", "descricao", "categoria"})
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "despesa_id_seq")
    @SequenceGenerator(name = "despesa_id_seq", sequenceName = "despesa_id_seq", allocationSize = 1)
    private Long id;

    @NotNull(message = "Data é obrigatório")
    private LocalDate data;

    @NotNull(message = "Valor é obrigatório")
    private BigDecimal valor;

    @NotBlank(message = "Descrição é obrigatório")
    @Size(max = 200, message = "Tamanho máximo da descrição é de {max} caracteres")
    private String descricao;

    @NotNull(message = "Categoria é obrigatório")
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    private boolean paga = false;

    public void validarValor() {
        if(valor == null || valor.compareTo(BigDecimal.ZERO) == 0) {
            throw new BusinessException("Valor da despesa deve ser maior que zero");
        }
    }

    public void pagar() {
        if (paga) {
            throw new BusinessException("A despesa já está paga");
        }
        this.paga = true;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public LocalDate getDataFormatada() {
        return data;
    }

    public String getDescricaCategoria() {
        return categoria.getDescricao();
    }
}
