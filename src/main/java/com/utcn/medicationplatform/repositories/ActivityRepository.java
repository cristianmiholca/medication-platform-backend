package com.utcn.medicationplatform.repositories;


import com.utcn.medicationplatform.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    void deleteById(Integer id);

}
