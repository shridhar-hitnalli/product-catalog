package com.gfg.catalog.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class APIPagination {

  @ApiModelProperty(required = true, notes = "Products", position = 1)
  private List<ProductDTO> products;

  @ApiModelProperty(required = true, notes = "Flag to indicate next page exists", example = "true", position = 2)
  private Boolean hasNextPage;

  @ApiModelProperty(required = true, notes = "Flag to indicate previous page exists", example = "true", position = 3)
  private Boolean hasPreviousPage;

  @ApiModelProperty(required = true, notes = "Total number of records", example = "15", position = 4)
  private Long totalRecords;

  @ApiModelProperty(required = true, notes = "Total number of pagers", example = "3", position = 5)
  private Integer totalPages;

  @ApiModelProperty(required = true, notes = "Page number", example = "2", position = 6)
  private Integer pageNumber;

  @ApiModelProperty(required = true, notes = "Records per page", example = "5", position = 7)
  private Integer pageSize;

}
