package com.utcn.medicationplatform.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Caregiver extends User{

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

    @OneToMany(
            mappedBy = "caregiver",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Patient> patients;

}
