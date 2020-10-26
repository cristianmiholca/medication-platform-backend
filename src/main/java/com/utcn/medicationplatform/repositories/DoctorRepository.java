package com.utcn.medicationplatform.repositories;

import com.utcn.medicationplatform.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

    Optional<Doctor> findByUsername(String username);
    void deleteByUsername(String username);

}
