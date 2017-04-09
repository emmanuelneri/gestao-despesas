package br.com.despesas.controller;

import br.com.despesas.model.Despesa;
import br.com.despesas.service.DespesaService;
import br.com.despesas.to.DespesaBuscaTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/despesas")
public class DespesaRestController {

    @Autowired
    private DespesaService despesaService;

    @RequestMapping(method = RequestMethod.POST, value = "/search")
    public ResponseEntity findAll(@RequestBody DespesaBuscaTO despesaBuscaTO) {
        final Page<Despesa> despesas = despesaService.findAll(despesaBuscaTO);
        return ResponseEntity.ok(despesas);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        final Despesa despesa = despesaService.findById(id);
        return ResponseEntity.ok(despesa);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody Despesa despesa) {
        despesaService.save(despesa);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        despesaService.delete(id);
        return ResponseEntity.ok().build();
    }
}
