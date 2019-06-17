package com.gfg.catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    @ApiModelProperty(required = true, notes = "One of the standard http status codes", position = 1)
    private int httpStatusCode;

    @ApiModelProperty(required = true, notes = "Textual description of the error code", position = 2)
    private String errorMessage;

}
