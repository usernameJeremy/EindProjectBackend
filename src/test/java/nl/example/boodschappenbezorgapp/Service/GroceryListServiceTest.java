package nl.example.boodschappenbezorgapp.Service;


import nl.example.boodschappenbezorgapp.DTO.GroceryListDto;
import nl.example.boodschappenbezorgapp.Enum.DeliveryRequestEnum;
import nl.example.boodschappenbezorgapp.Model.Account;
import nl.example.boodschappenbezorgapp.Model.Delivery;
import nl.example.boodschappenbezorgapp.Model.GroceryList;
import nl.example.boodschappenbezorgapp.Repository.DeliveryRepository;
import nl.example.boodschappenbezorgapp.Repository.GroceryListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nl.example.boodschappenbezorgapp.Enum.DeliveryRequestEnum.AVAILABLE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GroceryListServiceTest {

    @Mock
    GroceryList groceryList;


    @Mock
    private GroceryListRepository groceryListRepository;

    @Mock
    Account account;
    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private GroceryListService groceryListService;


GroceryList groceryList1;
    GroceryList groceryList2;
    GroceryList groceryList3;
    GroceryListDto groceryListDto1;
    GroceryListDto groceryListDto2;
    GroceryListDto groceryListDto3;
    GroceryListDto groceryListDto4;
    GroceryListDto groceryListDto5;
    GroceryListDto groceryListDto6;
    @BeforeEach
    void setUp(){
        groceryList1 = new GroceryList("testUsername1", "testAddress1","testInstruction" ,1200 ,"testProducts", AVAILABLE, null,null);
        groceryList2 = new GroceryList("testUsername2", "testAddress2","testInstruction" ,1200 ,"testProducts", AVAILABLE, null,null);
        groceryList3 = new GroceryList("testUsername3", "testAddress3","testInstruction" ,1200 ,"testProducts", AVAILABLE, null,null);

        groceryListDto1 = new GroceryListDto("testUsername1", "testAddress1",  "testInstruction",  1200,"testProducts");
        groceryListDto2 = new GroceryListDto("testUsername2", "testAddress2","testInstruction" , 1200, "testProducts");
        groceryListDto3 = new GroceryListDto("testUsername3", "testAddress3", "testInstruction", 1200, "testProducts");
        groceryListDto4 = new GroceryListDto("testUsername4", "testAddress4", "testInstruction", 1200, "testProducts");
        groceryListDto5 = new GroceryListDto("testUsername1", "testAddress5",  "testInstruction", 1200, "testProducts");
        groceryListDto6 = new GroceryListDto("testUsername2", "testAddress6", "testInstruction", 1200, "testProducts");
    }
    @Test
    void createGroceryList() {
        // Arrange

        Delivery newDelivery = new Delivery();
        newDelivery.setAddress("testAddress1");

        when(groceryListRepository.save(any(GroceryList.class))).thenReturn(groceryList1);
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(newDelivery);
        when(groceryListRepository.findById(groceryListDto1.getAddress())).thenReturn(Optional.of(groceryList1));
        when(deliveryRepository.findById("testAddress1")).thenReturn(Optional.of(newDelivery));

        // Act
        groceryListService.createGroceryList(groceryListDto1);

        // Assert
        assertEquals("testAddress1", groceryList1.getAddress());
        assertEquals("testAddress1", newDelivery.getAddress());

    }

    @Test
    void getAllGroceryLists() {
        //Arrange
        List<GroceryList> actualLists = new ArrayList<>();
        actualLists.add(groceryList3);
        actualLists.add(groceryList2);

        List<GroceryListDto> expectedList = new ArrayList<>();
        expectedList.add(groceryListDto3);
        expectedList.add(groceryListDto2);

        when(groceryListRepository.findAll()).thenReturn(actualLists);

        List<GroceryListDto> foundLists = (List<GroceryListDto>) groceryListService.getAllGroceryLists();

        assertEquals(expectedList.get(0).getName(), foundLists.get(0).getName() );
        assertEquals(expectedList.get(0).getAddress(), foundLists.get(0).getAddress() );
        assertEquals(expectedList.get(1).getName(), foundLists.get(1).getName() );
        assertEquals(expectedList.get(1).getAddress(), foundLists.get(1).getAddress() );

    }



    @Test
    void getGroceryList() {

        when(groceryListRepository.findById("testaddress")).thenReturn(Optional.of(groceryList2));
        GroceryListDto groceryListDto = groceryListService.getGroceryList("testaddress");

        //act//assert
        assertEquals( groceryList2.getAddress(), groceryListDto.getAddress());
    }


    @Test
    void deleteGroceryList() {
        // Arrange
        String id = "testaddress";

        when(groceryListRepository.findById(id)).thenReturn(Optional.of(groceryList1));

        // Act
        String result = groceryListService.deleteGroceryList(id);

        // Assert
        assertEquals(result, "Grocery list with " + id + " removed");

    }

    @Test
    void overWriteGroceryList() {
        //Arrange
        when(groceryListRepository.findById("testId")).thenReturn(Optional.of(groceryList3));
        when(groceryListRepository.save(any(GroceryList.class))).thenReturn(groceryList3);

        //Act
        GroceryListDto result = groceryListService.overWriteGroceryList("testId", groceryListDto4);

        //Assert
        assertEquals("testUsername4", result.getName());
        assertEquals("testAddress4", result.getAddress());
        assertEquals("testInstruction", result.getDeliveryInstructions());
        assertEquals(1200, result.getDateTime());
        assertEquals("testProducts", result.getProducts());
    }

    @Test
    void transferToDto() {
        //Arrange
        when(groceryList.getName()).thenReturn("test name");
        when(groceryList.getAddress()).thenReturn("test address");
        when(groceryList.getDeliveryInstructions()).thenReturn("test instruction");
        when(groceryList.getDateTime()).thenReturn(1200);
        when(groceryList.getProducts()).thenReturn("test products");

        //Act
        GroceryListDto groceryListDto = groceryListService.transferToDto(groceryList);

        //Assert
        assertEquals("test name", groceryListDto.getName());
        assertEquals("test address", groceryListDto.getAddress());
        assertEquals("test instruction", groceryListDto.getDeliveryInstructions());
        assertEquals(1200, groceryListDto.getDateTime());
        assertEquals("test products", groceryListDto.getProducts());
    }

    @Test
    void assignGroceryListToDelivery()  {
        // Arrange
        GroceryList groceryList = new GroceryList();
        groceryList.setAddress("test adres groceryList");

        Delivery delivery = new Delivery();
        delivery.setAddress("test address delivery");

        when(groceryListRepository.findById("testId")).thenReturn(Optional.of(groceryList));
        when(deliveryRepository.findById("testDeliveryId")).thenReturn(Optional.of(delivery));
        // Act
        GroceryListService.assignGroceryListToDelivery("testId","testDeliveryId" );

        // Assert
        assertEquals("test adres groceryList", groceryList.getAddress());
        assertEquals("test address delivery", delivery.getAddress());
    }

}