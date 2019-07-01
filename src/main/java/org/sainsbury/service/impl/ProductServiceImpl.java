package org.sainsbury.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sainsbury.service.ProductService;
import org.sainsbury.vo.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {

    private final RestTemplate restTemplate;

    @Value("${products.link}")
    private String link;

    @Override
    public Optional<Products> getAllProducts() {
        return Optional.empty();
    }
}
