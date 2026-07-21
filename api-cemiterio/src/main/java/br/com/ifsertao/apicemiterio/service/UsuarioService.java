package br.com.ifsertao.apicemiterio.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ifsertao.apicemiterio.entity.Servico;
import br.com.ifsertao.apicemiterio.entity.Usuario;
import br.com.ifsertao.apicemiterio.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    //cria a variavel do repositorio de usuario
    private final UsuarioRepository reposity;

    //inicializa pelo construtor
    public UsuarioService(UsuarioRepository repository){
        this.reposity = repository;
    }

    public Usuario salvar(Usuario usuario){
        //regra de negocio que verifica se o campo de login está preenchido
        if(usuario.getLogin()==null || usuario.getLogin().isBlank()){
            throw new RuntimeException("O login é obrigatorio");
        }
        return reposity.save(usuario);
    }

    // Lista os usuários utilizando paginação.
    public Page<Usuario> listarTodos(Pageable pageable){
        return reposity.findAll(pageable);
    }

    //busca por id e se não encontrar retorna a excessao
    public Usuario buscarPorId(Long id){
        return reposity.findById(id).orElseThrow(()-> new RuntimeException("Usuario não encontrado!"));
    }

    //buscar por id
    public Usuario atualizar(Long id, Usuario usuario){
        buscarPorId(id);
        usuario.setIdUsuario(id);
        return reposity.save(usuario);
    }

    public void deletar(Long id){
        buscarPorId(id);
        reposity.deleteById(id);
    }

    //paginação, isso faz com que o spring devolva pagina com registro
    public Page<Usuario> buscarPorLogin(String login, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return reposity.findByLoginContainingIgnoreCase(login, pageable);
    }

    //faz a mesma coisa do outro metodo, só que agora busca por perfil, cria a pagina pra retornar só o desejado
    public Page<Usuario> buscarPorPerfil(String perfil, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return reposity.findByPerfilContainingIgnoreCase(perfil, pageable);
    }

     



}
