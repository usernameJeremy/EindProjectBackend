package nl.example.boodschappenbezorgapp.Service;

import nl.example.boodschappenbezorgapp.DTO.AccountDto;
import nl.example.boodschappenbezorgapp.Model.Account;
import nl.example.boodschappenbezorgapp.Model.User;
import nl.example.boodschappenbezorgapp.Repository.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;
    @InjectMocks
    AccountService accountService;

    @Test
    public void testCreateAccount() {
        // Arrange
        AccountDto accountDto = new AccountDto();
        accountDto.setUsername("testuser");
        accountDto.setName("Test");
        accountDto.setLastName("User");
        accountDto.setAddress("123 Test");
        accountDto.setUser(new User());

        Account newAccount = new Account();
        newAccount.setUsername("testuser");
        newAccount.setName("Test");
        newAccount.setLastName("User");
        newAccount.setAddress("123 Test St");
        newAccount.setUser(new User());

        AccountRepository accountRepository = mock(AccountRepository.class);
        accountService.setAccountRepository(accountRepository);

        when(accountRepository.save(any(Account.class))).thenReturn(newAccount);
        when(accountRepository.findById(accountDto.getUsername())).thenReturn(Optional.of(newAccount));
        //Act
        String username = accountService.createAccount(accountDto);

        // Assert
        assertEquals("testuser", username);
    }
    @Test
    void getAllAccounts() {
        User user = new User();
        User user1 = new User();
        AccountDto accountDto = new AccountDto();
        accountDto.setUsername("testuser");
        accountDto.setName("Test");
        accountDto.setLastName("User");
        accountDto.setAddress("123 Test");
        accountDto.setUser(user);

        Account newAccount = new Account();
        newAccount.setUsername("testuser");
        newAccount.setName("Test");
        newAccount.setLastName("User");
        newAccount.setAddress("123 Test");
        newAccount.setUser(user);

        AccountDto accountDto1 = new AccountDto();
        accountDto1.setUsername("testuser1");
        accountDto1.setName("Test1");
        accountDto1.setLastName("User1");
        accountDto1.setAddress("123 Test");
        accountDto1.setUser(user1);

        Account newAccount1 = new Account();
        newAccount1.setUsername("testuser1");
        newAccount1.setName("Test1");
        newAccount1.setLastName("User1");
        newAccount1.setAddress("123 Test");
        newAccount1.setUser(user1);


        List<Account> actualList = new ArrayList<>();
        actualList.add(newAccount);
        actualList.add(newAccount1);

        List<AccountDto> expectedList = new ArrayList<>();
        expectedList.add(accountDto);
        expectedList.add(accountDto1);

        when(accountRepository.findAll()).thenReturn(actualList);

        List<AccountDto> foundLists = (List<AccountDto>) accountService.getAllAccounts();

        assertEquals(expectedList.get(0).getName(), foundLists.get(0).getName());
        assertEquals(expectedList.get(0).getAddress(), foundLists.get(0).getAddress());
        assertEquals(expectedList.get(0).getLastName(), foundLists.get(0).getLastName());
        assertEquals(expectedList.get(0).getUsername(), foundLists.get(0).getUsername());

        assertEquals(expectedList.get(1).getName(), foundLists.get(1).getName());
        assertEquals(expectedList.get(1).getAddress(), foundLists.get(1).getAddress());
        assertEquals(expectedList.get(1).getLastName(), foundLists.get(1).getLastName());
        assertEquals(expectedList.get(1).getUsername(), foundLists.get(1).getUsername());
    }
    @Test
    void getAccount() {
        Account newAccount = new Account();
        User user = new User();
        newAccount.setUsername("testuser");
        newAccount.setName("Test");
        newAccount.setLastName("User");
        newAccount.setAddress("123 Test 1");
        newAccount.setUser(user);

        when(accountRepository.findById("testId")).thenReturn(Optional.of(newAccount));
        AccountDto accountDto = accountService.getAccount("testId");

        assertEquals("testuser", accountDto.getUsername());
        assertEquals("Test", accountDto.getName());
        assertEquals("User", accountDto.getLastName());
        assertEquals("123 Test 1", accountDto.getAddress());
        assertEquals(user, accountDto.getUser());

    }
    @Test
    void deleteAccount() {
        Account newAccount = new Account();
        newAccount.setUsername("testuser");
        newAccount.setName("Test");
        newAccount.setLastName("User");
        newAccount.setAddress("123 Test 1");
        newAccount.setUser(new User());
        when(accountRepository.findById("testId")).thenReturn(Optional.of(newAccount));

        String result = accountService.deleteAccount("testId");

        assertEquals(result, "Account With " + "testId" + " removed");
    }

}