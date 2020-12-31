package com.utcn.medicationplatform.jsonrpc;

import java.util.Date;
import java.util.UUID;

public interface JsonRpcService {
    MedicationPlanInfo sendMedicationPlan(UUID patientUUID, Date currentDate);
    void notifyMedicationTaken(String medicationName);
}
