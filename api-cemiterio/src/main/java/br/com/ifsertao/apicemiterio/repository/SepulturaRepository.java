package br.com.ifsertao.apicemiterio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ifsertao.apicemiterio.entity.Sepultura;

public interface SepulturaRepository extends JpaRepository<Sepultura, Long> {
    // Busca sepulturas pelo status (DISPONIVEL ou OCUPADA)
    Page<Sepultura> findByStatusSepulturaContainingIgnoreCase(String status, Pageable pageable);

    // Busca sepulturas pelo lote
    Page<Sepultura> findByLoteContainingIgnoreCase(String lote, Pageable pageable);

    // Busca sepulturas pelo tipo
    Page<Sepultura> findByTipoSepulturaContainingIgnoreCase(String tipo, Pageable pageable);






}
