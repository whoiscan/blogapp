package com.example.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private boolean success;
    private String message;
    private Object data;

    public Response(Object data, boolean success) {
        this.data = data;
        this.success = success;
        this.message = "Request completed!";
    }
}
