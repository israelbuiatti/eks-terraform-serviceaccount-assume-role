package com.example.circuitbreaker.model;

import lombok.Data;

@Data
public class Model3 {
    private String id = "service3";
    private boolean status = true;
    private String message = "Active Service";
}
