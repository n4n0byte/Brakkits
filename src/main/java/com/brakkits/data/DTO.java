package com.brakkits.data;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/18/2019
 * Generic Data transfer object (DTO) for rest services
 **/
@Data
@NoArgsConstructor
public class DTO<T> {

    private int statusCode = 200;
    private String message = "success";
    private T data;

    public DTO(T data) {
        this.data = data;
    }
}
