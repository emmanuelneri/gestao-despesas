package br.com.despesas.service;

import br.com.despesas.model.Despesa;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
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
        Despesa despesa = new Despesa();
        despesaService.salvar(despesa);
        assertNotNull(despesa.getId());

        final List<Despesa> despesas = despesaService.buscarTodos();
        assertEquals(1, despesas.size());
        assertThat(despesas, contains(hasProperty("id", is(despesa.getId()))));
    }
}
