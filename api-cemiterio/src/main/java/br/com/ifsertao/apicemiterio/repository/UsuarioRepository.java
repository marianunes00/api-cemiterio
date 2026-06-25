package br.com.ifsertao.apicemiterio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ifsertao.apicemiterio.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
