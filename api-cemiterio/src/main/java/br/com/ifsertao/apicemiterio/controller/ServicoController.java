package br.com.ifsertao.apicemiterio.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifsertao.apicemiterio.entity.Servico;
import br.com.ifsertao.apicemiterio.service.ServicoService;
import jakarta.validation.Valid;

@RestController 
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService service;

    public ServicoController(ServicoService service){
        this.service = service;
    }

    @GetMapping
    public Page<Servico> listarTodos(@RequestParam(defaultValue="0") int page,
        @RequestParam(defaultValue="10") int size){
        Pageable pageable = PageRequest.of(page,size);
        return service.listarTodos(pageable);
    }
    
    @GetMapping("/{id}")
    public Servico buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PostMapping
    public Servico salvar(@Valid @RequestBody Servico servico){
        return service.salvar(servico);
    }

    @PutMapping("/{id}")
    public Servico atualizar(@PathVariable Long id,@Valid @RequestBody Servico servico){
        return service.atualizar(id, servico);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }
    
}