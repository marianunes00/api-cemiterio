package br.com.ifsertao.apicemiterio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
@Table(name="falecido")
public class Falecido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFalecido;

    @NotBlank
    private String nomeCompleto;

    @NotNull
    private LocalDate dataNascimento;

    @NotBlank
    @NotNull
    private String possuiCertidaoObito;

    @NotBlank
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "id_sepultura")
    private Sepultura sepultura;

    @NotNull
    private LocalDate dataFalecimento;

    @NotBlank
    private String familiarResponsavel;

    public Long getIdFalecido(){
        return idFalecido;
    }
    public void setIdFalecido(Long idFalecido){
        this.idFalecido = idFalecido;
    }

    public String getNomeCompleto(){
        return nomeCompleto;
    }
    public void setNomeCompleto(String nomeCompleto){
        this.nomeCompleto = nomeCompleto;
    }

    public LocalDate getDataNascimento(){
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento){
        this.dataNascimento = dataNascimento;
    }

    public String getPossuiCertidaoObito(){
        return possuiCertidaoObito;
    }
    public void setPossuiCertidaoObito(String possuiCertidaoObito){
        this.possuiCertidaoObito = possuiCertidaoObito;
    }

    public String getCpf(){
        return cpf;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    
    public Sepultura getSepultura() {
    return sepultura;
    }

    public void setSepultura(Sepultura sepultura) {
    this.sepultura = sepultura;
    }

    public LocalDate getDataFalecimento(){
        return dataFalecimento;
    }
    public void setDataFalecimento(LocalDate dataFalecimento){
        this.dataFalecimento = dataFalecimento;
    }

    public String getFamiliarResponsavel(){
        return familiarResponsavel;
    }
    public void setFamiliarResponsavel(String familiarResponsavel){
        this.familiarResponsavel = familiarResponsavel;
    }


}
