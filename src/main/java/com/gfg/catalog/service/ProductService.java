package com.gfg.catalog.service;

import com.gfg.catalog.dto.APIPagination;
import com.gfg.catalog.dto.ProductBatchCreationRequest;
import com.gfg.catalog.dto.ProductDTO;
import com.gfg.catalog.dto.ProductInputRequest;
import com.gfg.catalog.dto.ProductList;
import com.gfg.catalog.dto.ProductUpdateRequest;
import org.springframework.data.domain.PageRequest;

public interface ProductService {

  ProductDTO findById(Long id);

  ProductList findAll();

  ProductDTO create(ProductInputRequest productInputRequest);

  ProductDTO update(Long productId, ProductUpdateRequest productInputRequest);

  APIPagination search(String title, String description, PageRequest pageRequest);

  ProductList batchCreate(ProductBatchCreationRequest productBatchCreationRequest);

  void delete(Long productId);
}
