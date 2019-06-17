package com.gfg.catalog.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductList {
  @ApiModelProperty(required = true, notes = "Products")
  private List<ProductDTO> products;

}
