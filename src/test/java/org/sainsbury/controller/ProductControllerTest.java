package org.sainsbury.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.sainsbury.component.ResponseBuilder;
import org.sainsbury.service.ProductService;
import org.sainsbury.vo.Products;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ResponseBuilder responseBuilder;

    @InjectMocks
    private ProductController productController;

    @Test
    public void getAll() {
        Mockito.when(productService.getAllProducts()).thenReturn(Optional.ofNullable(new Products()));
        Mockito.when(responseBuilder.success(Mockito.any())).thenReturn(ResponseEntity.ok("OK"));

        ResponseEntity<String> responseEntity = productController.getAll();

        Mockito.verify(productService, Mockito.times(1)).getAllProducts();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}