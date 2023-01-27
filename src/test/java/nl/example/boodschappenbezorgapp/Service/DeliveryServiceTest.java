package nl.example.boodschappenbezorgapp.Service;

import nl.example.boodschappenbezorgapp.DTO.DeliveryDto;
import nl.example.boodschappenbezorgapp.DTO.GroceryListDto;
import nl.example.boodschappenbezorgapp.Enum.DeliveryRequestEnum;
import nl.example.boodschappenbezorgapp.Exceptions.RecordNotFoundException;
import nl.example.boodschappenbezorgapp.Model.Account;
import nl.example.boodschappenbezorgapp.Model.Delivery;
import nl.example.boodschappenbezorgapp.Model.GroceryList;
import nl.example.boodschappenbezorgapp.Repository.AccountRepository;
import nl.example.boodschappenbezorgapp.Repository.DeliveryRepository;
import nl.example.boodschappenbezorgapp.Repository.GroceryListRepository;
import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DeliveryServiceTest {

    @Mock
    GroceryListRepository groceryListRepository;
    @Mock
    DeliveryRepository deliveryRepository;
    @Mock
    AccountRepository accountRepository;
    @Mock
    Delivery delivery;

    @InjectMocks
    DeliveryService deliveryService;

    @Test
    void getAllDeliveryRequests() {
        // Arrange
        Delivery delivery1 = new Delivery();
        delivery1.setAddress("testAddress");
        Delivery delivery2 = new Delivery();
        delivery2.setAddress("testAddress1");
        List<Delivery> actualdeliveryList = new ArrayList<>();

        DeliveryDto deliveryDto1 = new DeliveryDto();
        deliveryDto1.setAddress("testAddress");
        Delivery deliveryDto2 = new Delivery();
        deliveryDto2.setAddress("testAddress1");
        List<DeliveryDto> expectedDeliveryList = new ArrayList<>();

        when(deliveryRepository.findAll()).thenReturn(actualdeliveryList);

        // Act
        Iterable<DeliveryDto> result = deliveryService.getAllDeliveryRequests();

        // Assert
         assertEquals(result, expectedDeliveryList);
    }

    @Test
    void getDeliveryRequest() {
        Delivery delivery = new Delivery();
        delivery.setAddress("watBenIkKlaarMetAlDieTesten");
        when(deliveryRepository.findById("wanneer 50%?")).thenReturn(Optional.of(delivery));
        DeliveryDto deliveryDto = deliveryService.getDeliveryRequest("wanneer 50%?");

        assertEquals(delivery.getAddress(),deliveryDto.getAddress());
    }

    @Test
    void deleteDeliveryRequest() {
        Delivery delivery = new Delivery();
        delivery.setAddress("wanneerIsHetNouEensGenoeg");
        when(deliveryRepository.findById("bijzondereBanaan")).thenReturn(Optional.of(delivery));
        // Act
        String result = deliveryService.deleteDeliveryRequest("bijzondereBanaan");
        // Assert
        assertEquals(result, "Delivery Request With " + "bijzondereBanaan" + " removed");
    }

    @Test
    void completedDeliveryStatus() {
        // Arrange
        String id = "1";
        String groceryId = "2";
        Delivery delivery = new Delivery();
        GroceryList groceryList = new GroceryList();
        Optional<Delivery> setDeliveryStatus = Optional.of(delivery);
        Optional<GroceryList> optionalGroceryList = Optional.of(groceryList);
        when(deliveryRepository.findById(id)).thenReturn(setDeliveryStatus);
        when(groceryListRepository.findById(groceryId)).thenReturn(optionalGroceryList);

        // Act
        DeliveryService.completedDeliveryStatus(id, groceryId);

        // Assert

        assertEquals(DeliveryRequestEnum.COMPLETED, groceryList.getStatus());
    }


    @Test
    void transferToDto() {
        // Arrange
       GroceryList groceryList = new GroceryList();
       groceryList.setAddress("zou dit nu de 50% zijn?");

        Account account = new Account();
        account.setUsername("MAAR NOG NIET HELEMAAL");

        Delivery delivery1 = new Delivery();
        delivery1.setAddress("WE ZIJN ER BIJNA WE ZIJN ER BIJNA");
       delivery1.setGrocerylist(groceryList);
       delivery1.setAccount(account);


        when(delivery.getAddress()).thenReturn("WE ZIJN ER BIJNA WE ZIJN ER BIJNA");
        when(delivery.getGrocerylist()).thenReturn(groceryList);
        when(delivery.getAccount()).thenReturn(account);

        // Act
        DeliveryDto deliveryDto = DeliveryService.transferToDto(delivery);

        // Assert
        assertEquals(groceryList, deliveryDto.getGrocerylist());
        assertEquals("WE ZIJN ER BIJNA WE ZIJN ER BIJNA", deliveryDto.getAddress());
        assertEquals("MAAR NOG NIET HELEMAAL", deliveryDto.getAccount().getUsername());
    }

    @Test
    void assignAccountToDelivery() {
        //Arrange
        String accountId = "1";
        String deliveryId = "2";
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());
        when(groceryListRepository.findById(deliveryId)).thenReturn(Optional.of(new GroceryList()));
        // Act + Assert
        assertThrows(RecordNotFoundException.class, () -> DeliveryService.assignAccountToGroceryList(deliveryId, accountId));
    }

    @Test
    void assignAccountToGroceryList() {
        // Arrange
        String accountId = "1";
        String groceryListId = "2";
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());
        when(groceryListRepository.findById(groceryListId)).thenReturn(Optional.of(new GroceryList()));
        // Act + Assert
        assertThrows(RecordNotFoundException.class, () -> DeliveryService.assignAccountToGroceryList(groceryListId, accountId));
    }
}