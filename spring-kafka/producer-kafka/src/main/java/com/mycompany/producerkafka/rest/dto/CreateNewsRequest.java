package com.mycompany.producerkafka.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewsRequest {

    @NotBlank
    private String source;

    @NotBlank
    private String title;
}
