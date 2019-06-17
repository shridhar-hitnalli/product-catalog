package com.gfg.catalog.dto;

import java.util.List;
import lombok.Data;

@Data
public class ProductBatchCreationRequest {
  private List<ProductInputRequest> productInputRequestList;

}
