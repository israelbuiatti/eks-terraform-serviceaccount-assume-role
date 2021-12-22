package com.example.circuitbreaker.dto;

import com.example.circuitbreaker.model.Model1;
import com.example.circuitbreaker.model.Model2;
import com.example.circuitbreaker.model.Model3;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {
    private Model1 model1;
    private Model2 model2;
    private Model3 model3;
}
