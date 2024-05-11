package com.trail_race.race_application.service;

import com.trail_race.race_application.dto.request.ApplicationRequest;
import com.trail_race.race_application.dto.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationService {

    private final KafkaTemplate<String, ApplicationRequest> kafkaTemplate;

    public void create(ApplicationRequest application) {
        application.setEventType(EventType.CREATE_APPLICATION);
        log.info("Creating new application object...");
        kafkaTemplate.send("application-topic", application);
    }

    public void update(Integer id, ApplicationRequest application) {
        validateId(id);
        application.setId(id);
        application.setEventType(EventType.UPDATE_APPLICATION);
        kafkaTemplate.send("application-topic", application);
    }

    public void delete(Integer id) {
        ApplicationRequest applicationRequest = ApplicationRequest.builder()
                .eventType(EventType.DELETE_APPLICATION)
                .id(id)
                .build();
        kafkaTemplate.send("application-topic", applicationRequest);
    }

    private void validateId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
    }
}
