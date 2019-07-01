package org.sainsbury.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.sainsbury.service.ProductService;
import org.sainsbury.vo.Product;
import org.sainsbury.vo.Products;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Value("${products.link}")
    private String link;

    private static final String PRODUCT_TAG_CLASS = ".product";
    private static final String PRICE_PER_UNIT_TAG = "pricePerUnit";
    private static final String A_TAG = "a";
    private static final String UNIT_PRICE_SEPARATOR = "/";

    @Override
    public Optional<Products> getAllProducts() {
        return Optional.ofNullable(getAllProductsFromLink());
    }

    private Products getAllProductsFromLink() {
        Products products = new Products();

        try {
            Document doc = Jsoup.connect(link).get();

            doc.select(PRODUCT_TAG_CLASS).forEach(tag -> {
                String unitPrice = tag.getElementsByClass(PRICE_PER_UNIT_TAG).first().text();

                Product product = new Product();
                product.setTitle(tag.getElementsByTag(A_TAG).text());
                product.setUnit_price(Float.parseFloat(unitPrice.substring(1, unitPrice.indexOf(UNIT_PRICE_SEPARATOR))));

                products.getResults().add(product);
            });
        } catch (IOException e) {
            log.error("Error scrapping Sainsbury’s Groceries website ", e);
        }

        return products;
    }
}
