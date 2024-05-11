package com.trail_race.race_application.controller;

import com.trail_race.race_application.dto.request.ApplicationRequest;
import com.trail_race.race_application.service.ApplicationService;
import com.trail_race.race_application.util.Api;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping(Api.USER + "/application")
    public ResponseEntity<String> createApplication(@Valid @RequestBody ApplicationRequest applicationRequest) {
        applicationService.create(applicationRequest);
        return ResponseEntity.ok("Application created successfully.");
    }

    @PatchMapping(Api.USER + "/application/{id}")
    public ResponseEntity<String> updateApplication(@PathVariable Integer id,
                                                    @Valid @RequestBody ApplicationRequest applicationRequest) {
        applicationService.update(id, applicationRequest);
        return ResponseEntity.ok("Application updated successfully.");
    }

    @DeleteMapping(Api.USER + "/application/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable Integer id) {
        applicationService.delete(id);
        return ResponseEntity.ok("Application deleted successfully.");
    }
}
