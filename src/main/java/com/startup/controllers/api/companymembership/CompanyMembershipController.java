package com.startup.controllers.api.companymembership;

import com.startup.controllers.api.user.User;
import com.startup.controllers.api.user.UserNotFoundException;
import com.startup.controllers.api.user.UserRepository;
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
@RequestMapping(value = "/api/users/{userId}/companymembership", produces = "application/hal+json")
public class CompanyMembershipController {

  final private CompanyMembershipRepository companyMembershipRepository;

  final private UserRepository userRepository;

  public CompanyMembershipController(final UserRepository userRepository,
                                     CompanyMembershipRepository companyMembershipRepository) {
    this.userRepository = userRepository;
    this.companyMembershipRepository = companyMembershipRepository;
  }

  @GetMapping
  public ResponseEntity<Resources<CompanyMembershipResource>> all(@PathVariable final long userId) {
    final List<CompanyMembershipResource> collection = getCompanyMembershipsForUser(userId);
    final Resources<CompanyMembershipResource> resources = new Resources<>(collection);
    final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
    resources.add(new Link(uriString, "self"));
    return ResponseEntity.ok(resources);
  }

  @GetMapping("/{companyMembershipId}")
  public ResponseEntity<CompanyMembershipResource> get(
          @PathVariable final long userId, @PathVariable final long companyMembershipId) {
    return userRepository
            .findById(userId)
            .map(
                    p ->
                            p.getCompanyMemberships()
                             .stream()
                             .filter(cm -> cm.getId().equals(companyMembershipId))
                             .findAny()
                             .map(cm -> ResponseEntity.ok(new CompanyMembershipResource(cm)))
                             .orElseThrow(() -> new CompanyMembershipNotFoundException()))
            .orElseThrow(() -> new UserNotFoundException());
  }

  @PostMapping
  public ResponseEntity<CompanyMembershipResource> post(
          @PathVariable final long userId, @RequestBody final CompanyMembership inputCompanyMembership) {
    return userRepository
            .findById(userId)
            .map(
                    p -> {
                      final CompanyMembership companyMembership = saveMembership(p, inputCompanyMembership);
                      final URI uri = createPostUri(companyMembership);
                      return ResponseEntity.created(uri).body(new CompanyMembershipResource(companyMembership));
                    })
            .orElseThrow(() -> new UserNotFoundException());
  }

  private CompanyMembership saveMembership(final User user, final CompanyMembership companyMembership) {
    return companyMembershipRepository.save(
            new CompanyMembership(user, companyMembership.getCompanyName()));
  }

  private URI createPostUri(final CompanyMembership companyMembership) {
    return MvcUriComponentsBuilder.fromController(getClass())
                                  .path("/{membershipId}")
                                  .buildAndExpand(companyMembership.getUser().getId(), companyMembership.getId())
                                  .toUri();
  }

  @PutMapping("/{companyMembershipId}")
  public ResponseEntity<CompanyMembershipResource> put(
          @PathVariable final long userId,
          @PathVariable final long companyMembershipId,
          @RequestBody final CompanyMembership inputCompanyMembership) {
    return userRepository
            .findById(userId)
            .map(
                    u -> {
                      u.getCompanyMemberships().stream()
                       .filter(c -> c.getId().equals(companyMembershipId)).findAny()
                       .orElseThrow(() -> new CompanyMembershipNotFoundException());
                      final CompanyMembership membership =
                              updateMembership(u, companyMembershipId, inputCompanyMembership);
                      final URI uri =
                              URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
                      return ResponseEntity.created(uri).body(new CompanyMembershipResource(membership));
                    })
            .orElseThrow(() -> new UserNotFoundException());
  }

  private CompanyMembership updateMembership(
          final User user, final long id, final CompanyMembership companyMembership) {
    return companyMembershipRepository.save(
            new CompanyMembership(id, user, companyMembership.getCompanyName()));
  }

  @DeleteMapping("/{companyMembershipId}")
  public ResponseEntity<?> delete(
          @PathVariable final long userId, @PathVariable final long companyMembershipId) {
    return userRepository
            .findById(userId)
            .map(
                    u ->
                            u.getCompanyMemberships()
                             .stream()
                             .filter(cm -> cm.getId().equals(companyMembershipId))
                             .findAny()
                             .map(
                                     cm -> {
                                       companyMembershipRepository.delete(cm);
                                       return ResponseEntity.noContent().build();
                                     })
                             .orElseThrow(() -> new CompanyMembershipNotFoundException()))
            .orElseThrow(() -> new UserNotFoundException());
  }


  private List<CompanyMembershipResource> getCompanyMembershipsForUser(final long userId) {
    return userRepository
            .findById(userId)
            .map(
                    p ->
                            p.getCompanyMemberships()
                             .stream()
                             .map(CompanyMembershipResource::new)
                             .collect(Collectors.toList()))
            .orElseThrow(() -> new UserNotFoundException());
  }
}
