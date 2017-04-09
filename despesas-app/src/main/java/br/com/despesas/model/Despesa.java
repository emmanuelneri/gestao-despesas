package br.com.despesas.model;

import br.com.despesas.exception.BusinessException;
import br.com.despesas.model.enums.Categoria;
import br.com.despesas.model.enums.StatusDespesa;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "despesa_uk", columnNames = {"data", "descricao", "categoria"}))
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

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusDespesa status = StatusDespesa.PENDENTE;

    public void validarValor() {
        if(valor == null || valor.compareTo(BigDecimal.ZERO) == 0) {
            throw new BusinessException("Valor da despesa deve ser maior que zero");
        }
    }
}
