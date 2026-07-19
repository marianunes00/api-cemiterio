package br.com.ifsertao.apicemiterio.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import br.com.ifsertao.apicemiterio.entity.Usuario;
import br.com.ifsertao.apicemiterio.service.UsuarioService;
import jakarta.validation.Valid;

@RestController // Indica que esta classe é um Controller REST, responsável por receber requisições HTTP.
@RequestMapping("/usuarios") // Define a rota base da API para as operações relacionadas aos usuários.
public class UsuarioController {

    private final UsuarioService service;

    // Injeta o UsuarioService, responsável por executar as regras de negócio.
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    /*Todos os endpoints encaminham a requisição para a camada Service, 
    onde estão implementadas as regras de negócio.*/

    // Endpoint GET: lista todos os usuários cadastrados no banco de dados.
    @GetMapping
    public Page<Usuario> listarTodos( @RequestParam(defaultValue="0") int page,
        @RequestParam(defaultValue="10") int size) {
            // page: número da página (começa em 0),size: quantidade de registros por página

            /*Cria um objeto Pageable contendo o número da página e a quantidade
            de registros que serão retornados nessa página.*/
             Pageable pageable = PageRequest.of(page,size);
        return service.listarTodos(pageable);
    }

    // Endpoint GET: busca um usuário pelo ID informado na URL.
    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    //essa anotação indica que responde a uma requisição endpoint post, onde é realizado o cadastro de Usuário no BD
    @PostMapping
    public Usuario salvar( @Valid @RequestBody Usuario usuario) {
        return service.salvar(usuario);
    }

    //Put atualiza o usuario com os dados modificados utilizando o id da url.
    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        return service.atualizar(id, usuario);
    }

    //Put atualiza o usuario com os dados modificados utilizando o id da url.
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

}