package com.utcn.medicationplatform.rmi;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class MedicationPlanInfo implements Serializable {
    private String intakeInterval;
    private Set<String> medications;
}
