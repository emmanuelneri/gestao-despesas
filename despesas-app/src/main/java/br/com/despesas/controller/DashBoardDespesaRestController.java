package br.com.despesas.controller;

import br.com.despesas.model.enums.Categoria;
import br.com.despesas.service.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping(path = "/dashboard")
public class DashBoardDespesaRestController {

    @Autowired
    private DespesaService despesaService;

    @RequestMapping(method = RequestMethod.GET, value = "/total/categoria")
    public ResponseEntity findTotalMesAtualByCategoria() {
        final Map<Categoria, BigDecimal> totalMesAtualByCategoria = despesaService.findTotalMesAtualByCategoria();
        return ResponseEntity.ok(totalMesAtualByCategoria);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/total/pagar")
    public ResponseEntity findTotalAPagarMesAtual() {
        final BigDecimal totalAPagar = despesaService.findTotalAPagarMesAtual();
        return ResponseEntity.ok(totalAPagar);
    }

}
