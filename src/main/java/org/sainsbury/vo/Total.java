package org.sainsbury.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Total {
    private float gross;
    private float vat;
}
