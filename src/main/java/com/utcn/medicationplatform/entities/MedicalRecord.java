package com.utcn.medicationplatform.entities;

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
@Table(name = "medical_record")
public class MedicalRecord {

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
    private int intakeInterval;

    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;

    @ManyToMany(cascade = {
        CascadeType.PERSIST, CascadeType.MERGE
    })
    @JoinTable(name = "medical_record-medication",
    joinColumns = @JoinColumn(name = "medical_record_id"),
    inverseJoinColumns = @JoinColumn(name = "medication_id"))
    private Set<Medication> medications;
}
