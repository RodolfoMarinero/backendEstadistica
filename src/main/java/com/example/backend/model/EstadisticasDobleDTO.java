package com.example.backend.model;

public class EstadisticasDobleDTO {
    private Double covarianza;
    private Double correlacion;
    private Double coeficienteCorrelacion;

    // Constructores, getters y setters

    public EstadisticasDobleDTO() {
    }

    public EstadisticasDobleDTO(Double covarianza, Double correlacion, Double coeficienteCorrelacion) {
        this.covarianza = covarianza;
        this.correlacion = correlacion;
        this.coeficienteCorrelacion = coeficienteCorrelacion;
    }

    public Double getCovarianza() {
        return covarianza;
    }

    public void setCovarianza(Double covarianza) {
        this.covarianza = covarianza;
    }

    public Double getCorrelacion() {
        return correlacion;
    }

    public void setCorrelacion(Double correlacion) {
        this.correlacion = correlacion;
    }

    public Double getCoeficienteCorrelacion() {
        return coeficienteCorrelacion;
    }

    public void setCoeficienteCorrelacion(Double coeficienteCorrelacion) {
        this.coeficienteCorrelacion = coeficienteCorrelacion;
    }
}
