package br.com.despesas.service;

import br.com.despesas.model.Despesa;
import br.com.despesas.model.enums.Categoria;
import br.com.despesas.to.DespesaBuscaTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class DespesaServiceTest {

    @Autowired
    private DespesaService despesaService;

    @Test
    public void salvar() {
        Despesa despesa = Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.TEN)
                .descricao("Conta de Luz")
                .categoria(Categoria.MORARIA)
                .paga(false)
                .build();
        despesaService.save(despesa);
        assertNotNull(despesa.getId());

        final List<Despesa> despesas = despesaService.findAll();
        assertEquals(1, despesas.size());
        assertThat(despesas, contains(allOf(
                hasProperty("id", is(despesa.getId())),
                hasProperty("descricao", is("Conta de Luz")))));
    }

    @Test
    public void alterar() {
        Despesa despesa = Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.TEN)
                .descricao("Conta de Luz")
                .categoria(Categoria.MORARIA)
                .paga(false)
                .build();
        despesaService.save(despesa);
        assertNotNull(despesa.getId());

        despesa.setDescricao("Conta de Energia");
        despesaService.save(despesa);

        final List<Despesa> despesas = despesaService.findAll();
        assertEquals(1, despesas.size());
        assertThat(despesas, contains(allOf(
                hasProperty("id", is(despesa.getId())),
                hasProperty("descricao", is("Conta de Energia")))));
    }

    @Test
    public void excluir() {
        Despesa despesa = Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.TEN)
                .descricao("Conta de Luz")
                .categoria(Categoria.MORARIA)
                .paga(false)
                .build();
        despesaService.save(despesa);

        assertEquals(1, despesaService.findAll().size());

        despesaService.delete(despesa.getId());

        assertEquals(0, despesaService.findAll().size());
    }

    @Test
    public void buscarTodos() {
        despesaService.save(Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.valueOf(70))
                .descricao("Conta de Luz")
                .categoria(Categoria.MORARIA)
                .paga(false)
                .build());

        despesaService.save(Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.TEN)
                .descricao("Conta de Agua")
                .categoria(Categoria.MORARIA)
                .paga(true)
                .build());

        despesaService.save(Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.valueOf(100))
                .descricao("Mercado")
                .categoria(Categoria.ALIMENTACAO)
                .paga(false)
                .build());

        final DespesaBuscaTO buscaTO = DespesaBuscaTO.builder()
                .quantidade(10)
                .build();

        final Page<Despesa> despesaPage = despesaService.findAll(buscaTO);
        assertEquals(3, despesaPage.getTotalElements());
        assertThat(despesaPage.getContent(), contains(
                allOf(hasProperty("descricao",          is("Mercado")),
                        hasProperty("valor",        is(BigDecimal.valueOf(100))),
                        hasProperty("categoria", is(Categoria.ALIMENTACAO))),
                allOf(hasProperty("descricao",          is("Conta de Agua")),
                        hasProperty("valor",        is(BigDecimal.TEN)),
                        hasProperty("categoria", is(Categoria.MORARIA))),
                allOf(hasProperty("descricao",          is("Conta de Luz")),
                    hasProperty("valor",        is(BigDecimal.valueOf(70))),
                    hasProperty("categoria", is(Categoria.MORARIA))))
              );
    }

    @Test
    public void buscarPorData() {
        despesaService.save(Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.valueOf(70))
                .descricao("Conta de Luz")
                .categoria(Categoria.MORARIA)
                .paga(false)
                .build());

        despesaService.save(Despesa.builder()
                .data(LocalDate.now().plusDays(10))
                .valor(BigDecimal.TEN)
                .descricao("Conta de Agua")
                .categoria(Categoria.MORARIA)
                .paga(true)
                .build());

        final DespesaBuscaTO buscaTO = DespesaBuscaTO.builder()
                .quantidade(10)
                .dataInicial(LocalDate.now())
                .dataFinal(LocalDate.now())
                .build();

        final Page<Despesa> despesaPage = despesaService.findAll(buscaTO);
        assertEquals(1, despesaPage.getTotalElements());
        assertThat(despesaPage.getContent(), contains(
                allOf(hasProperty("descricao",          is("Conta de Luz")),
                        hasProperty("valor",        is(BigDecimal.valueOf(70))),
                        hasProperty("categoria", is(Categoria.MORARIA))))
        );
    }

    @Test
    public void buscarPorDescricao() {
        despesaService.save(Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.valueOf(70))
                .descricao("Conta de Luz")
                .categoria(Categoria.MORARIA)
                .paga(false)
                .build());

        despesaService.save(Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.TEN)
                .descricao("Conta de Agua")
                .categoria(Categoria.MORARIA)
                .paga(true)
                .build());

        despesaService.save(Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.valueOf(100))
                .descricao("Mercado")
                .categoria(Categoria.ALIMENTACAO)
                .paga(false)
                .build());

        final DespesaBuscaTO buscaTO = DespesaBuscaTO.builder()
                .quantidade(10)
                .descricao("Conta")
                .build();

        final Page<Despesa> despesaPage = despesaService.findAll(buscaTO);
        assertEquals(2, despesaPage.getTotalElements());
        assertThat(despesaPage.getContent(), contains(
                allOf(hasProperty("descricao",          is("Conta de Agua")),
                        hasProperty("valor",        is(BigDecimal.TEN)),
                        hasProperty("categoria", is(Categoria.MORARIA))),
                allOf(hasProperty("descricao",          is("Conta de Luz")),
                        hasProperty("valor",        is(BigDecimal.valueOf(70))),
                        hasProperty("categoria", is(Categoria.MORARIA))))
        );
    }

    @Test
    public void buscarPorId() {
        Despesa despesa = Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.TEN)
                .descricao("Conta de Luz")
                .categoria(Categoria.MORARIA)
                .paga(false)
                .build();
        despesaService.save(despesa);

        final Despesa depesaPorId = despesaService.findById(despesa.getId());
        assertThat(depesaPorId, allOf(
                hasProperty("id", is(despesa.getId())),
                hasProperty("descricao", is("Conta de Luz"))));
    }

    @Test
    public void findTotalByCategoria() {
        despesaService.save(Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.valueOf(70))
                .descricao("Conta de Luz")
                .categoria(Categoria.MORARIA)
                .paga(false)
                .build());

        despesaService.save(Despesa.builder()
            .data(LocalDate.now())
            .valor(BigDecimal.TEN)
            .descricao("Conta de Agua")
            .categoria(Categoria.MORARIA)
            .paga(true)
            .build());

        despesaService.save(Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.valueOf(100))
                .descricao("Mercado")
                .categoria(Categoria.ALIMENTACAO)
                .paga(false)
                .build());


        final Map<Categoria, BigDecimal> totalMesAtualByCategoria = despesaService.findTotalMesAtualByCategoria();
        assertEquals(BigDecimal.valueOf(80), totalMesAtualByCategoria.get(Categoria.MORARIA));
        assertEquals(BigDecimal.valueOf(100), totalMesAtualByCategoria.get(Categoria.ALIMENTACAO));
    }

    @Test
    public void findTotalAPagarMesAtual() {
        despesaService.save(Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.valueOf(70))
                .descricao("Conta de Luz")
                .categoria(Categoria.MORARIA)
                .paga(false)
                .build());

        despesaService.save(Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.TEN)
                .descricao("Conta de Agua")
                .categoria(Categoria.MORARIA)
                .paga(true)
                .build());

        despesaService.save(Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.valueOf(100))
                .descricao("Mercado")
                .categoria(Categoria.ALIMENTACAO)
                .paga(false)
                .build());


        final BigDecimal totalAPagarMesAtual = despesaService.findTotalAPagarMesAtual();
        assertEquals(BigDecimal.valueOf(170), totalAPagarMesAtual);
    }
}
