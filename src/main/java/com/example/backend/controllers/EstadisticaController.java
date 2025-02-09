package com.example.backend.controllers;

import com.example.backend.model.*;
import com.example.backend.services.EstadisticaService;
import com.example.backend.utils.CsvReader;
import com.example.backend.utils.MultipartFileEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.WebDataBinder;

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

    @Autowired
    private EstadisticaService service;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Registrar el editor personalizado para MultipartFile
        binder.registerCustomEditor(MultipartFile.class, new MultipartFileEditor());
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
            EstadisticasDTO dto = service.calcularEstadisticas(muestra);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "Error al procesar: " + e.getMessage()));
        }
    }

    @PostMapping("/procesar-csv-doble")
    public ResponseEntity<?> procesarCSVDoble(@ModelAttribute MuestraDobleDTO muestraDoble) {
        try {
            EstadisticasDobleDTO dto = service.calcularEstadisticasDoble(muestraDoble);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "Error al procesar: " + e.getMessage()));
        }
    }



}
