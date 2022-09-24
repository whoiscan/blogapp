package com.example.postservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDTO {
    @Column(unique = true)
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
