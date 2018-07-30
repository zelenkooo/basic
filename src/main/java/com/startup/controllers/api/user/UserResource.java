package com.startup.controllers.api.user;

import com.startup.controllers.api.companymembership.CompanyMembershipController;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class UserResource extends ResourceSupport {

  private final User user;

  public UserResource(final User user) {
    this.user = user;
    final long id = user.getId();
    add(linkTo(UserController.class).withRel("users"));
    add(linkTo(methodOn(CompanyMembershipController.class).all(id)).withRel("companyMembership"));
    add(linkTo(methodOn(UserController.class).get(id)).withSelfRel());
  }

}

