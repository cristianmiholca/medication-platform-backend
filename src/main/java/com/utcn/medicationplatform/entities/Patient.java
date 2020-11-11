package com.utcn.medicationplatform.entities;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@OnDelete(action = OnDeleteAction.CASCADE)
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

    @ManyToOne(fetch = FetchType.EAGER)
    private Caregiver caregiver;

}
