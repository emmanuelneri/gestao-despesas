package br.com.despesas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/despesas")
public class DespesaController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity find() {
        // TODO
        return ResponseEntity.ok("Hello");
    }
}
