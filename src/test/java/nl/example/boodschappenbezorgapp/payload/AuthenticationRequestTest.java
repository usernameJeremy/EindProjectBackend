package nl.example.boodschappenbezorgapp.payload;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationRequestTest {

    private AuthenticationRequest authenticationRequest;

    @BeforeEach
    public void setUp() {
        authenticationRequest = Mockito.mock(AuthenticationRequest.class);
    }

    @Test
    void getUsername() {
        // Arrange
        Mockito.when(authenticationRequest.getUsername()).thenReturn("user1");

        // Act
        String username = authenticationRequest.getUsername();

        // Assert
        Mockito.verify(authenticationRequest, Mockito.times(1)).getUsername();
        assertEquals("user1", username);
    }



    @Test
    void getPassword() {
        // Arrange
        Mockito.when(authenticationRequest.getPassword()).thenReturn("secret");

        // Act
        String password = authenticationRequest.getPassword();

        // Assert
        Mockito.verify(authenticationRequest, Mockito.times(1)).getPassword();
        assertEquals("secret", password);
    }


}