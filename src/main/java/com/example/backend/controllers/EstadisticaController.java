package com.example.backend.controllers;


import com.example.backend.model.RequestData;
import com.example.backend.services.EstadisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estadisticas")
@CrossOrigin(origins = "*")
public class EstadisticaController {

    @Autowired
    private EstadisticaService service;

    @PostMapping("/media")
    public double getMedia(@RequestBody RequestData request) {
        return service.calcularMedia(request.getData());
    }

    @PostMapping("/mediana")
    public double getMediana(@RequestBody RequestData request) {
        return service.calcularMediana(request.getData());
    }

    @PostMapping("/moda")
    public List<Double> getModa(@RequestBody RequestData request) {
        return service.calcularModa(request.getData());
    }

    @PostMapping("/desviacion")
    public double getDesviacion(@RequestBody RequestData request) {
        return service.calcularDesviacionEstandar(request.getData());
    }

    @PostMapping("/varianza")
    public double getVarianza(@RequestBody RequestData request) {
        return service.calcularVarianza(request.getData());
    }

    @GetMapping("/permutacion")
    public long getPermutacion(@RequestParam int n, @RequestParam int r) {
        return service.calcularPermutacion(n, r);
    }

    @GetMapping("/combinatoria")
    public long getCombinatoria(@RequestParam int n, @RequestParam int r) {
        return service.calcularCombinatoria(n, r);
    }
}
