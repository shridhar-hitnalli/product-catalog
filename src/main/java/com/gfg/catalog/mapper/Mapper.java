package com.gfg.catalog.mapper;

import com.gfg.catalog.dto.ProductDTO;
import com.gfg.catalog.dto.ProductInputRequest;
import com.gfg.catalog.dto.ProductUpdateRequest;
import com.gfg.catalog.entity.Product;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


@Component
public class Mapper {
  public ProductDTO toProductDTO(Product product) {
    return ProductDTO.builder()
        .id(product.getId())
        .title(product.getTitle())
        .description(product.getDescription())
        .brand(product.getBrand())
        .price(product.getPrice())
        .color(product.getColor())
        .build();
  }

  public Product toProduct(ProductInputRequest productInputRequest) {
    return Product.builder()
        .title(productInputRequest.getTitle())
        .description(productInputRequest.getDescription())
        .brand(productInputRequest.getBrand())
        .price(productInputRequest.getPrice())
        .color(productInputRequest.getColor())
        .build();
  }

  public Product updateData(Product product, ProductUpdateRequest productUpdateRequest) {
    if (StringUtils.isNoneBlank(productUpdateRequest.getTitle()))
      product.setTitle(productUpdateRequest.getTitle());
    if (StringUtils.isNoneBlank(productUpdateRequest.getDescription()))
      product.setDescription(productUpdateRequest.getDescription());
    if (StringUtils.isNoneBlank(productUpdateRequest.getBrand()))
      product.setBrand(productUpdateRequest.getBrand());
    if (StringUtils.isNoneBlank(productUpdateRequest.getColor()))
      product.setColor(productUpdateRequest.getColor());
    if (productUpdateRequest.getPrice() != null && productUpdateRequest.getPrice().doubleValue() != 0)
      product.setPrice(productUpdateRequest.getPrice());

    return product;
  }
}

