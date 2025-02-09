package com.example.backend.model;

import java.util.List;

public class EstadisticasDTO {
    private Double media;
    private Double mediana;
    private List<Double> moda;
    private Double desviacionEstandar;
    private Double varianza;

    // Constructores, getters y setters

    public EstadisticasDTO() {
    }

    public EstadisticasDTO(Double media, Double mediana, List<Double> moda, Double desviacionEstandar, Double varianza) {
        this.media = media;
        this.mediana = mediana;
        this.moda = moda;
        this.desviacionEstandar = desviacionEstandar;
        this.varianza = varianza;
    }

    public Double getMedia() {
        return media;
    }

    public void setMedia(Double media) {
        this.media = media;
    }

    public Double getMediana() {
        return mediana;
    }

    public void setMediana(Double mediana) {
        this.mediana = mediana;
    }

    public List<Double> getModa() {
        return moda;
    }

    public void setModa(List<Double> moda) {
        this.moda = moda;
    }

    public Double getDesviacionEstandar() {
        return desviacionEstandar;
    }

    public void setDesviacionEstandar(Double desviacionEstandar) {
        this.desviacionEstandar = desviacionEstandar;
    }

    public Double getVarianza() {
        return varianza;
    }

    public void setVarianza(Double varianza) {
        this.varianza = varianza;
    }
}
