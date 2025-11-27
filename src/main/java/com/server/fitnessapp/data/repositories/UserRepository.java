package com.server.fitnessapp.data.repositories;

import com.server.fitnessapp.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
