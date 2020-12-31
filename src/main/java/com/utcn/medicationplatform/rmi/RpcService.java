package com.utcn.medicationplatform.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.UUID;

public interface RpcService extends Remote {
    MedicationPlanInfo sendMedicationPlan(UUID patientUUID, Date currentDate) throws RemoteException;
    void notifyMedicationTaken(String medicationName) throws RemoteException;
}
