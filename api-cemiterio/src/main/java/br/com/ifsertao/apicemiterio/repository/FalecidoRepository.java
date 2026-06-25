package br.com.ifsertao.apicemiterio.repository;

import org.springframework.data.jpa.repository.JpaRepository; //Importa o JpaRepository do Spring Data JPA

import br.com.ifsertao.apicemiterio.entity.Falecido; //importa a entidade de Falecido.

//A partir do JpaRepository vamos acessar as tabelas do banco e conseguir usar metódos como buscar por id, salvar, sem precisar usar sql.
//Metódos:// save(), findAll(), findById(), deleteById() e outros.
public interface FalecidoRepository extends JpaRepository<Falecido, Long> {



}