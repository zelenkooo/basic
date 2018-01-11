package com.startup.controllers.api.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.startup.controllers.api.companymembership.CompanyMembership;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String firstName;

  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<CompanyMembership> companyMemberships;

  public User() {

  }

  public User(User user) {
    this.firstName = user.firstName;
  }

  public User(Long id, String firstName) {
    this.id = id;
    this.firstName = firstName;
  }

  public User(User user, Long id) {
    this(user);
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }


  public List<CompanyMembership> getCompanyMemberships() {
    return companyMemberships;
  }

  public void setCompanyMemberships(List<CompanyMembership> companyMemberships) {
    this.companyMemberships = companyMemberships;
  }
}
