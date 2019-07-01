package org.sainsbury.vo;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class Products {
    private Optional<List<Product>> results = Optional.empty();
}
