package com.gfg.catalog.dto;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInputRequest {

  @ApiModelProperty(required = true, notes = "Product title", example = "product")
  @NotEmpty(message = "Product title is required")
  private String title;

  @ApiModelProperty(required = true, notes = "Product description", example = "description")
  @NotEmpty(message = "Product description is required")
  private String description;

  @ApiModelProperty(required = true, notes = "Brand name", example = "Adidas")
  @NotEmpty(message = "Product brand is required")
  private String brand;

  @ApiModelProperty(required = true, notes = "Product price", example = "12.20")
  @NotNull(message = "Product price is required")
  @DecimalMin(value = "0")
  private BigDecimal price;

  @ApiModelProperty(required = true, notes = "Product color", example = "Black")
  @NotEmpty(message = "Product color is required")
  private String color;

}
