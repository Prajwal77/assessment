package org.sainsbury.service.impl;

import org.sainsbury.service.ProductDetailsService;
import org.sainsbury.vo.Products;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {
    @Override
    public Optional<Products> getAllProducts() {
        return Optional.empty();
    }
}
