package org.sainsbury.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class Product {
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer kcal_per_100g;

    private float unit_price;
    private String description;
}
