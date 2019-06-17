package com.gfg.catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
  @ApiModelProperty(required = true, notes = "Product Id", example = "11", position = 1)
  private Long id;

  @ApiModelProperty(required = true, notes = "Product title", example = "product", position = 2)
  private String title;

  @ApiModelProperty(required = true, notes = "Product description", example = "description", position = 3)
  private String description;

  @ApiModelProperty(required = true, notes = "Brand name", example = "Adidas", position = 4)
  private String brand;

  @ApiModelProperty(required = true, notes = "Product price", example = "12.20", position = 5)
  private BigDecimal price;

  @ApiModelProperty(required = true, notes = "Product color", example = "Black", position = 6)
  private String color;
}
