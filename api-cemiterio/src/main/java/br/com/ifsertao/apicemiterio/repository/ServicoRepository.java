package br.com.ifsertao.apicemiterio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ifsertao.apicemiterio.entity.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ServicoRepository extends JpaRepository<Servico, Long> {

    //estou dizendo para que procure todos os servicos cunjo tipo tenha o texto informado e que ignore as letras maisuculas e minusculas
    Page<Servico> findByTipoServicoContainingIgnoreCase(String tipoServico, Pageable pageable);

    Page<Servico> findByStatusServicoContainingIgnoreCase(String statusServico, Pageable pageable);
}
