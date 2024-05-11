package com.trail_race.race_application.service;

import com.trail_race.race_application.dto.request.ApplicationRequest;
import com.trail_race.race_application.dto.EventType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
public class ApplicationServiceUnitTest {

    @Mock
    private KafkaTemplate<String, ApplicationRequest> kafkaTemplate;

    @InjectMocks
    private ApplicationService applicationService;

    @Test
    public void testCreate() {
        ApplicationRequest applicationRequest = ApplicationRequest
                .builder()
                .firstName("Andy")
                .lastName("Dandy")
                .build();

        applicationService.create(applicationRequest);
        Mockito.verify(kafkaTemplate).send("application-topic", applicationRequest);
    }

    @Test
    public void testUpdate() {
        ApplicationRequest applicationRequest = ApplicationRequest
                .builder()
                .firstName("NewName")
                .lastName("NewLastName")
                .build();

        applicationService.update(1, applicationRequest);

        Mockito.verify(kafkaTemplate).send("application-topic", applicationRequest);
    }

    @Test
    public void testDelete() {
        Integer id = 1;
        ApplicationRequest applicationRequest = ApplicationRequest.builder()
                .eventType(EventType.DELETE_APPLICATION)
                .id(id)
                .build();

        applicationService.delete(id);

        Mockito.verify(kafkaTemplate).send("application-topic", applicationRequest);
    }

}
