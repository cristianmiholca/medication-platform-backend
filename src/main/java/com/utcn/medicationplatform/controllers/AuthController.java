package com.utcn.medicationplatform.controllers;

import com.utcn.medicationplatform.entities.*;
import com.utcn.medicationplatform.payload.*;
import com.utcn.medicationplatform.security.jwt.JwtUtils;
import com.utcn.medicationplatform.security.services.UserDetailsImpl;
import com.utcn.medicationplatform.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    PatientService patientService;

    @Autowired
    CaregiverService caregiverService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.err.println(loginRequest.getUsername() + " " + loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup/patient")
    public ResponseEntity<?> registerPatient(@RequestBody PatientSignupRequest patientSignupRequest){
        if(userService.existsByUsername(patientSignupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        Role role = getRoleFromDB(ERole.ROLE_PATIENT);

        Patient patient = new Patient();
        patient.setUsername(patientSignupRequest.getUsername());
        patient.setPassword(passwordEncoder.encode(patientSignupRequest.getPassword()));
        patient.setRoles(new HashSet<>(){{add(role);}});
        patient.setName(patientSignupRequest.getName());
        patient.setGender(patientSignupRequest.getGender());
        patient.setAddress(patientSignupRequest.getAddress());
        patient.setBirthDate(stringToDate(patientSignupRequest.getBirthDate()));
        if(patientSignupRequest.getCaregiverId() != null){
            Caregiver caregiver = caregiverService.findById(patientSignupRequest.getCaregiverId())
                    .orElseThrow(() -> new RuntimeException("Cannot find caregiver with given id!"));
            patient.setCaregiver(caregiver);
        }

        patientService.save(patient);
        return ResponseEntity.ok(new MessageResponse("Patient registered successfully!"));
    }

    @PostMapping("/signup/caregiver")
    public ResponseEntity<?> registerCaregiver(@Valid @RequestBody CaregiverSignupRequest caregiverSignupRequest) {
        if(userService.existsByUsername(caregiverSignupRequest.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        Role role = getRoleFromDB(ERole.ROLE_CAREGIVER);

        Caregiver caregiver = new Caregiver();
        caregiver.setUsername(caregiverSignupRequest.getUsername());
        caregiver.setPassword(passwordEncoder.encode(caregiverSignupRequest.getPassword()));
        caregiver.setRoles(new HashSet<>(){{add(role);}});
        caregiver.setName(caregiverSignupRequest.getName());
        caregiver.setGender(caregiverSignupRequest.getGender());
        caregiver.setAddress(caregiverSignupRequest.getAddress());
        caregiver.setBirthDate(stringToDate(caregiverSignupRequest.getBirthDate()));

        caregiverService.save(caregiver);
        return ResponseEntity.ok(new MessageResponse("Caregiver registered successfully!"));
    }

    @PostMapping("/signup/doctor")
    public ResponseEntity<?> registerDoctor(@Valid @RequestBody DoctorSignupRequest doctorSignupRequest) {
        if(userService.existsByUsername(doctorSignupRequest.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        Role role = getRoleFromDB(ERole.ROLE_DOCTOR);

        Doctor doctor = new Doctor();
        doctor.setUsername(doctorSignupRequest.getUsername());
        doctor.setPassword(passwordEncoder.encode(doctorSignupRequest.getPassword()));
        doctor.setRoles(new HashSet<>(){{add(role);}});
        doctor.setName(doctorSignupRequest.getName());
        doctor.setAddress(doctorSignupRequest.getAddress());
        doctor.setBirthDate(stringToDate(doctorSignupRequest.getBirthDate()));

        doctorService.save(doctor);
        return ResponseEntity.ok(new MessageResponse("Doctor registered successfully!"));
    }

    private Role getRoleFromDB(ERole eRole){
        return roleService.findByName(eRole)
                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
    }

    private Date stringToDate(String date){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}
