package com.shopma.data.mapper;

import com.shopma.data.remote.dto.ProductDto;
import com.shopma.domain.model.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    public static Product toDomain(ProductDto dto) {
        return new Product(
                dto.getId(), dto.getTitle(), dto.getPrice(), dto.getDescription(),
                dto.getCategory(), dto.getImage(), dto.getRating()
        );
    }

    public static List<Product> toDomainList(List<ProductDto> dtos) {
        List<Product> products = new ArrayList<>();
        for (ProductDto dto : dtos) products.add(toDomain(dto));
        return products;
    }
}
