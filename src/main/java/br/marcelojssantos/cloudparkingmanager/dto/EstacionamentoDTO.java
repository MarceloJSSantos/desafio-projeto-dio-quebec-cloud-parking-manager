package br.marcelojssantos.cloudparkingmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstacionamentoDTO {
    private String id;
    private String licenca;
    private String estado;
    private String modelo;
    private String cor;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataEntrada;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataSaida;
    private Double conta;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLicenca() {
        return licenca;
    }

    public void setLicenca(String licenca) {
        this.licenca = licenca;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Double getConta() {
        return conta;
    }

    public void setConta(Double conta) {
        this.conta = conta;
    }
}
