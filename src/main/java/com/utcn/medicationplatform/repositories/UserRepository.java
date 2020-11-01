package com.utcn.medicationplatform.repositories;

import com.utcn.medicationplatform.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);

    Boolean existsByUsername(String username);
}
