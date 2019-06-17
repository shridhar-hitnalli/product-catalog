package com.gfg.catalog.dto;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateRequest {

  @ApiModelProperty(required = true, notes = "Product title", example = "product")
  private String title;

  @ApiModelProperty(required = true, notes = "Product description", example = "description")
  private String description;

  @ApiModelProperty(required = true, notes = "Brand name", example = "Adidas")
  private String brand;

  @ApiModelProperty(required = true, notes = "Product price", example = "12.20")
  private BigDecimal price;

  @ApiModelProperty(required = true, notes = "Product color", example = "Black")
  private String color;

}
