package com.gfg.catalog.service;

import static com.gfg.catalog.TestDataFactory.PRODUCT_ID;
import static com.gfg.catalog.TestDataFactory.getPagination;
import static com.gfg.catalog.TestDataFactory.getProduct;
import static com.gfg.catalog.TestDataFactory.getProductDTO;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gfg.catalog.dto.APIPagination;
import com.gfg.catalog.dto.ProductBatchCreationRequest;
import com.gfg.catalog.dto.ProductDTO;
import com.gfg.catalog.dto.ProductInputRequest;
import com.gfg.catalog.dto.ProductList;
import com.gfg.catalog.dto.ProductUpdateRequest;
import com.gfg.catalog.entity.Product;
import com.gfg.catalog.exception.ProductNotFoundException;
import com.gfg.catalog.mapper.Mapper;
import com.gfg.catalog.mapper.PageRequestBuilder;
import com.gfg.catalog.repository.ProductRepository;
import com.gfg.catalog.service.impl.ProductServiceImpl;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationServiceTest.class)
public class ApplicationServiceTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private Mapper mapper;

  @InjectMocks
  private ProductServiceImpl productService;


  @Test
  public void should_create_product_success() throws Exception {
    final Product product = getProduct(null);

    when(productRepository.save(product)).thenReturn(product);

    final ProductInputRequest productInputRequest = ProductInputRequest.builder()
        .title("product")
        .description("product description")
        .brand("Adidas")
        .price(BigDecimal.valueOf(12.20))
        .color("Black")
        .build();

    final ProductDTO productDTO = getProductDTO(PRODUCT_ID);

    when(mapper.toProductDTO(any())).thenReturn(productDTO);
    when(mapper.toProduct(any())).thenReturn(product);

    final ProductDTO result = productService.create(productInputRequest);
    assertNotNull(result);
    assertThat(result.getTitle(), is(product.getTitle()));
    assertThat(result.getDescription(), is(product.getDescription()));
    assertThat(result.getBrand(), is(product.getBrand()));
    assertThat(result.getPrice(), is(product.getPrice()));
    assertThat(result.getColor(), is(product.getColor()));
  }

  @Test
  public void should_create_batch_insert_success() {
    final Product product1 = getProduct(null);
    final Product product2 = getProduct(null);

    final ArrayList<Product> products = Lists.newArrayList(product1, product2);

    when(productRepository.saveAll(products)).thenReturn(products);

    final ProductInputRequest productInputRequest1 = ProductInputRequest.builder().title("product1").brand("adidas1").build();
    final ProductInputRequest productInputRequest2 = ProductInputRequest.builder().title("product2").brand("adidas2").build();

    final ArrayList<ProductInputRequest> productDTOS = Lists.newArrayList(productInputRequest1, productInputRequest2);

    ProductBatchCreationRequest productBatchCreationRequest = new ProductBatchCreationRequest();
    productBatchCreationRequest.setProductInputRequestList(productDTOS);

    final ProductDTO productDTO = getProductDTO(PRODUCT_ID);
    when(mapper.toProductDTO(any())).thenReturn(productDTO);
    when(mapper.toProduct(any())).thenReturn(product1);

    final ProductList result = productService.batchCreate(productBatchCreationRequest);
    assertNotNull(result);
    assertThat(result.getProducts().size(), is(2));

  }


  @Test
  public void should_return_product_by_id_success() {

    final Product product = getProduct(PRODUCT_ID);
    when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));

    final ProductDTO productDTO = getProductDTO(PRODUCT_ID);
    when(mapper.toProductDTO(any())).thenReturn(productDTO);


    final ProductDTO result = productService.findById(PRODUCT_ID);

    verify(productRepository, times(1)).findById(PRODUCT_ID);

    assertNotNull(result);
    assertThat(result.getId(), is(PRODUCT_ID));
  }

  @Test
  public void should_throw_exception_product_by_id() {
    when(productRepository.findById(PRODUCT_ID)).thenThrow(ProductNotFoundException.class);

    try {
        productService.findById(PRODUCT_ID);
        fail("should throw exception here");

    } catch (Exception e){}
  }

  @Test
  public void should_find_all_success() throws Exception {
    final Product product1 = getProduct(1L);
    final Product product2 = getProduct(2L);

    final ArrayList<Product> productList = Lists.newArrayList(product1, product2);
    when(productRepository.findAll()).thenReturn(productList);

    final ProductDTO productDTO = getProductDTO(1L);
    when(mapper.toProductDTO(any())).thenReturn(productDTO);

    final ProductList result = productService.findAll();
    assertThat(result.getProducts().size(), is(2));
    assertThat(result.getProducts().get(0).getId(), is(1L));
    assertThat(result.getProducts().get(0).getTitle(), is("product"));
  }


  @Test
  public void should_search_title_description_pagination_sort_all_success() throws Exception {
    final ProductDTO product1 = getProductDTO(1L);
    final ProductDTO product2 = getProductDTO(2L);

    final APIPagination apiPagination = getPagination(Lists.newArrayList(product1, product2));
    PageRequest pageRequest = PageRequestBuilder.getPageRequest(5, 1, "id");

    Page<Product> products = new PageImpl<>(Lists.newArrayList(getProduct(PRODUCT_ID), getProduct(2L)));

    when(productRepository.findByTitleAndDescription("shoe", "sho", pageRequest)).thenReturn(products);

    final ProductDTO productDTO = getProductDTO(1L);
    when(mapper.toProductDTO(any())).thenReturn(productDTO);

    final APIPagination result = productService.search("shoe", "sho", pageRequest);
    assertThat(result.getTotalRecords(), is(2L));
    assertThat(result.getPageNumber(), is(1));
    assertThat(result.getPageSize(), is(5));
    assertThat(result.getProducts().size(), is(2));
    assertThat(result.getProducts().get(0).getId(), is(1L));
    assertThat(result.getProducts().get(0).getTitle(), is("product"));
  }

  @Test
  public void should_update_product_success() throws Exception {
    final Product product = getProduct(PRODUCT_ID);

    when(productRepository.save(product)).thenReturn(product);

    final ProductUpdateRequest productUpdateRequest = ProductUpdateRequest.builder()
        .title("product")
        .description("product description")
        .brand("Adidas")
        .price(BigDecimal.valueOf(12.20))
        .color("Black")
        .build();

    final ProductDTO productDTO = getProductDTO(PRODUCT_ID);

    when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));

    when(mapper.toProductDTO(any())).thenReturn(productDTO);
    when(mapper.updateData(any(),any())).thenReturn(product);

    final ProductDTO result = productService.update(PRODUCT_ID, productUpdateRequest);

    assertNotNull(result);
    assertThat(result.getTitle(), is(product.getTitle()));
    assertThat(result.getDescription(), is(product.getDescription()));
    assertThat(result.getBrand(), is(product.getBrand()));
    assertThat(result.getPrice(), is(product.getPrice()));
    assertThat(result.getColor(), is(product.getColor()));
  }

  @Test
  public void should_delete_product_success() {
    final Product product = getProduct(PRODUCT_ID);
    when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));

    productService.delete(PRODUCT_ID);

    verify(productRepository, times(1)).delete(product);

  }


}