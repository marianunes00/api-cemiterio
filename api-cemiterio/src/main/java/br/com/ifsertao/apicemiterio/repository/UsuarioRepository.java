package br.com.ifsertao.apicemiterio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ifsertao.apicemiterio.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    //procure todos os usuarios cujo login tenha o texto informado e ignore letra maiuscula e  minuscula
    Page<Usuario> findByLoginContainingIgnoreCase(String login, Pageable pageable);

    Page<Usuario> findByPerfilContainingIgnoreCase(String perfil, Pageable pageable);

}
