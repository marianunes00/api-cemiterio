package br.com.ifsertao.apicemiterio.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name="servico")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServico;

    @NotNull
    private LocalDate dataServico;

    @NotEmpty
    private String tipoServico;

    @NotEmpty
    private String statusServico;

    @ManyToOne
    @JoinColumn(name = "id_sepultura")
    private Sepultura sepultura;

    @NotEmpty
    private String notificacaoServico;

    public Long getIdServico() {
        return idServico;
    }
    
    public void setIdServico(Long idServico) {
        this.idServico = idServico;
    }

    public LocalDate getDataServico() {
        return dataServico;
    }

    public void setDataServico(LocalDate dataServico) {
        this.dataServico = dataServico;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public String getStatusServico() {
        return statusServico;
    }

    public void setStatusServico(String statusServico) {
        this.statusServico = statusServico;
    }

    public Sepultura getSepultura() {
        return sepultura;
    }

    public void setSepultura(Sepultura sepultura) {
        this.sepultura = sepultura;
    }

    public String getNotificacaoServico() {
        return notificacaoServico;
    }

    public void setNotificacaoServico(String notificacaoServico) {
        this.notificacaoServico = notificacaoServico;
    }

}
