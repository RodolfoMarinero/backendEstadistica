package com.example.backend.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MuestraDTO {
    private long id;
    private String nombre;
    private String datos;
    private MultipartFile file;

}