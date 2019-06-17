package com.gfg.catalog.mapper;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.by;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public final class PageRequestBuilder {

  private PageRequestBuilder() {

  }

  public static PageRequest getPageRequest(Integer pageSize, Integer pageNumber, String sortingCriteria) {

    final Set<String> sortingFields = new LinkedHashSet<>(
        Arrays.asList(StringUtils.split(StringUtils.defaultIfEmpty(sortingCriteria, "price,id"), ",")));

    final List<Order> sortingOrders = sortingFields.stream().map(PageRequestBuilder::getOrder).collect(Collectors.toList());

    final Sort sort = sortingOrders.isEmpty() ? null : by(sortingOrders);

    return of(defaultIfNull(pageNumber, 1) - 1, defaultIfNull(pageSize, 5), sort);
  }

  private static Order getOrder(String value) {
    if (startsWith(value, "-")) {
      return new Order(Direction.DESC, substringAfter(value, "-"));
    } else if (startsWith(value, "+")) {
      return new Order(Direction.ASC, substringAfter(value, "+"));
    } else {
      return new Order(Direction.ASC, StringUtils.trim(value));
    }

  }

}