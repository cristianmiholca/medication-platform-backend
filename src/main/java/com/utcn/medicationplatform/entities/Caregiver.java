package com.utcn.medicationplatform.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
