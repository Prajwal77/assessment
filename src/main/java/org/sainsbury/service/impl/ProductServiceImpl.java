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

    @Value("${base.url}")
    private String baseUrl;

    @Value("${products.url}")
    private String productsUrl;

    private static final String PRODUCT_TAG_CLASS = ".product";
    private static final String PRICE_PER_UNIT_TAG = "pricePerUnit";
    private static final String A_TAG = "a";
    private static final String HREF_TAG = "href";
    private static final String UNIT_PRICE_SEPARATOR = "/";
    private static final String URL_SEPARATOR = "../";
    private static final String TABLE_ROW_TAG_CLASS = ".tableRow1";
    private static final String TABLE_ROW_CHILD_TAG_CLASS = "tableRow1";
    private static final String PRODUCT_TEXT_TAG_CLASS = ".productText";
    private static final String P_TAG = "p";

    @Override
    public Optional<Products> getAllProducts() {
        return Optional.ofNullable(getAllProductsFromLink());
    }

    private Products getAllProductsFromLink() {
        Products products = new Products();
        loadProduct(products);
        return products;
    }

    private void loadProduct(Products products) {
        try {
            Document doc = Jsoup.connect(baseUrl + productsUrl).get();

            doc.select(PRODUCT_TAG_CLASS).forEach(tag -> {
                String unitPrice = tag.getElementsByClass(PRICE_PER_UNIT_TAG).first().text();

                Product product = new Product();
                product.setTitle(tag.getElementsByTag(A_TAG).text());
                product.setUnit_price(Float.parseFloat(unitPrice.substring(1, unitPrice.indexOf(UNIT_PRICE_SEPARATOR))));

                loadProductDetails(product, tag.getElementsByTag(A_TAG).attr(HREF_TAG));

                products.getResults().add(product);
            });
        } catch (IOException e) {
            log.error("Error scrapping Sainsbury’s Groceries website ", e);
        }
    }

    private void loadProductDetails(Product product, String link) {
        try {
            Document doc = Jsoup.connect(baseUrl + link.substring(link.lastIndexOf(URL_SEPARATOR) + 2)).get();
            loadProductCalories(product, doc);
            loadProductDescription(product, doc);
        }  catch (IOException e) {
            log.error("Error scrapping Sainsbury’s Groceries details website ", e);
        }
    }

    private void loadProductCalories(Product product, Document doc) {
        try {
            String kals = doc.select(TABLE_ROW_TAG_CLASS).first().getElementsByClass(TABLE_ROW_CHILD_TAG_CLASS).text();
            kals = kals.substring(kals.indexOf(" ") + 1, kals.indexOf("kJ"));
            product.setKcal_per_100g(Integer.parseInt(kals));
        } catch (NullPointerException e) {
            log.info("No calories info present for product: {}", product.getTitle());
        }
    }

    private void loadProductDescription(Product product, Document doc) {
        try {
            String description = doc.select(PRODUCT_TEXT_TAG_CLASS).first().getElementsByTag(P_TAG).first().text();
            product.setDescription(description);
        } catch (NullPointerException e) {
            log.info("No product description present for product: {}", product.getTitle());
        }
    }
}
