package com.utcn.medicationplatform.controllers;

import com.utcn.medicationplatform.entities.User;
import com.utcn.medicationplatform.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> create(@RequestBody User user){
        log.info("POST request for saving user with id: {}", user.getId());
        UUID id = userService.save(user);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") UUID id){
        log.info("GET request for user with id: {}", id);
        Optional<User> resultDB = userService.findById(id);
        return resultDB.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<User> getByUsername(@PathVariable String username){
        log.info("GET request for user with username: {}", username);
        Optional<User> resultDB = userService.findByUsername(username);
        return resultDB.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll() {
        log.info("GET request for all users");
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<HttpStatus> updateByUsername(@PathVariable String username, @RequestBody User user){
        log.info("PUT request for user with username: {}", username);
        Optional<User> resultDB = userService.findByUsername(username);
        if(resultDB.isPresent()){
            User userDB = resultDB.get();
            user.setId(userDB.getId());
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String username){
        log.info("DELETE request for user with username: {}", username);
        userService.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable UUID id){
        log.info("DELETE request for user with username: {}", id);
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
