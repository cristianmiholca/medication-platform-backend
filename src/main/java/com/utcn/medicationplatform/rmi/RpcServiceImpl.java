package com.utcn.medicationplatform.rmi;

import com.utcn.medicationplatform.entities.Medication;
import com.utcn.medicationplatform.entities.MedicationPlan;
import com.utcn.medicationplatform.services.MedicationPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RpcServiceImpl extends UnicastRemoteObject implements RpcService {

    private static final String RPC_NAME = "rmi://localhost:1900/rpc";
    private final MedicationPlanService medicationPlanService;

    @Autowired
    protected RpcServiceImpl(MedicationPlanService medicationPlanService) throws RemoteException, MalformedURLException {
        super();
        this.medicationPlanService = medicationPlanService;
        LocateRegistry.createRegistry(1900);
        Naming.rebind(RPC_NAME, this);
    }

    @Override
    public MedicationPlanInfo sendMedicationPlan(UUID patientUUID, Date currentDate) throws RemoteException {
        List<MedicationPlan> medicationPlans = medicationPlanService.findByPatientId(patientUUID);
        for (MedicationPlan medPlan : medicationPlans) {
            if (medPlan.getStartDate().before(currentDate) && medPlan.getEndDate().after(currentDate)) {
                MedicationPlanInfo medicationPlanInfo = new MedicationPlanInfo();
                medicationPlanInfo.setMedications(medPlan.getMedications().stream()
                        .map(Medication::getName)
                        .collect(Collectors.toSet()));
                medicationPlanInfo.setIntakeInterval(medPlan.getIntakeInterval());
                return medicationPlanInfo;
            }
        }
        return null;
    }

    @Override
    public void notifyMedicationTaken(String medicationName) throws RemoteException {
        System.err.println("Medication " + medicationName + " taken");
    }
}
