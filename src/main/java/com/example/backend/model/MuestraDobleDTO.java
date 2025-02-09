package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MuestraDobleDTO {
    private long id;
    private String nombre;
    private String datos1;
    private String datos2;
    private MultipartFile file1;
    private MultipartFile file2;
}