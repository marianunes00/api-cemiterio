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
import org.springframework.http.ResponseEntity;

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
    
    //cria o endpoint que cria  arota de buscar por tipo em servico, e estabelece os parametro
    //quando alguem quiser buscar o de limpeza ele vai colcoar a limpeza como tipo
    @GetMapping("/buscar/tipo")
    public ResponseEntity<Page<Servico>> buscarPorTipo(
        @RequestParam String tipo,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size){
            return ResponseEntity.ok(service.buscarPorTipo(tipo, page, size));
        }
    
        //mesma coisa do outro, só que para buscar por status
    @GetMapping("/buscar/status")
    public ResponseEntity<Page<Servico>> buscarPorStatus(
        @RequestParam String status,
        @RequestParam(defaultValue = "0")  int page,
        @RequestParam(defaultValue = "10") int size){
            return ResponseEntity.ok(service.buscarPorStatus(status,page,size));
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