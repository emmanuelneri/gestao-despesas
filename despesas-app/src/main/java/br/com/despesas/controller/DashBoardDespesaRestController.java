package br.com.despesas.controller;

import br.com.despesas.model.Despesa;
import br.com.despesas.model.enums.Categoria;
import br.com.despesas.service.DespesaService;
import br.com.despesas.to.ValorPorDescricaoTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/dashboard")
public class DashBoardDespesaRestController {

    @Autowired
    private DespesaService despesaService;

    @RequestMapping(method = RequestMethod.GET, value = "/total/categoria")
    public ResponseEntity findTotalMesAtualByCategoria() {
        final Map<Categoria, BigDecimal> totalMesAtualByCategoria = despesaService.findTotalMesAtualByCategoria();
        return ResponseEntity.ok(totalMesAtualByCategoria.entrySet().stream()
                .map(entry -> new ValorPorDescricaoTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet()));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/total/status")
    public ResponseEntity findTotalMesAtualByPago() {
        final Map<Boolean, BigDecimal> totalByPago = despesaService.findTotalByPago();
        return ResponseEntity.ok(totalByPago.entrySet().stream()
                .map(entry -> new ValorPorDescricaoTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet()));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/total/pagar")
    public ResponseEntity findTotalAPagarMesAtual() {
        final BigDecimal totalAPagar = despesaService.findTotalAPagarMesAtual();
        return ResponseEntity.ok(totalAPagar);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/total/pago")
    public ResponseEntity findTotalPagoAtual() {
        final BigDecimal totalPago = despesaService.findTotalPagoMesAtual();
        return ResponseEntity.ok(totalPago);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/despesas/vencer")
    public ResponseEntity findDespesasAVencerMesAtual() {
        final List<Despesa> despesas = despesaService.findProximasCincoDespesasAVencer();
        return ResponseEntity.ok(despesas);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/despesas/atrasadas")
    public ResponseEntity findDespesaAtrasadas() {
        final List<Despesa> despesas = despesaService.findDespesaAtrasadas();
        return ResponseEntity.ok(despesas);
    }

}
