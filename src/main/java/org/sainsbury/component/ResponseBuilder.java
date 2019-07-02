package org.sainsbury.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sainsbury.vo.SuccessResponse;
import org.sainsbury.vo.Total;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ResponseBuilder {

    private final ObjectMapper objectMapper;
    private String EMPTY_STRING = "";

    public ResponseEntity<String> success(List body) {
        Total total = Total.builder().gross(0).vat(0).build();
        SuccessResponse successResponse = SuccessResponse.builder()
                .results(body)
                .total(total)
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
}
