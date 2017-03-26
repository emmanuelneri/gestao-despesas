package br.com.despesas.controller;

import br.com.despesas.model.enums.Categoria;
import br.com.despesas.model.Despesa;
import br.com.despesas.service.DespesaService;
import br.com.despesas.to.DespesaBuscaTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DespesaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DespesaService despesaService;

    @Inject
    private ObjectMapper objectMapper;

    @Test
    public void buscarTodos() throws Exception {
        final Despesa contaDeLuz = Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.valueOf(70))
                .descricao("Conta de Luz")
                .categoria(Categoria.MORARIA)
                .build();

        final Despesa contaDeAgua = Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.TEN)
                .descricao("Conta de Agua")
                .categoria(Categoria.MORARIA)
                .build();

       final DespesaBuscaTO buscaTO = DespesaBuscaTO.builder()
                .quantidade(10)
                .build();

        final List<Despesa> despesas = Arrays.asList(contaDeLuz, contaDeAgua);
        final Page<Despesa> pageDespesa = new PageImpl<>(despesas,
                new PageRequest(buscaTO.getPagina(), buscaTO.getQuantidade()), 2L);

        given(despesaService.findAll(any(DespesaBuscaTO.class)))
                .willReturn(pageDespesa);

        mockMvc.perform(post("/despesas/busca")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(buscaTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(pageDespesa)));
    }

    @Test
    public void buscarPorId() throws Exception {
        final Despesa despesa = Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.valueOf(70))
                .descricao("Conta de Luz")
                .categoria(Categoria.MORARIA)
                .build();

        given(despesaService.findById(1L))
                .willReturn(despesa);

        mockMvc.perform(get("/despesas/" + 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(despesa)));
    }

    @Test
    public void salvar() throws Exception {
        final Despesa despesa = Despesa.builder()
                .data(LocalDate.now())
                .valor(BigDecimal.valueOf(70))
                .descricao("Conta de Luz")
                .categoria(Categoria.MORARIA)
                .build();

        given(despesaService.save(despesa))
                .willReturn(despesa);

        mockMvc.perform(post("/despesas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(despesa))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void excluir() throws Exception {
        mockMvc.perform(delete("/despesas/" + 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
