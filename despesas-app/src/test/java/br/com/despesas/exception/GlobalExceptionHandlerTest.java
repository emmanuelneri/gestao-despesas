package br.com.despesas.exception;

import br.com.despesas.model.Despesa;
import br.com.despesas.model.enums.Categoria;
import br.com.despesas.service.DespesaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DespesaService despesaService;

    @Inject
    private ObjectMapper objectMapper;

    @Test
    public void businessExceptionTest() throws Exception {
        final Despesa despesa = Despesa.builder()
                .descricao("Conta")
                .categoria(Categoria.ALIMENTACAO)
                .build();

        given(despesaService.save(despesa))
                .willThrow(new BusinessException("Erro de validação"));

        mockMvc.perform(post("/despesas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(despesa))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(
                        Collections.singleton(new ExceptionVO("Erro de validação")))));
    }

    @Test
    public void exceptionTest() throws Exception {
        final Despesa despesa = Despesa.builder()
                .descricao("Conta")
                .categoria(Categoria.ALIMENTACAO)
                .build();

        given(despesaService.save(despesa))
                .willThrow(new RuntimeException());

        mockMvc.perform(post("/despesas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(despesa))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(GlobalExceptionHandler.SISTEMA_INDISPONIVEL));
    }
}
