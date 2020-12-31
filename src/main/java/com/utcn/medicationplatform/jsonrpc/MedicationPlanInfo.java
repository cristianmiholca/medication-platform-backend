package com.utcn.medicationplatform.jsonrpc;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class MedicationPlanInfo implements Serializable {
    private final String intakeInterval;
    private final Set<String> medications;
}
