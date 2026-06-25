package br.com.ifsertao.apicemiterio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ifsertao.apicemiterio.entity.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

}
