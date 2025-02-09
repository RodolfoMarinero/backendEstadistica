package com.example.backend.services.impl;

import com.example.backend.model.EstadisticasDTO;
import com.example.backend.model.EstadisticasDobleDTO;
import com.example.backend.model.MuestraDTO;
import com.example.backend.services.EstadisticaService;
import com.example.backend.utils.CsvReader;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EstadisticaServiceImpl implements EstadisticaService {


    @Override
    public EstadisticasDTO calcularEstadisticas(MuestraDTO muestra) {
        List<Double> data = obtenerDatos(muestra);
        EstadisticasDTO dto = new EstadisticasDTO();
        dto.setMedia(calcularMedia(data));
        dto.setMediana(calcularMediana(data));
        dto.setModa(calcularModa(data));
        dto.setDesviacionEstandar(calcularDesviacionEstandar(data));
        dto.setVarianza(calcularVarianza(data));
        return dto;
    }

    @Override
    public EstadisticasDobleDTO calcularEstadisticasDoble(MuestraDTO muestra1, MuestraDTO muestra2) {
        List<Double> data1 = obtenerDatos(muestra1);
        List<Double> data2 = obtenerDatos(muestra2);
        EstadisticasDobleDTO dto = new EstadisticasDobleDTO();
        dto.setCovarianza(calcularCovarianza(data1, data2));
        dto.setCorrelacion(calcularCorrelacion(data1, data2));
        dto.setCoeficienteCorrelacion(calcularCoeficienteCorrelacion(data1, data2));
        return dto;
    }



    private List<Double> obtenerDatos(MuestraDTO muestra) {
        if (muestra.getFile() != null && !muestra.getFile().isEmpty()) {
            try {
                return CsvReader.leerCSV(muestra.getFile());
            } catch (Exception e) {
                throw new RuntimeException("Error al leer el archivo CSV", e);
            }
        } else if (muestra.getDatos() != null && !muestra.getDatos().trim().isEmpty()) {
            return parseDataString(muestra.getDatos());
        }
        throw new IllegalArgumentException("No se recibieron datos ni archivo CSV");
    }

    private List<Double> parseDataString(String dataString) {
        // Eliminar espacios en blanco y quitar corchetes inicial y final
        dataString = dataString.trim();
        if (dataString.startsWith("[") && dataString.endsWith("]")) {
            dataString = dataString.substring(1, dataString.length() - 1);
        }

        // Si la cadena queda vacía, retornar una lista vacía
        if (dataString.isEmpty()) {
            return Collections.emptyList();
        }

        // Dividir la cadena por comas y mapear cada elemento a Double
        try {
            return Arrays.stream(dataString.split(","))
                    .map(String::trim)
                    .map(Double::parseDouble)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato inválido en los datos: " + dataString, e);
        }
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
        return calcularCorrelacion(data1, data2)/calcularCovarianza(data1, data2);
    }
}
