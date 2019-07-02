package org.sainsbury.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.sainsbury.vo.Products;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    private static final String baseUrl="https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/";
    private static final String productsUrl="webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    @Test
    public void getAllProducts() {
        ReflectionTestUtils.setField(productService, "baseUrl", baseUrl);
        ReflectionTestUtils.setField(productService, "productsUrl", productsUrl);

        Optional<Products> products = productService.getAllProducts();

        Assert.assertEquals(true, products.isPresent());
    }
}