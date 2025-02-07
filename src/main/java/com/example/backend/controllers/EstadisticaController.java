package com.example.backend.controllers;

import com.example.backend.model.MuestraDTO;
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
    public ResponseEntity<?> procesarCSV(@ModelAttribute MuestraDTO muestra) {
        try {
            List<Double> data;

            // Si se recibe archivo, se procesa el CSV.
            if (muestra.getFile() != null && !muestra.getFile().isEmpty()) {
                data = CsvReader.leerCSV(muestra.getFile());
            }
            // Si no se recibe archivo, se utiliza la lista de datos proporcionada.
            else if (muestra.getDatos() != null && !muestra.getDatos().isEmpty()) {
                data = muestra.getDatos();
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "No se recibieron datos ni archivo CSV"));
            }

            // Calcular estadísticas usando el servicio correspondiente.
            Map<String, Object> resultados = service.calcularEstadisticas(data);
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al procesar: " + e.getMessage()));
        }
    }


    @PostMapping("/procesar-csv-doble")
    public ResponseEntity<?> procesarCSVDoble(
            @ModelAttribute("muestra1") MuestraDTO muestra1,
            @ModelAttribute("muestra2") MuestraDTO muestra2) {
        try {
            List<Double> data1;
            List<Double> data2;

            // Procesar el primer conjunto de datos
            if (muestra1.getFile() != null && !muestra1.getFile().isEmpty()) {
                data1 = CsvReader.leerCSV(muestra1.getFile());
            } else if (muestra1.getDatos() != null && !muestra1.getDatos().isEmpty()) {
                data1 = muestra1.getDatos();
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "No se recibieron datos en la primera muestra"));
            }

            // Procesar el segundo conjunto de datos
            if (muestra2.getFile() != null && !muestra2.getFile().isEmpty()) {
                data2 = CsvReader.leerCSV(muestra2.getFile());
            } else if (muestra2.getDatos() != null && !muestra2.getDatos().isEmpty()) {
                data2 = muestra2.getDatos();
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "No se recibieron datos en la segunda muestra"));
            }

            // Llamar al método del service que calcula las estadísticas para dos conjuntos de datos.
            Map<String, Object> resultados = service.calcularEstadisticasDoble(data1, data2);

            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al procesar: " + e.getMessage()));
        }
    }


}
