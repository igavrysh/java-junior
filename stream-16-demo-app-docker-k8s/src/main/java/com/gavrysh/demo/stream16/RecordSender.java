package com.gavrysh.demo.stream16;

import com.gavrysh.demo.stream16.model.ExcelRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordSender {

    private final RestTemplate restTemplate;

    public void send(List<ExcelRecord> records) {

        try {
            final ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                    "/records",
                    records,
                    String.class
            );

            log.info("Records send result: [{}] {}", responseEntity.getStatusCode(), responseEntity.getBody());
        } catch (final Exception ex) {
            log.error("Failed to send records: {}", ex.getMessage(), ex);
        }

    }
}
