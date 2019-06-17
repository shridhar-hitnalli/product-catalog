package com.gfg.catalog.controller;


import com.gfg.catalog.dto.APIPagination;
import com.gfg.catalog.dto.ProductBatchCreationRequest;
import com.gfg.catalog.dto.ProductDTO;
import com.gfg.catalog.dto.ProductInputRequest;
import com.gfg.catalog.dto.ProductList;
import com.gfg.catalog.dto.ProductUpdateRequest;
import com.gfg.catalog.mapper.PageRequestBuilder;
import com.gfg.catalog.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/v1/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "Product Catalog", value = "product catalog", description = "CRUD REST API for product catalog")
@Validated
public class ProductCatalogController {
  private static final String PRODUCT_ID = "id";

  private final ProductService productService;

  public ProductCatalogController(ProductService productService) {
    this.productService = productService;
  }

  @ApiOperation(value = "Batch fetch")
  @GetMapping
  public ResponseEntity<ProductList> findAll() {
    return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
  }

  @ApiOperation(value = "Search by title and description including pagination and sorting.")
  @GetMapping("/search")
  public ResponseEntity<APIPagination> search(
      @ApiParam(value = "title", example = "product")
      @RequestParam(value = "title", required = false) String title,
      @ApiParam(value = "description", example = "description")
      @RequestParam(value = "description", required = false) String description,
      @ApiParam(value = "pageNumber", example = "1")
      @Valid @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
      @ApiParam(value = "pageSize", example = "10")
      @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,
      @ApiParam(value = "sort criteria: Default ASC and for DESC order '-' ", example = "price,-brand")
      @Valid @RequestParam(value = "sort", required = false) String sort) {

    PageRequest pageRequest = PageRequestBuilder.getPageRequest(pageSize, pageNumber, sort);

    return new ResponseEntity<>(productService.search(
            StringUtils.defaultIfEmpty(title, ""),
            StringUtils.defaultIfEmpty(description, ""),
            pageRequest), HttpStatus.OK);
  }

  @ApiOperation(value = "Retrieve product based on product id")
  @GetMapping("/{id}")
  public ResponseEntity<ProductDTO> findProductById(
      @Valid
      @ApiParam(required = true, example = "11", value="Product Id")
      @PathVariable(value= PRODUCT_ID) Long productId) {

    return new ResponseEntity<>(productService.findById(productId), HttpStatus.OK);

  }

  @ApiOperation(value = "Create product")
  @PostMapping
  public ResponseEntity<ProductDTO> create(
      @ApiParam(required = true)
      @RequestBody @Valid ProductInputRequest productInputRequest) {

    return new ResponseEntity<>(productService.create(productInputRequest), HttpStatus.CREATED);
  }

  @ApiOperation(value = "Batch creation")
  @PostMapping("/batch")
  public ResponseEntity<ProductList> batchCreate(
      @ApiParam(required = true)
      @RequestBody @Valid ProductBatchCreationRequest productBatchCreationRequest) {

    return new ResponseEntity<>(productService.batchCreate(productBatchCreationRequest), HttpStatus.CREATED);
  }


  @ApiOperation(value = "Update product by id")
  @PutMapping("/{id}")
  public ResponseEntity<ProductDTO> update(
      @Valid
      @ApiParam(required = true, example = "11", value="Product Id")
      @PathVariable(value= PRODUCT_ID) Long productId,
      @RequestBody @Valid ProductUpdateRequest productUpdateRequest) {

    return new ResponseEntity<>(productService.update(productId, productUpdateRequest), HttpStatus.OK);
  }


  @ApiOperation(value = "Delete product by id")
  @DeleteMapping("/{id}")
  public ResponseEntity delete(
      @Valid
      @ApiParam(required = true, example = "11", value="Product Id")
      @PathVariable(value= PRODUCT_ID) Long productId) {

    productService.delete(productId);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }



}
