package com.example.circuitbreaker.model;

import lombok.Data;

@Data
public class Model2 {
    private String id = "service2";
    private boolean status = true;
    private String message = "Active Service";
}
