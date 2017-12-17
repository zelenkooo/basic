package com.startup.controllers.user;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

/*@RestController
public class UsersController {

  @RequestMapping(value = "/api/users", method = RequestMethod.GET)
  public String getAllUsers() {
    return "index";
  }

  @RequestMapping(value = "/api/users", method = RequestMethod.POST)
  public String createNewUser() {
    return "index";
  }

  @RequestMapping(value = "/api/users/{id}", method = RequestMethod.GET)
  public String getUser() {
    return "index";
  }

  @RequestMapping(value = "/api/users/{id}", method = RequestMethod.PUT)
  public String updateUser() {
    return "index";
  }

  @RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE)
  public String deleteUser() {
    return "index";
  }
}     */


@RestController
@RequestMapping(value = "/api/users", produces = "application/hal+json")
public class UserController {

  final UserRepository userRepository;

  public UserController(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping
  public ResponseEntity<Resources<UserResource>> all() {
    final List<UserResource> collection =
            userRepository.findAll().stream().map(UserResource::new).collect(Collectors.toList());
    final Resources<UserResource> resources = new Resources<>(collection);
    final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
    resources.add(new Link(uriString, "self"));
    return ResponseEntity.ok(resources);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResource> get(@PathVariable final long id) {
    // GET
    return null;
  }

  @PostMapping
  public ResponseEntity<UserResource> post(@RequestBody final User userFromRequest) {
    // POST
    return null;
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResource> put(
          @PathVariable("id") final long id, @RequestBody User userFromRequest) {
    // PUT
    return null;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") final long id) {
    // DELETE
    return null;
  }
}
