package com.example.backend.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestData {
    List<Double> data;
}
