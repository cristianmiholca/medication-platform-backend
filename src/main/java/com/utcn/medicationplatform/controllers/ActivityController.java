package com.utcn.medicationplatform.controllers;

import com.utcn.medicationplatform.entities.Activity;
import com.utcn.medicationplatform.services.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/activities")
@Slf4j
public class ActivityController {

    private static final long SEVEN_HOURS_MILLIS = 25200000;
    private static final long FIVE_HOURS_MILLIS = 18000000;
    private static final long THIRTY_MINUTES_MILLIS = 1800000;
    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/filterByRule/{number}")
    public ResponseEntity<List<Activity>> filterByRule(@PathVariable Integer number){
        log.info("GET request for filter by rule: {}", number);
        Predicate<Activity> predicate;
        switch (number) {
            case 1:
                predicate = this::isSleepingMoreThan7h;
                break;
            case 2:
                predicate = this::isOutdoorMoreThan5h;
                break;
            case 3:
                predicate = this::isInBathroomMoreThan30m;
                break;
            default:
                predicate = activity -> true;
        }
        List<Activity> activities = activityService.findAll().stream()
                .filter(predicate)
                .collect(Collectors.toList());
        activities.stream()
                .map(this::getActivityDuration)
                .forEach(System.err::println);
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @GetMapping("/clear")
    public ResponseEntity<List<Activity>> clear(){
        log.info("GET request for clear");
        List<Activity> activities = activityService.findAll();
        activityService.deleteAll();

        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    private long getActivityDuration(Activity activity) {
        return activity.getEndDate().getTime() - activity.getStartDate().getTime();
    }

    private boolean isSleepingMoreThan7h(Activity activity) {
        return activity.getActivity().equals("Sleeping") &&
                getActivityDuration(activity) > SEVEN_HOURS_MILLIS;
    }

    private boolean isOutdoorMoreThan5h(Activity activity) {
        return activity.getActivity().equals("Leaving") &&
                getActivityDuration(activity) > FIVE_HOURS_MILLIS;
    }

    private boolean isInBathroomMoreThan30m(Activity activity) {
        return (activity.getActivity().equals("Toileting") ||
                activity.getActivity().equals("Showering") ||
                activity.getActivity().equals("Grooming")) &&
                getActivityDuration(activity) > THIRTY_MINUTES_MILLIS;
    }

}
