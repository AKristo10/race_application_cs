package com.trail_race.race_application.repository;

import com.trail_race.race_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getFirstByUsername(String username);
}
