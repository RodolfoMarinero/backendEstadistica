package com.example.backend.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class CsvReader {
    public static List<Double> leerCSV(MultipartFile file) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        return reader.lines()
                .skip(1) // Ignorar encabezados
                .flatMap(line -> List.of(line.split(",")).stream()) // Separar por comas
                .map(String::trim) // Eliminar espacios
                .map(Double::parseDouble) // Convertir a Double
                .collect(Collectors.toList());
    }
}
