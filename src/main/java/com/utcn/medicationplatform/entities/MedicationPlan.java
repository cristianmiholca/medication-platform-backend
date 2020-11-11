package com.utcn.medicationplatform.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "medication_plan")
public class MedicationPlan {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "start_date")
    @NotNull
    private Date startDate;

    @Column(name = "end_date")
    @NotNull
    private Date endDate;

    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "intake_interval")
    @NotNull
    private String intakeInterval;

    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "medicationplan_medication",
            joinColumns = @JoinColumn(name = "medicationplan_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id"))
    private Set<Medication> medications;
}
