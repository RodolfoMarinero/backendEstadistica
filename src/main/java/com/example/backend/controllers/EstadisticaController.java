package com.example.backend.controllers;

import com.example.backend.model.RequestData;
import com.example.backend.services.EstadisticaService;
import com.example.backend.utils.CsvReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estadisticas")
@CrossOrigin(origins = "*")
public class EstadisticaController {

    private final EstadisticaService service;

    public EstadisticaController(EstadisticaService service) {
        this.service = service;
    }

    @PostMapping("/media")
    public ResponseEntity<Double> getMedia(@RequestBody RequestData request) {
        return ResponseEntity.ok(service.calcularMedia(request.getData()));
    }

    @PostMapping("/mediana")
    public ResponseEntity<Double> getMediana(@RequestBody RequestData request) {
        return ResponseEntity.ok(service.calcularMediana(request.getData()));
    }

    @PostMapping("/moda")
    public ResponseEntity<List<Double>> getModa(@RequestBody RequestData request) {
        return ResponseEntity.ok(service.calcularModa(request.getData()));
    }

    @PostMapping("/desviacion")
    public ResponseEntity<Double> getDesviacion(@RequestBody RequestData request) {
        return ResponseEntity.ok(service.calcularDesviacionEstandar(request.getData()));
    }

    @PostMapping("/varianza")
    public ResponseEntity<Double> getVarianza(@RequestBody RequestData request) {
        return ResponseEntity.ok(service.calcularVarianza(request.getData()));
    }

    @GetMapping("/permutacion")
    public ResponseEntity<Long> getPermutacion(@RequestParam int n, @RequestParam int r) {
        return ResponseEntity.ok(service.calcularPermutacion(n, r));
    }

    @GetMapping("/combinatoria")
    public ResponseEntity<Long> getCombinatoria(@RequestParam int n, @RequestParam int r) {
        return ResponseEntity.ok(service.calcularCombinatoria(n, r));
    }

    @PostMapping("/procesar-csv")
    public ResponseEntity<?> procesarCSV(@RequestParam("file") MultipartFile file) {
        try {
            // Leer el archivo CSV
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

            // Saltar la primera línea (encabezados) y leer los datos
            List<Double> data = CsvReader.leerCSV(file);

            // Calcular estadísticas
            Map<String, Object> resultados = service.calcularEstadisticas(data);

            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Error al procesar el archivo: " + e.getMessage()
            ));
        }
    }

    @PostMapping("/procesar-csv-doble")
    public ResponseEntity<?> procesarCSVDoble(@RequestParam("file1") MultipartFile file1,
                                              @RequestParam("file2") MultipartFile file2) {
        try {
            List<Double> data1 = CsvReader.leerCSV(file1);
            List<Double> data2 = CsvReader.leerCSV(file2);

            // Calcular estadísticas
            Map<String, Object> resultados = Map.of(
                    "covarianza", service.calcularCovarianza(data1, data2),
                    "correlacion", service.calcularCorrelacion(data1, data2),
                    "coeficienteCorrelacion", service.calcularCoeficienteCorrelacion(data1, data2)
            );

            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Error al procesar los archivos: " + e.getMessage()
            ));
        }
    }
}
