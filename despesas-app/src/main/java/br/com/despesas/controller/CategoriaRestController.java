package br.com.despesas.controller;

import br.com.despesas.model.enums.Categoria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/categorias")
public class CategoriaRestController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll() {
        return ResponseEntity.ok(Categoria.getCategoriasTOSortedByDescricao());
    }

}
