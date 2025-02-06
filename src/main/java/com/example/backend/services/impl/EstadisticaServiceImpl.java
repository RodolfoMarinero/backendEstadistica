package com.example.backend.services.impl;

import com.example.backend.services.EstadisticaService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EstadisticaServiceImpl implements EstadisticaService {

    @Override
    public Map<String, Object> calcularEstadisticas(List<Double> data) {
        Map<String, Object> resultados = new HashMap<>();
        resultados.put("media", calcularMedia(data));
        resultados.put("mediana", calcularMediana(data));
        resultados.put("moda", calcularModa(data));
        resultados.put("desviacionEstandar", calcularDesviacionEstandar(data));
        resultados.put("varianza", calcularVarianza(data));
        return resultados;
    }
    @Override
    public double calcularMedia(List<Double> data) {
        return data.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    @Override
    public double calcularMediana(List<Double> data) {
        List<Double> sorted = data.stream().sorted().collect(Collectors.toList());
        int size = sorted.size();
        if (size % 2 == 0) {
            return (sorted.get(size / 2 - 1) + sorted.get(size / 2)) / 2.0;
        } else {
            return sorted.get(size / 2);
        }
    }

    @Override
    public List<Double> calcularModa(List<Double> data) {
        Map<Double, Long> frequency = data.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        long maxCount = Collections.max(frequency.values());
        return frequency.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public double calcularDesviacionEstandar(List<Double> data) {
        double mean = calcularMedia(data);
        double variance = data.stream()
                .mapToDouble(x -> Math.pow(x - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    @Override
    public double calcularVarianza(List<Double> data) {
        double mean = calcularMedia(data);
        return data.stream()
                .mapToDouble(x -> Math.pow(x - mean, 2))
                .average()
                .orElse(0.0);
    }

    @Override
    public long calcularPermutacion(int n, int r) {
        return factorial(n) / factorial(n - r);
    }

    @Override
    public long calcularCombinatoria(int n, int r) {
        return factorial(n) / (factorial(r) * factorial(n - r));
    }


    private long factorial(int num) {
        if (num == 0 || num == 1) return 1;
        long result = 1;
        for (int i = 2; i <= num; i++) result *= i;
        return result;
    }

    @Override
    public double calcularCovarianza(List<Double> data1, List<Double> data2) {
        if (data1.size() != data2.size() || data1.isEmpty()) {
            throw new IllegalArgumentException("Las listas deben tener el mismo tamaño y no estar vacías");
        }
        double mediaX = calcularMedia(data1);
        double mediaY = calcularMedia(data2);
        return IntStream.range(0, data1.size())
                .mapToDouble(i -> (data1.get(i) - mediaX) * (data2.get(i) - mediaY))
                .average()
                .orElse(0.0);
    }

    @Override
    public double calcularCorrelacion(List<Double> data1, List<Double> data2) {
        double covarianza = calcularCovarianza(data1, data2);
        double desviacionX = calcularDesviacionEstandar(data1);
        double desviacionY = calcularDesviacionEstandar(data2);
        return (desviacionX == 0 || desviacionY == 0) ? 0 : covarianza / (desviacionX * desviacionY);
    }

    @Override
    public double calcularCoeficienteCorrelacion(List<Double> data1, List<Double> data2) {
        return calcularCorrelacion(data1, data2);
    }
}
