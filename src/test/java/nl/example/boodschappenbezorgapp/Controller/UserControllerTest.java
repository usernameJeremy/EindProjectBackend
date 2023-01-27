package nl.example.boodschappenbezorgapp.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.example.boodschappenbezorgapp.Model.Authority;
import nl.example.boodschappenbezorgapp.Repository.UserRepository;
import nl.example.boodschappenbezorgapp.Service.AccountService;
import nl.example.boodschappenbezorgapp.Service.CustomUserDetailsService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nl.example.boodschappenbezorgapp.DTO.UserDto;
import nl.example.boodschappenbezorgapp.Model.User;
import nl.example.boodschappenbezorgapp.Service.UserService;
import nl.example.boodschappenbezorgapp.filter.JwtRequestFilter;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import javax.servlet.http.HttpServlet;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {



    @MockBean
    private UserService userService;
    @MockBean
    AccountService accountService;
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private Authority authority;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    HttpServlet httpServlet;
    @MockBean
    JwtRequestFilter jwtRequestFilter;

    @Autowired
    private MockMvc mockMvc;

    private UserController userController;
    @BeforeEach
    public void setUp1() {
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    User gebruiker1;
    User gebruiker2;
    User gebruiker3;
    UserDto userDto4;
    UserDto userDto5;
    UserDto userDto6;
    UserDto userDto7;
    UserDto userDto8;
    UserDto userDto9;


    @BeforeEach
    void setUp() {

         gebruiker1 = new User("testUsername1", "testPassword", true, "testApiKey", "testEmail", null, null);
         gebruiker2 = new User("testUsername2", "testPassword", true, "testApiKey", "testEmail", null, null);
         gebruiker3 = new User("testUsername3", "testPassword", true, "testApiKey", "testEmail", null, null);

         userDto4 = new UserDto("testUsername1", "testPassword", true, "testApiKey", "testEmail");
         userDto5 = new UserDto("testUsername2", "testPassword", true, "testApiKey", "testEmail");
         userDto6 = new UserDto("testUsername3", "testPassword", true, "testApiKey", "testEmail");
         userDto7 = new UserDto("testUsername4", "testPassword", true, "testApiKey", "testEmail");

         userDto8 = new UserDto("testUsername1", "testPassword", true, "testApiKey", "testEmail");
         userDto9 = new UserDto("testUsername2", "testPassword", true, "testApiKey", "testEmail");


    }

    @Test
    public void ShouldReturnListOfUserDtos() throws Exception {
        // Arrange
        given(userService.getUsers()).willReturn(List.of(userDto4, userDto5));

        // Act and Assert
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testUsername1"))
                .andExpect(jsonPath("$[1].username").value("testUsername2"));
    }


    @Test
    public void ShouldReturnUser() throws Exception {
        // Arrange

        given(userService.getUser(anyString())).willReturn(userDto7);

        // Act  // Assert
        mockMvc.perform(get("/users/testUsername4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("testUsername4"))
                .andExpect(jsonPath("password").value("testPassword"));


    }


    @Test
    void createUserTest() throws Exception {
        // Arrange
        userDto8 = new UserDto();
        String newUsername = "testUsername1";
        given(userService.createUser(userDto8)).willReturn(newUsername);

        // Act
         mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto8)))
                .andExpect(status().isCreated())
                .andReturn();

        // Assert
        verify(userService).createUser(userDto8);
        verify(userService).addAuthority(newUsername, "ROLE_USER");
    }

    @Test
    void updateUser() throws Exception {
        // Arrange
        String username = "testUsername1";
        userDto4 = new UserDto();
        gebruiker1 = new User();
        given(userService.findById(username)).willReturn(Optional.of(gebruiker1));

        // Act
        mockMvc.perform(put("/users/testUsername1", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto4)))
                .andExpect(status().isNoContent());

        // Assert
        verify(userService).updateUser(username, userDto4);
    }

    @Test
    void deleteUser() throws Exception {
        // Arrange
        String username = "testUsername1";

        // Act
        mockMvc.perform(delete("/users/testUsername1", username))
                .andExpect(status().isNoContent());

        // Assert
        verify(userService).deleteUser(username);
    }

    @Test
    void getUserAuthorities() throws Exception {

            // Arrange
            String username = "testUsername1";
            List<String> expectedAuthorities = List.of("ROLE_USER", "ROLE_ADMIN");
            given(userService.getAuthorities(username)).willAnswer(invocationOnMock ->  Set.copyOf(expectedAuthorities));

            // Act
            mockMvc.perform(get("/users/{username}/authorities", username));

            //Assert
            verify(userService).getAuthorities(username);
    }

    @Test
    void shouldAddAuthority() throws Exception {
        // Arrange
        String username = "johndoe";
        String authority = "ROLE_ADMIN";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("authority", authority);
        String requestBodyJson = new ObjectMapper().writeValueAsString(requestBody);

        // Act
        mockMvc.perform(post("/users/{username}/authorities", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyJson))
                .andExpect(status().isNoContent());

        // Assert
        verify(userService).addAuthority(username, authority);
    }

//    @Test
//    public void deleteUserAuthorityTest() throws Exception {
//    // Arrange
//    String username = "testUsername1";
//
//    new Authority authority = new String("ROLE_USER");
//    when(userRepository.findById(username)).thenReturn(Optional.of(gebruiker1.addAuthority("ROLE_USER")));
//
//
//        // Act
//    mockMvc.perform(delete("/" + username + "/authorities/" + authority))
//            .andExpect(status().isNoContent());
//    // Assert
//    verify(userService).removeAuthority(username, authority);
//}


}
