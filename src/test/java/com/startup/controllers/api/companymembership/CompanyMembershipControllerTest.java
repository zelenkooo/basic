package com.startup.controllers.api.companymembership;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startup.controllers.api.user.User;
import com.startup.controllers.api.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(CompanyMembershipController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CompanyMembershipControllerTest {

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private CompanyMembershipRepository companyMembershipRepository;

  @Before
  public void setup() {
    setMockData();
  }

  @Test
  public void testCompanyMembershipAll() throws Exception {
    this.mockMvc.perform(get("/api/users/1/companymembership")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"companyName\":\"Intel\"}")))
                .andExpect(content().string(containsString("{\"id\":2,\"companyName\":\"AMD\"}")));
  }

  @Test
  public void testCompanyMembershipGet() throws Exception {
    this.mockMvc.perform(get("/api/users/1/companymembership/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"companyName\":\"Intel\"}")));
  }

  @Test
  public void testCompanyMembershipGet_userNotFoundException() throws Exception {
    this.mockMvc.perform(get("/api/users/99/companymembership/1")).andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("")))
                .andExpect(status().reason(containsString("User not found!")));
  }

  @Test
  public void testCompanyMembershipGet_CompanyMembershipNotFoundException() throws Exception {
    this.mockMvc.perform(get("/api/users/1/companymembership/99")).andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("")))
                .andExpect(status().reason(containsString("Company Membership not found!")));
  }

  @Test
  public void testCompanyMembershipPost() throws Exception {
    this.mockMvc.perform(post("/api/users/1/companymembership/").contentType(MediaType.APPLICATION_JSON)
                                           .content(mapper.writeValueAsString(new CompanyMembership())))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().string(containsString("{\"id\":1,\"companyName\":\"Intel\"}")));
  }


  @Test
  public void testCompanyMembershipPut() throws Exception {
    CompanyMembership companyMembership = new CompanyMembership();
    companyMembership.setId(1L);
    companyMembership.setCompanyName("Intel");
    this.mockMvc.perform(put("/api/users/1/companymembership/1").contentType(MediaType.APPLICATION_JSON)
                                            .content(mapper.writeValueAsString(companyMembership)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().string(containsString("{\"id\":1,\"companyName\":\"Intel\"}")));
  }

  @Test
  public void testCompanyMembershipPut_UserNotFoundException() throws Exception {
    CompanyMembership companyMembership = new CompanyMembership();
    companyMembership.setId(1L);
    companyMembership.setCompanyName("Intel");
    this.mockMvc.perform(put("/api/users/99/companymembership/1").contentType(MediaType.APPLICATION_JSON)
                                                                 .content(mapper.writeValueAsString(companyMembership)))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("")))
                .andExpect(status().reason(containsString("User not found!")));
  }

  @Test
  public void testCompanyMembershipPut_CompanyMembershipNotFoundException() throws Exception {
    CompanyMembership companyMembership = new CompanyMembership();
    companyMembership.setId(1L);
    companyMembership.setCompanyName("Intel");
    this.mockMvc.perform(put("/api/users/1/companymembership/99").contentType(MediaType.APPLICATION_JSON)
                                             .content(mapper.writeValueAsString(companyMembership)))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("")))
                .andExpect(status().reason(containsString("Company Membership not found!")));
  }

  @Test
  public void testCompanyMembershipDelete() throws Exception {
    this.mockMvc.perform(delete("/api/users/1/companymembership/1").contentType(MediaType.APPLICATION_JSON)
                                               .content(mapper.writeValueAsString(new User())))
                .andDo(print()).andExpect(status().isNoContent())
                .andExpect(content().string(equalTo("")));
  }

  @Test
  public void testUserDelete_UserNotFoundExceptio() throws Exception {
    this.mockMvc.perform(delete("/api/users/99/companymembership/1").contentType(MediaType.APPLICATION_JSON)
                                                .content(mapper.writeValueAsString(new User())))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("")))
                .andExpect(status().reason(containsString("User not found!")));
  }

  @Test
  public void testUserDelete_CompanyMembershipNotFoundExceptio() throws Exception {
    this.mockMvc.perform(delete("/api/users/1/companymembership/99").contentType(MediaType.APPLICATION_JSON)
                                                .content(mapper.writeValueAsString(new User())))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("")))
                .andExpect(status().reason(containsString("Company Membership not found!")));
  }

  public void setMockData() {
    User user1 = new User();
    user1.setId(1L);
    user1.setFirstName("George");
    List<CompanyMembership> companyMemberships = new LinkedList<>();
    CompanyMembership companyMembership1 = new CompanyMembership(1L,user1,"Intel");
    CompanyMembership companyMembership2 = new CompanyMembership(2L,user1,"AMD");
    companyMemberships.add(companyMembership1);
    companyMemberships.add(companyMembership2);
    user1.setCompanyMemberships(companyMemberships);

    User user2 = new User();
    user2.setId(2L);
    user2.setFirstName("Marc");

    List<User> users = new LinkedList<>();
    users.add(user1);
    users.add(user2);

    when(userRepository.findAll()).thenReturn(users);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
    when(userRepository.save(any(User.class))).thenReturn(user1);
    when(userRepository.findById(99L)).thenReturn(Optional.empty());
    when(companyMembershipRepository.save(any(CompanyMembership.class))).thenReturn(companyMembership1);
  }

}

