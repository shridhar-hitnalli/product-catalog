package com.gfg.catalog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfg.catalog.dto.APIPagination;
import com.gfg.catalog.dto.ProductBatchCreationRequest;
import com.gfg.catalog.dto.ProductDTO;
import com.gfg.catalog.entity.Product;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.util.ResourceUtils;

public class TestDataFactory {

  public static final Long PRODUCT_ID = 1L;

  public static Product getProduct(final Long id) {
    return Product
        .builder()
        .id(id)
        .title("product")
        .description("description")
        .brand("Adidas")
        .price(BigDecimal.valueOf(12.20))
        .color("Black")
        .build();
  }

  public static ProductDTO getProductDTO(final Long id) {
    return ProductDTO
        .builder()
        .id(id)
        .title("product")
        .description("description")
        .brand("Adidas")
        .price(BigDecimal.valueOf(12.20))
        .color("Black")
        .build();
  }

  public static APIPagination getPagination(List<ProductDTO> products) {
    return APIPagination
        .builder()
        .hasNextPage(false)
        .hasPreviousPage(false)
        .pageNumber(1)
        .pageSize(5)
        .totalPages(1)
        .totalRecords(2L)
        .products(products)
        .build();
  }

  public static ProductBatchCreationRequest getBulkRequests() {

    ProductBatchCreationRequest productBatchCreationRequest = null;

    ObjectMapper mapper = new ObjectMapper();
    try {
      productBatchCreationRequest = mapper.readValue(ResourceUtils.getFile("classpath:batchrequest.json"), ProductBatchCreationRequest.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return productBatchCreationRequest;
  }

  public static Product getProductDTOForUpdate(final Long id) {
    return Product
        .builder()
        .id(id)
        .title("updateproduct")
        .brand("Adidas")
        .build();
  }
}
