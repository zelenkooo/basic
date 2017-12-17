package com.startup.controllers.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


//@Getter
//@Setter
@Entity
@Table(name = "users")
//@NoArgsConstructor
public class User {

  public User (){

  }


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String firstName;

  private String secondName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /*@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private LocalDateTime dateOfBirth;  */

  /*private String profession;

  private int salary;

  @JsonIgnore
  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
  private List<GymMembership> memberships; */

  /*public User(final User user) {
    this.firstName = user.getFirstName();
    this.secondName = user.getSecondName();
    /*this.dateOfBirth = person.getDateOfBirth();
    this.profession = person.getProfession();
    this.salary = person.getSalary();
    this.memberships = person.getMemberships();
  }                */

  /*public User(final User user, final long id) {
    this.id = id;
    this.firstName = user.getFirstName();
    this.secondName = user.getSecondName();
    /*this.dateOfBirth = person.getDateOfBirth();
    this.profession = person.getProfession();
    this.salary = person.getSalary();
    this.memberships = person.getMemberships();
  }                  */

}
