package br.com.ifsertao.apicemiterio.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


//esse entity diz que é uma tabela 
@Entity
//define o nome da tabela
@Table(name="sepultura")
public class Sepultura {
    //mistura banco de dados com java
    //o id identifica que é uma chave primaria e o generated diz que vai ser preenchido automaticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSepultura;

    @JsonIgnore
    @OneToMany(mappedBy = "sepultura")
    private List<Falecido> falecidos;

    @JsonIgnore
    @OneToMany(mappedBy = "sepultura")
    private List<Servico> servicos;

    //esse notBlank diz que é um campo de preenchimento obrigatorio
    @NotBlank
    private String lote;
    
    @NotBlank
    private String tipoSepultura;

    @NotBlank
    private String statusSepultura;

    @NotBlank
    private String familiarResponsavel;

    @NotNull
    private LocalDate dataCriacao;

    public Long getIdSepultura(){
        return idSepultura;
    }
    public void setIdSepultura(Long idSepultura){
        this.idSepultura = idSepultura;
    }


    public List<Falecido> getFalecidos() {
        return falecidos;
    }

    public void setFalecidos(List<Falecido> falecidos) {
        this.falecidos = falecidos;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public String getLote(){
        return lote;
    }
    
    public void setLote(String lote){
        this.lote = lote;
    }

    public String getTipoSepultura(){
        return tipoSepultura;
    }

    public void setTipoSepultura(String tipoSepultura){
        this.tipoSepultura = tipoSepultura;
    }

    public String getStatusSepultura(){
        return statusSepultura;
    }

    public void setStatusSepultura(String statusSepultura){
        this.statusSepultura = statusSepultura;
    }

    public String getFamiliarResponsavel(){
        return familiarResponsavel;
    }

    public void setFamiliarResponsavel(String familiarResponsavel){
        this.familiarResponsavel = familiarResponsavel;
    }

    public LocalDate getDataCriacao(){
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao){
        this.dataCriacao = dataCriacao;
    }

    
    
}
