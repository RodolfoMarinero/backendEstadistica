package com.example.backend.services;


import java.util.List;

public interface EstadisticaService {
    public double calcularMedia(List<Double> data);
    public double calcularMediana(List<Double> data);
    public List<Double> calcularModa(List<Double> data);
    public double calcularVarianza(List<Double> data);
    public double calcularDesviacionEstandar(List<Double> data);
    public long calcularPermutacion(int n, int r);
    public long calcularCombinatoria(int n, int r);
}
