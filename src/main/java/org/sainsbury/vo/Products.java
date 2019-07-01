package org.sainsbury.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Products {
    private List<Product> results = new ArrayList();
}
