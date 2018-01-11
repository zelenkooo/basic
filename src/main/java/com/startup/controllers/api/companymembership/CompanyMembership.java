package com.startup.controllers.api.companymembership;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.startup.controllers.api.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "company_memberships")
public class CompanyMembership {

  @Id
  @GeneratedValue
  private Long id;

  private String companyName;

  @JsonIgnore
  @ManyToOne
  private User user;

  public CompanyMembership() {

  }

  public CompanyMembership(User user, String companyName) {
    this.user= user;
    this.companyName= companyName;
  }

  public CompanyMembership(Long id, User user, String companyName) {
    this(user,companyName);
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }


}
