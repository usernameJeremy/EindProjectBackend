package nl.example.boodschappenbezorgapp.Service;


import nl.example.boodschappenbezorgapp.DTO.AccountDto;
import nl.example.boodschappenbezorgapp.DTO.GroceryListDto;
import nl.example.boodschappenbezorgapp.DTO.UserDto;
import nl.example.boodschappenbezorgapp.Model.Account;
import nl.example.boodschappenbezorgapp.Model.Authority;
import nl.example.boodschappenbezorgapp.Model.GroceryList;
import nl.example.boodschappenbezorgapp.Model.User;
import nl.example.boodschappenbezorgapp.Repository.AccountRepository;
import nl.example.boodschappenbezorgapp.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest  {

    @InjectMocks
     UserService userService;
    @Mock
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;
    @Mock
    AccountRepository accountRepository;

    @Mock
    User user;
    @Mock
    UserDto userDto;

    User user1;
    User user2;
    User user3;
    UserDto userDto1;
    UserDto userDto2;
    UserDto userDto3;
    UserDto userDto4;
    UserDto userDto5;
    UserDto userDto6;
    @BeforeEach
    void setUp(){
        user1 = new User("testUsername1", "testPasword1", true, "testApiKey","test@test.nl",null, null);
        user2 = new User("testUsername2", "testPasword2", true, "testApiKey","test@test.nl", null , null);
        user3 = new User("testUsername3", "testPasword3", true, "testApiKey","test@test.nl", null ,null);

        userRepository.save(user3);

        userDto1 = new UserDto("testUsername1", "testPasword1",  true,  "testApiKey","test@test.nl");
        userDto2 = new UserDto("testUsername2", "testPasword2",true , "testApiKey", "test@test.nl");
        userDto3 = new UserDto("testUsername3", "testPasword3", true, "testApiKey", "test@test.nl");
        userDto4 = new UserDto("testUsername4", "testPasword4", true, "testApiKey", "test@test.nl");
        userDto5 = new UserDto("testUsername1", "testPasword5",  true, "testApiKey", "test@test.nl");
        userDto6 = new UserDto("testUsername2", "testPasword6", true, "testApiKey", "test@test.nl");

    }



    @Test
    void getUsers() {
        List<User> actualList = new ArrayList<>();
        actualList.add(user1);
        actualList.add(user2);

        List<UserDto> expectedList = new ArrayList<>();
        expectedList.add(userDto1);
        expectedList.add(userDto2);

        Mockito.when(userRepository.findAll()).thenReturn(actualList);

        List<UserDto> findUsers = userService.getUsers();

        assertEquals( expectedList, findUsers);
     }

    @Test
    void getUser() {

        when(userRepository.findById("testUsername3")).thenReturn(Optional.of(user3));
        UserDto userDto = userService.getUser("testUsername3");

        assertEquals("testUsername3", userDto.getUsername());
        assertEquals("testPasword3", userDto.getPassword());
        assertEquals(true, userDto.getEnabled());
        assertEquals("testApiKey", userDto.getApikey());
        assertEquals("test@test.nl", userDto.getEmail());

    }

    @Test
    void deleteUser() {
        String username = "testUsername";
        User expectedUser = new User("testUsername", "encodedpassword", true, "testApiKey", "email@example.com", null, null);
        when(userRepository.findById(username)).thenReturn(Optional.of(expectedUser));

        userService.deleteUser(username);

        verify(userRepository).deleteById(username);
    }

    @Test
    void fromUser() {
        //Arrange
        when(user.getUsername()).thenReturn("testUsername1");
        when(user.getPassword()).thenReturn("testAddress1");
        when(user.isEnabled()).thenReturn(true);
        when(user.getApikey()).thenReturn("testApiKey");
        when(user.getEmail()).thenReturn("test@test.nl");

        //Act
        UserDto userdto = UserService.fromUser(user);

        //Assert
        assertEquals("testUsername1", userdto.getUsername());
        assertEquals("testAddress1", userdto.getPassword());
        assertEquals(true, userdto.getEnabled());
        assertEquals("testApiKey", userdto.getApikey());
        assertEquals("test@test.nl", userdto.getEmail());
    }

    @Test
    void toUser() {
        //Arrange
        when(userDto.getUsername()).thenReturn("testUsername1");
        when(userDto.getPassword()).thenReturn("testAddress1");
        when(userDto.getEnabled()).thenReturn(true);
        when(userDto.getApikey()).thenReturn("testApiKey");
        when(userDto.getEmail()).thenReturn("test@test.nl");

        //Act
        User user = UserService.toUser(userDto);

        //Assert
        assertEquals("testUsername1", user.getUsername());
        assertEquals("testAddress1", user.getPassword());
        assertTrue(user.isEnabled());
        assertEquals("testApiKey", user.getApikey());
        assertEquals("test@test.nl", user.getEmail());

        }


    @Test
    void assignAccountToUser() {
        //Arrange
        Account account = new Account();
        account.setUsername("nakijker");
        account.setAddress("vind");
        account.setLastName("je het");
        account.setName("leuk");


        User user = new User();
        user.setUsername("nakijker");
        user.setPassword("zegt");
        user.setEmail("dat is");
        user.setEnabled(true);

        when(accountRepository.findById("appel")).thenReturn(Optional.of(account));
        when(userRepository.findById("banaan")).thenReturn(Optional.of(user));
        //act
        UserService.assignAccountToUser("banaan","appel");

        //Assert
        assertEquals("nakijker", account.getUsername());
        assertEquals("zegt", user.getPassword());
        assertEquals("dat is", user.getEmail());
        assertTrue(user.isEnabled());

    }

}