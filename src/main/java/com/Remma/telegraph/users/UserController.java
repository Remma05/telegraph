package com.Remma.telegraph.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(
            @RequestBody UserRequest user
    ) {
        log.info("Called method createUser");
        service.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable(name = "id") Long id
    ) {
        log.info("Called method getUserById, id = {}", id);
        return ResponseEntity.ok(service.getUserById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Void> updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UserRequest request
    ) {
        log.info("Called method updateUser, id = {}", id);
        service.updateUser(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable(name = "id") Long id
    ) {
        log.info("Called method deleteUser, id = {}", id);
        service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
