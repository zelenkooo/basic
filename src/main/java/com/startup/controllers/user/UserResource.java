package com.startup.controllers.user;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserResource extends ResourceSupport {

  private final User user;

  public UserResource(final User user) {
    this.user = user;
    final long id = user.getId();
    add(linkTo(UserController.class).withRel("users"));
    //add(linkTo(methodOn(GymMembershipController.class).all(id)).withRel("memberships"));
    add(linkTo(methodOn(UserController.class).get(id)).withSelfRel());
  }

}

