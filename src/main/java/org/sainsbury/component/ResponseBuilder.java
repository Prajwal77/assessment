package org.sainsbury.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sainsbury.vo.Products;
import org.sainsbury.vo.SuccessResponse;
import org.sainsbury.vo.Total;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ResponseBuilder {

    private final ObjectMapper objectMapper;
    private String EMPTY_STRING = "";

    public ResponseEntity<String> success(Optional<Products> products) {
        SuccessResponse successResponse = SuccessResponse.builder()
                .results(products.get().getResults())
                .total(calculateTotal(products))
                .build();

        return ResponseEntity.ok(writeValueAsString(successResponse));
    }

    private <T> String writeValueAsString(T response) {
        try {
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            log.error("Unable to write response");
        }

        return EMPTY_STRING;
    }

    private Total calculateTotal(Optional<Products> products) {
        Total total = Total.builder()
                .gross(0)
                .vat(0)
                .build();

        products.get().getResults().stream().forEach(product -> {
            BigDecimal price = new BigDecimal(product.getUnit_price()).setScale(2, RoundingMode.HALF_UP);
            BigDecimal vat = new BigDecimal(0.2 * price.doubleValue()).setScale(2, RoundingMode.HALF_UP);

            total.setGross(total.getGross() + price.floatValue());
            total.setVat(total.getVat() + vat.floatValue());
        });

        return total;
    }
}
