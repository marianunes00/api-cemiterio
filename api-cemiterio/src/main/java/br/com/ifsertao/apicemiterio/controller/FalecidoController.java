package br.com.ifsertao.apicemiterio.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.ifsertao.apicemiterio.service.FalecidoService;
import br.com.ifsertao.apicemiterio.entity.Falecido;
import br.com.ifsertao.apicemiterio.repository.SepulturaRepository;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/falecidos")
public class FalecidoController {

    private final FalecidoService service;

    public FalecidoController(FalecidoService service){
        this.service = service;
    }

    @GetMapping
    public List<Falecido> listarTodos(){
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Falecido buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }


    @PostMapping
    public Falecido salvar(@RequestBody Falecido falecido){
        return service.salvar(falecido);
    }

    @PutMapping("/{id}")
    public Falecido atualizar(@PathVariable Long id, @RequestBody Falecido falecido){
        return service.atualizar(id, falecido);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }
    
    
}
