package com.utcn.medicationplatform.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class Patient extends User{

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "birth_date")
    @NotNull
    private Date birthDate;

    @Column(name = "gender")
    @NotNull
    private String gender;

    @Column(name = "address")
    @NotNull
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    private Caregiver caregiver;

    @OneToMany(
            mappedBy = "patient",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MedicalRecord> medicalRecords;
}
