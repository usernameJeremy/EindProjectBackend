package nl.example.boodschappenbezorgapp.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.example.boodschappenbezorgapp.DTO.AccountDto;
import nl.example.boodschappenbezorgapp.Model.Account;
import nl.example.boodschappenbezorgapp.Service.AccountService;
import nl.example.boodschappenbezorgapp.Service.DatabaseService;
import nl.example.boodschappenbezorgapp.filter.JwtRequestFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import javax.servlet.http.HttpServlet;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountControllerTest {

    @MockBean
    private AccountService accountService;
    @MockBean
    HttpServlet httpServlet;
    @MockBean
    private JwtRequestFilter jwtRequestFilter;
    @MockBean
    private DatabaseService databaseService;
    @Autowired
    private MockMvc mockMvc;

    AccountDto accountDto1;
    AccountDto accountDto2;


    @BeforeEach
    void setUp() {

        accountDto1 = new AccountDto("testUsername1", "testPassword", "testApiKey", "testEmail");
        accountDto2 = new AccountDto("testUsername2", "testPassword", "testApiKey", "testEmail");

    }

    @Test
    public void getAllAccountsTest() throws Exception {
        // Arrange
        accountDto1 = new AccountDto("testUsername1", "testPassword", "testApiKey", "testEmail");
        accountDto2 = new AccountDto("testUsername2", "testPassword", "testApiKey", "testEmail");

        given(accountService.getAllAccounts()).willReturn(List.of(accountDto1, accountDto2));

        // Act and Assert
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testUsername1"))
                .andExpect(jsonPath("$[1].username").value("testUsername2"));
        verify(accountService).getAllAccounts();
    }

    @Test
    void getAccount() throws Exception {
        given(accountService.getAccount(anyString())).willReturn(accountDto2);

        mockMvc.perform(get("/accounts/testUsername2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("testUsername2"))
                .andExpect(jsonPath("name").value("testPassword"));

        verify(accountService).getAccount("testUsername2");
    }

    @Test
    void createAccount() throws Exception {
        given(accountService.createAccount(accountDto2)).willReturn("Account created!");

        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(accountDto2)))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteAccount() throws Exception{
        String account = "testAccount";

        mockMvc.perform(delete("/accounts/testAccount"))
                .andExpect(status().isOk());

        verify(accountService).deleteAccount(account);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}