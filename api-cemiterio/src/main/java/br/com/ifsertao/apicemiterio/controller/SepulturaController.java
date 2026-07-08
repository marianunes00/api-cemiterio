package br.com.ifsertao.apicemiterio.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.ifsertao.apicemiterio.service.SepulturaService;
import br.com.ifsertao.apicemiterio.entity.Sepultura;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


// Controller responsável por receber as requisições HTTP
// e encaminhá-las para a camada Service.


//quando eu coloco isso digo que essa classe vai responder requisições http
@RestController
//já essa define qual é o endereço base
//tudo que tiver aqui dentro vai ficar assim: localhost:8080/sepulturas
@RequestMapping("/sepulturas")
public class SepulturaController {

    private final SepulturaService service;

    public SepulturaController(SepulturaService service){
        this.service = service;
    }

    //primeiro endpoint
    @GetMapping
    public List<Sepultura> listarTodos(){
        return service.listarTodos();
    }

    //quando alguem pesquisar a sepultura por id, vai aparecer sepultura e aquela
    //url e depois o id da sepultura que deseja
    @GetMapping("/{id}")
    public Sepultura buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    //essa anotação indica que responde a uma requisição post
    //esse requestbody diz: pegue o json enviao pelo cliente e transforme em um objeto
    //do tipo sepultura, eu envio os dados e o spring tranforma em objeto
    @PostMapping
    public Sepultura salvar(@RequestBody Sepultura sepultura){
        return service.salvar(sepultura);
    }
    //recebe o id e a sepultura e chama o metodo lá do service, realiza a atualização salvando os dados modificados
    @PutMapping("/{id}")
    public Sepultura atualizar(@PathVariable Long id, @RequestBody Sepultura sepultura){
        return service.atualizar(id, sepultura);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }
    
}
