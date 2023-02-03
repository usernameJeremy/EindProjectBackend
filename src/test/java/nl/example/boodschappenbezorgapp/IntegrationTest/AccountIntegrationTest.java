package nl.example.boodschappenbezorgapp.IntegrationTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.example.boodschappenbezorgapp.DTO.AccountDto;
import nl.example.boodschappenbezorgapp.Model.Account;
import nl.example.boodschappenbezorgapp.Repository.AccountRepository;
import nl.example.boodschappenbezorgapp.Service.AccountService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class AccountIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountService accountService;
    @Autowired
    AccountRepository accountRepository;
    Account gebruiker1;
    Account gebruiker2;
    Account gebruiker3;
    AccountDto accountDto1;

    @BeforeEach
    void setUp() {

        gebruiker1 = new Account("Geef", "Mij AUB", "een", "Goed cijfer");
        gebruiker2 = new Account("testUsername2", "testPassword", "testApiKey", "testEmail");
        gebruiker3 = new Account("testUsername3", "testPassword", "testApiKey", "testEmail");

        accountRepository.save(gebruiker1);
        accountRepository.save(gebruiker2);
        accountRepository.save(gebruiker3);

        accountDto1 = new AccountDto("testUsername1", "testPassword", "testApiKey", "testEmail");
    }
    @Test
    public void ShouldGetAllAccounts() throws Exception {

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("bas"))
                .andExpect(jsonPath("$[1].username").value("jerry"));

    }
    @Test
    void getAccount() throws Exception {

        mockMvc.perform(get("/accounts/"+ gebruiker1.getUsername() ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("Geef"))
                .andExpect(jsonPath("name").value("Mij AUB"))
                .andExpect(jsonPath("lastName").value("een"))
                .andExpect(jsonPath("address").value("Goed cijfer"));
    }

    @Test
    void ShouldcreateAccount() throws Exception {

        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(accountDto1)))
                .andExpect(status().isCreated());
    }


    @Test
    void ShoulddeleteAccount() throws Exception{


        mockMvc.perform(delete("/accounts/"+ gebruiker3.getUsername()))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}