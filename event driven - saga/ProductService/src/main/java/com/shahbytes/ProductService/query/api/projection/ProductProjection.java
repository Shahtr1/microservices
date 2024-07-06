package com.shahbytes.ProductService.query.api.projection;

import com.shahbytes.ProductService.command.api.data.Product;
import com.shahbytes.ProductService.command.api.data.ProductRepository;
import com.shahbytes.ProductService.command.api.model.ProductRestModel;
import com.shahbytes.ProductService.query.api.queries.GetProductsQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductProjection {
    private final ProductRepository productRepository;

    @QueryHandler
    public List<ProductRestModel> handle(GetProductsQuery getProductsQuery) {
        List<Product> products = productRepository.findAll();

        return products.stream().map(
                product -> ProductRestModel
                        .builder()
                        .quantity(product.getQuantity())
                        .name(product.getName())
                        .price(product.getPrice())
                        .build()
        ).toList();

    }
}
