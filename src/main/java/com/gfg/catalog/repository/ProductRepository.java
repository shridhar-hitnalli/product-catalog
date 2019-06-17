package com.gfg.catalog.repository;

import com.gfg.catalog.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query("select p from Product p where lower(p.title) like %:title% and lower(p.description) like %:description% ")
  Page<Product> findByTitleAndDescription(@Param(value = "title") String title, @Param(value = "description") String description, Pageable pageable);

}