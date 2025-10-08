package br.edu.ifpb.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_VIAGEM")
public class Viagem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String origem;
    private String destino;

    private LocalDateTime dataHoraSaida;

    private LocalDateTime dataHoraChegada;

    @Enumerated(EnumType.STRING)
    private StatusViagem status;

    private Double distanciaKm;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Motorista motorista;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDateTime getDataHoraSaida() {
        return dataHoraSaida;
    }

    public void setDataHoraSaida(LocalDateTime dataHoraSaida) {
        this.dataHoraSaida = dataHoraSaida;
    }

    public LocalDateTime getDataHoraChegada() {
        return dataHoraChegada;
    }

    public void setDataHoraChegada(LocalDateTime dataHoraChegada) {
        this.dataHoraChegada = dataHoraChegada;
    }

    public StatusViagem getStatus() {
        return status;
    }

    public void setStatus(StatusViagem status) {
        this.status = status;
    }

    public Double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(Double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    // Enum para status
    public enum StatusViagem {
        AGENDADA,
        EM_ANDAMENTO,
        CONCLUIDA,
        CANCELADA
    }
}
