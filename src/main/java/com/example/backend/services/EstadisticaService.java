package com.example.backend.services;


import java.util.List;
import java.util.Map;

public interface EstadisticaService {
    public Map<String, Object> calcularEstadisticas(List<Double> data);
    public double calcularMedia(List<Double> data);
    public double calcularMediana(List<Double> data);
    public List<Double> calcularModa(List<Double> data);
    public double calcularVarianza(List<Double> data);
    public double calcularDesviacionEstandar(List<Double> data);
    public long calcularPermutacion(int n, int r);
    public long calcularCombinatoria(int n, int r);
    public double calcularCovarianza(List<Double> data1,List<Double> data2);
    public double calcularCorrelacion(List<Double> data1,List<Double> data2);
    public double calcularCoeficienteCorrelacion(List<Double> data1,List<Double> data2);
    Map<String, Object> calcularEstadisticasDoble(List<Double> data1, List<Double> data2);
}
