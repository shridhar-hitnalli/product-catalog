package com.gfg.catalog.service.impl;

import com.gfg.catalog.dto.APIPagination;
import com.gfg.catalog.dto.ProductBatchCreationRequest;
import com.gfg.catalog.dto.ProductDTO;
import com.gfg.catalog.dto.ProductInputRequest;
import com.gfg.catalog.dto.ProductList;
import com.gfg.catalog.dto.ProductUpdateRequest;
import com.gfg.catalog.entity.Product;
import com.gfg.catalog.exception.ProductNotFoundException;
import com.gfg.catalog.mapper.Mapper;
import com.gfg.catalog.repository.ProductRepository;
import com.gfg.catalog.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {

  private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

  private final ProductRepository productRepository;

  private final Mapper mapper;

  public ProductServiceImpl(ProductRepository productRepository,   Mapper mapper) {
    this.productRepository = productRepository;
    this.mapper = mapper;
  }

  @Override
  public ProductDTO findById(Long productId) {
    logger.debug("findById{} :", productId);
    return mapper.toProductDTO(productRepository.findById(productId)
        .orElseThrow(
            () -> new ProductNotFoundException("Product not found with id : " + productId)));

  }

  @Override
  public APIPagination search(String title, String description, PageRequest pageRequest) {
    if (StringUtils.isNoneBlank(title))
      title = title.toLowerCase();

    if (StringUtils.isNoneBlank(description))
      description = description.toLowerCase();

    final Page<Product> products = productRepository.findByTitleAndDescription(title, description, pageRequest);
    return APIPagination.builder()
        .products(
            products
                .stream()
                .map(mapper::toProductDTO)
                .collect(Collectors.toList()))
        .hasNextPage(products.hasNext())
        .hasPreviousPage(products.hasPrevious())
        .totalRecords(products.getTotalElements())
        .totalPages(products.getTotalPages())
        .pageNumber(pageRequest.getPageNumber() + 1) //as page number starts from 0, but for human readable format start from 1
        .pageSize(pageRequest.getPageSize()).build();
  }

  @Override
  public ProductList batchCreate(ProductBatchCreationRequest productBatchCreationRequest) {
    final List<Product> products = productRepository.saveAll(
        productBatchCreationRequest.getProductInputRequestList()
            .stream()
            .map(mapper::toProduct)
            .collect(Collectors.toList()));

    return ProductList.builder()
        .products(
            products
                .stream()
                .map(mapper::toProductDTO)
                .collect(Collectors.toList())).build();
  }

  @Override
  public ProductList findAll() {
    logger.debug("findAll::");

    return ProductList.builder()
        .products(
            productRepository.findAll()
                .stream()
                .map(mapper::toProductDTO)
                .collect(Collectors.toList())).build();
  }

  @Override
  public ProductDTO create(ProductInputRequest productInputRequest) {
    return mapper.toProductDTO(
        productRepository.save(
            mapper.toProduct(
                productInputRequest
            )
        )
    );
  }

  @Override
  public ProductDTO update(Long productId, ProductUpdateRequest productUpdateRequest) {
    if (productUpdateRequest == null) {
      return new ProductDTO();
    }

    final Product product = productRepository.findById(productId)
        .orElseThrow(
            () -> new ProductNotFoundException("Product not found with id : " + productId));

    return mapper.toProductDTO(
        productRepository.save(
            mapper.updateData(product,productUpdateRequest)
        )
    );
  }

  @Override
  public void delete(Long productId) {
    productRepository.delete(productRepository.findById(productId)
        .orElseThrow(
            () -> new ProductNotFoundException("Product not found with id : " + productId)));
  }

}
