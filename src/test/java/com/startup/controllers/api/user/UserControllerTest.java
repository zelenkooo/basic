package com.startup.controllers.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
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
@WebMvcTest(UserController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTest {

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserRepository userRepository;

  @Before
  public void setup() {
    setMockData();
  }

  @Test
  public void testUserAll() throws Exception {
    this.mockMvc.perform(get("/api/users")).andDo(print()).andExpect(status().isOk())
                //TODO added proper json path asserts
                //.andExpect(jsonPath("$..id").value("1"))

                .andExpect(content().string(containsString("{\"user\":{\"id\":1,\"firstName\":\"George\"}")))
                .andExpect(content().string(containsString("{\"user\":{\"id\":2,\"firstName\":\"Marc\"}")));
  }

  @Test
  public void testUserGet() throws Exception {
    this.mockMvc.perform(get("/api/users/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"user\":{\"id\":1,\"firstName\":\"George\"}")));
  }

  @Test
  public void testUserGet_notFoundException() throws Exception {
    this.mockMvc.perform(get("/api/users/99")).andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("")))
                .andExpect(status().reason(containsString("User not found!")));
  }

  @Test
  public void testUserPost() throws Exception {
    this.mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
                                           .content(mapper.writeValueAsString(new User())))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().string(containsString("{\"user\":{\"id\":1,\"firstName\":\"George\"}")));
  }


  @Test
  public void testUserPut() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setFirstName("George");
    this.mockMvc.perform(put("/api/users/1").contentType(MediaType.APPLICATION_JSON)
                                            .content(mapper.writeValueAsString(user)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().string(containsString("{\"user\":{\"id\":1,\"firstName\":\"George\"}")));
  }

  @Test
  public void testUserPut_notFoundException() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setFirstName("George");
    this.mockMvc.perform(put("/api/users/99").contentType(MediaType.APPLICATION_JSON)
                                             .content(mapper.writeValueAsString(user)))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("")))
                .andExpect(status().reason(containsString("User not found!")));
  }

  @Test
  public void testUserDelete() throws Exception {
    this.mockMvc.perform(delete("/api/users/1").contentType(MediaType.APPLICATION_JSON)
                                               .content(mapper.writeValueAsString(new User())))
                .andDo(print()).andExpect(status().isNoContent())
                .andExpect(content().string(equalTo("")));
  }

  @Test
  public void testUserDelete_notFoundExceptio() throws Exception {
    this.mockMvc.perform(delete("/api/users/99").contentType(MediaType.APPLICATION_JSON)
                                                .content(mapper.writeValueAsString(new User())))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("")))
                .andExpect(status().reason(containsString("User not found!")));
  }

  public void setMockData() {
    User user1 = new User();
    user1.setId(1L);
    user1.setFirstName("George");

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
  }

}
