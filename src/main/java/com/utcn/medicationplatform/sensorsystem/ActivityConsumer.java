package com.utcn.medicationplatform.sensorsystem;

import com.rabbitmq.client.ConnectionFactory;
import com.utcn.medicationplatform.entities.Activity;
import com.utcn.medicationplatform.entities.Patient;
import com.utcn.medicationplatform.services.ActivityService;
import com.utcn.medicationplatform.services.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class ActivityConsumer {

    private static final String QUEUE_NAME = "activities";
    private static final String HOST = "localhost";

    private final ActivityService activityService;
    private final PatientService patientService;

    @Autowired
    public ActivityConsumer(ActivityService activityService, PatientService patientService) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST);

        this.activityService = activityService;
        this.patientService = patientService;
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void consumeMessages(String message) {
        JSONObject jsonObject = new JSONObject(message);
        Activity activity = jsonToActivity(jsonObject);
        activityService.save(activity);
    }

    private Activity jsonToActivity(JSONObject jsonObject){
        return Activity.builder()
                .patient(getPatientFromId(jsonObject.getString("patient_id")))
                .startDate(new Date(jsonObject.getLong("start")))
                .endDate(new Date(jsonObject.getLong("end")))
                .activity(jsonObject.getString("activity"))
                .build();
    }

    private Patient getPatientFromId(String patientId){
        UUID patientUUID = UUID.fromString(patientId);
        Optional<Patient> resultDB = patientService.findById(patientUUID);
        return resultDB.orElse(null);
    }

}
