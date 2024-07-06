package com.shahbytes.ProductService.command.api.events;

import com.shahbytes.ProductService.command.api.data.Product;
import com.shahbytes.ProductService.command.api.data.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
@RequiredArgsConstructor
@ProcessingGroup("product")
public class ProductEventsHandler {

    private final ProductRepository productRepository;

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) throws Exception {
        Product product = new Product();
        BeanUtils.copyProperties(productCreatedEvent, product);

        productRepository.save(product);
        throw new Exception("Exception");
    }

    @ExceptionHandler
    public void handleException(Exception exception) throws Exception {
        // we can add loggers here as well
        throw exception;
    }
}
