package com.shahbytes.ProductService.query.api.controller;

import com.shahbytes.ProductService.command.api.model.ProductRestModel;
import com.shahbytes.ProductService.query.api.queries.GetProductsQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductQueryController {

    private final QueryGateway queryGateway;

    @GetMapping
    public List<ProductRestModel> getAllProducts() {
        GetProductsQuery getProductsQuery = new GetProductsQuery();

        return queryGateway.query(getProductsQuery,
                ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();
    }
}
