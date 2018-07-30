package com.startup.controllers.api.companymembership;

import com.startup.controllers.api.user.UserController;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class CompanyMembershipResource extends ResourceSupport {

  private final CompanyMembership companyMembership;

  public CompanyMembershipResource(final CompanyMembership companyMembership) {
    this.companyMembership = companyMembership;
    final long companyMembershipId = companyMembership.getId();
    final long userId = companyMembership.getUser().getId();
    add(new Link(String.valueOf(companyMembershipId), "companyMembership-id"));
    add(linkTo(methodOn(CompanyMembershipController.class).all(userId)).withRel("companyMembership"));
    add(linkTo(methodOn(UserController.class).get(userId)).withRel("user"));
    add(linkTo(methodOn(CompanyMembershipController.class).get(userId, companyMembershipId)).withSelfRel());
  }
}
