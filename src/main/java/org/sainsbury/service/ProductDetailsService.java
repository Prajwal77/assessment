package org.sainsbury.service;

import org.sainsbury.vo.Products;

import java.util.Optional;

public interface ProductDetailsService {
    Optional<Products> getAllProducts();
}
