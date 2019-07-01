package org.sainsbury.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.sainsbury.vo.Products;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void getAllProducts() {
        Optional<Products> products = productService.getAllProducts();

        Assert.assertEquals(Optional.empty(), products);
    }
}