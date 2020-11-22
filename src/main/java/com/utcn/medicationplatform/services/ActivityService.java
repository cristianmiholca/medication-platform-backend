package com.utcn.medicationplatform.services;

import com.utcn.medicationplatform.entities.Activity;
import com.utcn.medicationplatform.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Integer save(Activity activity) {
        return activityRepository.save(activity).getId();
    }

    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    public void deleteById(Integer id) {
        activityRepository.deleteById(id);
    }

    public void deleteAll() {
        activityRepository.deleteAll();
    }

}
