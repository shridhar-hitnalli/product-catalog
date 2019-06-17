package com.gfg.catalog.controller;

import static com.gfg.catalog.TestDataFactory.PRODUCT_ID;
import static com.gfg.catalog.TestDataFactory.getBulkRequests;
import static com.gfg.catalog.TestDataFactory.getProductDTO;
import static com.gfg.catalog.TestDataFactory.getProductDTOForUpdate;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import com.gfg.catalog.dto.APIPagination;
import com.gfg.catalog.dto.ProductDTO;
import com.gfg.catalog.dto.ProductList;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;

@Slf4j
@EnableAutoConfiguration
public class ProductCatalogControllerTest extends BaseApplicationTest {

  private static final String username = "admin";
  private static final String password = "gfg123";

  @Before
  public void setup() {}

  @Test
  public void testProductCatalogController() {

    createProductWithoutAuth();
    createProduct();
    findProductById();
    update();
    delete();

    //now batch requests
    createBatchInserts();
    findAll();
    searchByTitleAndDescription();
    search();
  }

  private void createProductWithoutAuth() {
    log.info("Create product without authentication:::");
    given()
        .baseUri("http://localhost")
        .port(port)
        .contentType("application/json")
        .body(getProductDTO(PRODUCT_ID))
        .when()
        .post("/v1/products")
        .then()
        .assertThat()
        .statusCode(HttpStatus.UNAUTHORIZED.value());
  }

  private void createProduct() {
    log.info("Create product success:::");
    given()
        .auth()
        .basic(username, password)
        .baseUri("http://localhost")
        .port(port)
        .contentType("application/json")
        .body(getProductDTO(PRODUCT_ID))
        .when()
        .post("/v1/products")
        .then()
        .assertThat()
        .statusCode(HttpStatus.CREATED.value());
  }

  private void findProductById() {
    log.info("find product by id success:::");
    ProductDTO productDTO =
        given()
            .auth()
            .basic(username, password)
            .baseUri("http://localhost")
            .port(port)
            .contentType("application/json")
            .when()
            .get("/v1/products/{id}", PRODUCT_ID)
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .log().ifValidationFails()
            .and()
            .extract().body().as(ProductDTO.class);
    assertNotNull(productDTO);
    assertThat(productDTO.getTitle(), is("product"));
    assertThat(productDTO.getDescription(), is("description"));
    assertThat(productDTO.getBrand(), is("Adidas"));
    assertThat(productDTO.getPrice(), is(BigDecimal.valueOf(12.20).setScale(2, RoundingMode.HALF_UP)));
    assertThat(productDTO.getColor(), is("Black"));
  }

  private void update() {
    log.info("Update product by id success:::");
    ProductDTO productDTO =
        given()
            .auth()
            .basic(username, password)
            .baseUri("http://localhost")
            .port(port)
            .contentType("application/json")
            .body(getProductDTOForUpdate(PRODUCT_ID))
            .when()
            .put("/v1/products/{id}", PRODUCT_ID)
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .log().ifValidationFails()
            .and()
            .extract().body().as(ProductDTO.class);

    assertNotNull(productDTO);
    assertThat(productDTO.getTitle(), is("updateproduct"));
    assertThat(productDTO.getBrand(), is("Adidas"));
    assertThat(productDTO.getPrice(), is(BigDecimal.valueOf(12.20).setScale(2, RoundingMode.HALF_UP)));
    assertThat(productDTO.getColor(), is("Black"));
  }

  private void delete() {
    log.info("Delete product by id success:::");
    given()
        .auth()
        .basic(username, password)
        .baseUri("http://localhost")
        .port(port)
        .contentType("application/json")
        .when()
        .delete("/v1/products/{id}", PRODUCT_ID)
        .then()
        .assertThat()
        .statusCode(HttpStatus.NO_CONTENT.value());
  }

  private void createBatchInserts() {
    log.info("Create batch inserts success:::");
    ProductList productListOutput =
    given()
        .auth()
        .basic(username, password)
        .baseUri("http://localhost")
        .port(port)
        .contentType("application/json")
        .body(getBulkRequests())
        .when()
        .post("/v1/products/batch")
        .then()
        .assertThat()
        .statusCode(HttpStatus.CREATED.value())
        .log().ifValidationFails()
        .and()
        .extract().body().as(ProductList.class);
    assertNotNull(productListOutput);
    assertThat(productListOutput.getProducts().size(), is(11));
    assertThat(productListOutput.getProducts().get(0).getTitle(), is("T-shirt"));
    assertThat(productListOutput.getProducts().get(1).getTitle(), is("Shoes"));
    assertThat(productListOutput.getProducts().get(2).getTitle(), is("Shoes"));
    assertThat(productListOutput.getProducts().get(3).getTitle(), is("Trousers"));

  }

  private void findAll() {
    log.info("fetch all batch success:::");
    ProductList productListOutput =
        given()
            .auth()
            .basic(username, password)
            .baseUri("http://localhost")
            .port(port)
            .contentType("application/json")
            .when()
            .get("/v1/products")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .log().ifValidationFails()
            .and()
            .extract().body().as(ProductList.class);
    assertNotNull(productListOutput);
    assertThat(productListOutput.getProducts().size(), is(11));
    assertThat(productListOutput.getProducts().get(0).getTitle(), is("T-shirt"));
    assertThat(productListOutput.getProducts().get(1).getTitle(), is("Shoes"));
    assertThat(productListOutput.getProducts().get(2).getTitle(), is("Shoes"));
    assertThat(productListOutput.getProducts().get(3).getTitle(), is("Trousers"));
  }



  private void searchByTitleAndDescription() {
    log.info("search by Title and Description, Pagination, sort by price ASC and branch DESC success:::");
    APIPagination apiPagination =
        given()
            .auth()
            .basic(username, password)
            .baseUri("http://localhost")
            .port(port)
            .contentType("application/json")
            .body(getBulkRequests())
            .when()
            .queryParam("title", "sho")
            .queryParam("description", "sho")
            .queryParam("pageNumber", 1)
            .queryParam("pageSize", 5)
            .queryParam("sort", "price,-brand")  //default ASC and for DESC '-'
            .get("/v1/products/search")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .log().ifValidationFails()
            .and()
            .extract().body().as(APIPagination.class);
    final List<ProductDTO> products = apiPagination.getProducts();

    assertNotNull(apiPagination);
    assertThat(apiPagination.getTotalPages(), is(1));
    assertThat(apiPagination.getPageNumber(), is(1));
    assertThat(apiPagination.getPageSize(), is(5));
    assertThat(apiPagination.getHasNextPage(), is(false));
    assertThat(apiPagination.getHasPreviousPage(), is(false));

    assertThat(products.size(), is(4));
    assertThat(products.get(0).getTitle(), is("Shoes"));
    assertThat(products.get(1).getTitle(), is("Shoes"));
    assertThat(products.get(0).getDescription(), is("Shoes trekking"));
    assertThat(products.get(1).getDescription(), is("Formal shoes"));

    assertThat(products.get(0).getPrice(), is(BigDecimal.valueOf(10.80).setScale(2, RoundingMode.HALF_UP)));
    assertThat(products.get(1).getPrice(), is(BigDecimal.valueOf(11.80).setScale(2, RoundingMode.HALF_UP)));
    assertThat(products.get(3).getPrice(), is(BigDecimal.valueOf(12.80).setScale(2, RoundingMode.HALF_UP)));
    assertThat(products.get(0).getBrand(), is("Red Tape"));
    assertThat(products.get(1).getBrand(), is("Hush puppies"));
    assertThat(products.get(3).getBrand(), is("Adidas"));

  }


  private void search() {
    log.info("search, Pagination, sort by price DESC title success:::");
    APIPagination apiPagination =
        given()
            .auth()
            .basic("admin", "gfg123")
            .baseUri("http://localhost")
            .port(port)
            .contentType("application/json")
            .body(getBulkRequests())
            .when()
            .queryParam("title", "") //lets test without title and description..it should fetch all records
            .queryParam("description", "")
            .queryParam("pageNumber", 1)
            .queryParam("pageSize", 5)
            .queryParam("sort", "-price")
            .get("/v1/products/search")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .log().ifValidationFails()
            .and()
            .extract().body().as(APIPagination.class);
    final List<ProductDTO> products = apiPagination.getProducts();

    assertNotNull(apiPagination);
    assertThat(apiPagination.getTotalPages(), is(3));
    assertThat(apiPagination.getTotalRecords(), is(11L));
    assertThat(apiPagination.getPageNumber(), is(1));
    assertThat(apiPagination.getPageSize(), is(5));
    assertThat(apiPagination.getHasNextPage(), is(true));
    assertThat(apiPagination.getHasPreviousPage(), is(false));

    assertThat(products.size(), is(5));
    assertThat(products.get(0).getTitle(), is("Trousers"));
    assertThat(products.get(1).getTitle(), is("Shoes"));
    assertThat(products.get(3).getTitle(), is("shirt"));
    assertThat(products.get(0).getDescription(), is("Trousers slim"));
    assertThat(products.get(1).getDescription(), is("Sneakers"));
    assertThat(products.get(3).getDescription(), is("slim fit"));

    assertThat(products.get(0).getPrice(), is(BigDecimal.valueOf(18.80).setScale(2, RoundingMode.HALF_UP)));
    assertThat(products.get(1).getPrice(), is(BigDecimal.valueOf(18.20).setScale(2, RoundingMode.HALF_UP)));
    assertThat(products.get(0).getBrand(), is("Blackberry"));
    assertThat(products.get(1).getBrand(), is("Woodland"));


  }

}
