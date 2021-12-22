package com.example.circuitbreaker.model;

import lombok.Data;

@Data
public class Model1 {
    private String id = "service1";
    private boolean status = true;
    private String message = "Active Service";
}
