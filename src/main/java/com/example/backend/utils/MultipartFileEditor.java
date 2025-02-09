package com.example.backend.utils;

import java.beans.PropertyEditorSupport;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        // Si el texto está vacío o nulo, asigna null
        if (text == null || text.trim().isEmpty()) {
            setValue(null);
        } else {
            throw new IllegalArgumentException("No se puede convertir el texto a MultipartFile: " + text);
        }
    }
}
