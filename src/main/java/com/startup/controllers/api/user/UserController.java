package com.startup.controllers.api.user;

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
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/users", produces = "application/hal+json")
public class UserController {

  final private UserRepository userRepository;

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
   return userRepository
            .findById(id)
            .map(p -> ResponseEntity.ok(new UserResource(p)))
            .orElseThrow(() -> new UserNotFoundException());
  }

  @PostMapping
  public ResponseEntity<UserResource> post(@RequestBody final User userFromRequest) {
    final User user = userRepository.save(new User(userFromRequest));
    final URI uri =
            MvcUriComponentsBuilder.fromController(getClass())
                                   .path("/{id}")
                                   .buildAndExpand(user.getId())
                                   .toUri();
    return ResponseEntity.created(uri).body(new UserResource(user));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResource> put(
          @PathVariable("id") final long id, @RequestBody User userFromRequest) {
    final User user = new User(userFromRequest, id);
    userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    userRepository.save(user);
    final UserResource resource = new UserResource(user);
    final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.created(uri).body(resource);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") final long id) {
    return userRepository
            .findById(id)
            .map(
                    p -> {
                      userRepository.deleteById(id);
                      return ResponseEntity.noContent().build();
                    })
            .orElseThrow(() -> new UserNotFoundException());
  }
}
