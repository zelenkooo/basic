package com.startup.controllers;

import com.startup.VO.ResponseVO;
import com.startup.model.User;
import com.startup.server.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO<Iterable<User>>> findAll() {
        return ResponseEntity.ok(new ResponseVO<>(userService.findAll()));
    }

    @GetMapping(value = "/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO<User>> get(@PathVariable("user_id") String userId) {
        return ResponseEntity.ok(new ResponseVO<>(userService.get(userId)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO<User>> createUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        return ResponseEntity.created(URI.create("/" + savedUser.getUserId())).body(new ResponseVO<>(savedUser));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<ResponseVO<String>> delete(@PathVariable("userId") String userId) {
        userService.delete(userId);
        return ResponseEntity.ok(new ResponseVO<>(userId));
    }
}
