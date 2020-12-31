package com.utcn.medicationplatform.jsonrpc;

import org.json.JSONObject;

public class Utils {

    public static JSONObject sendMedPlanJsonResponse(MedicationPlanInfo medicationPlanInfo) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("return_type", "MedicationPlanInfo");
        jsonObject.put("intake_interval", medicationPlanInfo.getIntakeInterval());
        jsonObject.put("medications", medicationPlanInfo.getMedications());
        return jsonObject;
    }

    public static JSONObject notifyMedTakenJsonResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("return_type", "void");
        jsonObject.put("response", "Medication taken");
        return jsonObject;
    }

}
