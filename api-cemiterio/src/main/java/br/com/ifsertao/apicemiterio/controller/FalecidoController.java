package br.com.ifsertao.apicemiterio.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.ifsertao.apicemiterio.service.FalecidoService;
import jakarta.validation.Valid;
import br.com.ifsertao.apicemiterio.entity.Falecido;
import br.com.ifsertao.apicemiterio.repository.SepulturaRepository;

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
    public Page<Falecido> listarTodos(@RequestParam(defaultValue="0") int page,
        @RequestParam(defaultValue="10") int size){
            Pageable pageable = PageRequest.of(page, size);
        return service.listarTodos(pageable);
    }

    @GetMapping("/{id}")
    public Falecido buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    //o valid serve para verificar antes de salvar e faz parte do bean validation
    @PostMapping
    public Falecido salvar(@Valid @RequestBody Falecido falecido){
        return service.salvar(falecido);
    }

    @PutMapping("/{id}")
    public Falecido atualizar(@PathVariable Long id, @Valid @RequestBody Falecido falecido){
        return service.atualizar(id, falecido);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }
    
    
}
