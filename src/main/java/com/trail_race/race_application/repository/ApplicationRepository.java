package com.trail_race.race_application.repository;

import com.trail_race.race_application.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
