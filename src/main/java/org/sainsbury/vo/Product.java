package org.sainsbury.vo;

import lombok.Data;

@Data
public class Product {
    private String title;
    private int kcal_per_100g;
    private float unit_price;
    private String description;
}
