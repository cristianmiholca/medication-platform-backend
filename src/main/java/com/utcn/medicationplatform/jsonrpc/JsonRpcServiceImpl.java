package com.utcn.medicationplatform.jsonrpc;

import com.utcn.medicationplatform.entities.Medication;
import com.utcn.medicationplatform.entities.MedicationPlan;
import com.utcn.medicationplatform.services.MedicationPlanService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.rmi.CORBA.Util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JsonRpcServiceImpl implements JsonRpcService {
    private static final String QUEUE_NAME = "rpc_queue";
    private static final String DEFAULT_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
    private final MedicationPlanService medicationPlanService;

    @Autowired
    public JsonRpcServiceImpl(MedicationPlanService medicationPlanService) {
        this.medicationPlanService = medicationPlanService;
    }

    @RabbitListener(queues = QUEUE_NAME)
    public String handleRequest(String request) throws ParseException {
        JSONObject jsonRequest = new JSONObject(request);
        String method = jsonRequest.getString("method");
        if(method.equals("sendMedicationPlan")) {
            UUID patientUUID = UUID.fromString(jsonRequest.getString("patient_id"));
            Date currentDate = new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(jsonRequest.getString("current_date"));
            MedicationPlanInfo medicationPlanInfo = sendMedicationPlan(patientUUID, currentDate);
            if (medicationPlanInfo != null) {
                return Utils.sendMedPlanJsonResponse(medicationPlanInfo).toString();
            }
        }
        if(method.equals("notifyMedicationTaken")) {
            String medicationName = jsonRequest.getString("medication_name");
            notifyMedicationTaken(medicationName);
            return Utils.notifyMedTakenJsonResponse().toString();
        }
        return "Cannot handle request!";
    }

    @Override
    public MedicationPlanInfo sendMedicationPlan(UUID patientUUID, Date currentDate) {
        List<MedicationPlan> medicationPlans = medicationPlanService.findByPatientId(patientUUID);
        for (MedicationPlan medPlan : medicationPlans) {
            if (medPlan.getStartDate().before(currentDate) && medPlan.getEndDate().after(currentDate)) {
                Set<String> medications = medPlan.getMedications().stream()
                        .map(Medication::getName)
                        .collect(Collectors.toSet());
                String intakeInterval = medPlan.getIntakeInterval();
                return new MedicationPlanInfo(intakeInterval, medications);
            }
        }
        return null;
    }

    @Override
    public void notifyMedicationTaken(String medicationName) {
        System.err.println("Medication " + medicationName + " taken");
    }
}
