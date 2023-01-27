package nl.example.boodschappenbezorgapp.Service;

import nl.example.boodschappenbezorgapp.DTO.GroceryListDto;
import nl.example.boodschappenbezorgapp.Exceptions.RecordNotFoundException;
import nl.example.boodschappenbezorgapp.Model.Delivery;
import nl.example.boodschappenbezorgapp.Model.GroceryList;
import nl.example.boodschappenbezorgapp.Repository.AccountRepository;
import nl.example.boodschappenbezorgapp.Repository.DeliveryRepository;
import nl.example.boodschappenbezorgapp.Repository.GroceryListRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import static nl.example.boodschappenbezorgapp.Enum.DeliveryRequestEnum.*;


@Service
public class GroceryListService {

        public static GroceryListRepository groceryListRepository;
        public static DeliveryRepository deliveryRequestRepository;
        public static DeliveryService deliveryService;
        public static AccountRepository accountRepository;

    public GroceryListService(GroceryListRepository groceryListRepository, DeliveryRepository deliveryRequestRepository, DeliveryService deliveryService,
                              AccountRepository accountRepository) {
        this.groceryListRepository = groceryListRepository;
        this.deliveryRequestRepository = deliveryRequestRepository;
        this.deliveryService = deliveryService;
        this.accountRepository = accountRepository;
    }

        public String createGroceryList(GroceryListDto groceryListDto ) {

            GroceryList newGroceryLists = new GroceryList();

            newGroceryLists.setName(groceryListDto.getName());
            newGroceryLists.setAddress(groceryListDto.getAddress());
            newGroceryLists.setProducts(groceryListDto.getProducts());
            newGroceryLists.setDeliveryInstructions(groceryListDto.getDeliveryInstructions());
            newGroceryLists.setDateTime(groceryListDto.getDateTime());
            newGroceryLists.setAccount(groceryListDto.getAccount());
            newGroceryLists.setStatus(AVAILABLE);

            groceryListRepository.save(newGroceryLists);

            Delivery newDelivery = new Delivery(newGroceryLists.getAddress(), newGroceryLists);
            deliveryRequestRepository.save(newDelivery);

            assignGroceryListToDelivery(newDelivery.getAddress(), newGroceryLists.getAddress());
            groceryListRepository.save(newGroceryLists);

        return newGroceryLists.getAddress();

    }

        public Iterable<GroceryListDto> getAllGroceryLists() {

        Iterable<GroceryList> allGroceryLists = groceryListRepository.findAll();

        ArrayList<GroceryListDto> resultList = new ArrayList<>();

        for (GroceryList t : allGroceryLists) {
            resultList.add(transferToDto(t));
        }
        return resultList;
    }

        //ophalen 1 object
        public GroceryListDto getGroceryList(String id) {
        Optional<GroceryList> requestedGroceryList = groceryListRepository.findById(id);
        if (requestedGroceryList.isEmpty()) {
            throw new RecordNotFoundException("No Grocery List found with this ID: " + id);
        } else {
            return transferToDto(requestedGroceryList.get());
        }
    }

        //verwijderen
        public String deleteGroceryList(String id) {
        Optional<GroceryList> optionalGroceryList = groceryListRepository.findById(id);
        if (optionalGroceryList.isPresent()) {
            groceryListRepository.deleteById(id);
            return "Grocery list with " + id + " removed";
        } else {
            throw new RecordNotFoundException(" no GroceryList Request found with ID " + id);
        }
    }
        //UPDATEN
        public GroceryListDto overWriteGroceryList(String id, GroceryListDto groceryListDto) {
        Optional<GroceryList> toOverWriteGroceryList = groceryListRepository.findById(id);
        if (toOverWriteGroceryList.isPresent()) {
            GroceryList writeOverGroceryList = toOverWriteGroceryList.get();

            writeOverGroceryList.setName(groceryListDto.getName());
            writeOverGroceryList.setAddress(groceryListDto.getAddress());
            writeOverGroceryList.setProducts(groceryListDto.getProducts());
            writeOverGroceryList.setDeliveryInstructions(groceryListDto.getDeliveryInstructions());
            writeOverGroceryList.setDateTime(groceryListDto.getDateTime());

            groceryListRepository.save(writeOverGroceryList);

            return transferToDto(writeOverGroceryList);
        }else {
            throw new RecordNotFoundException("Grocery list with ID: " + id + " doesn't exist or is not found");
        }
    }
    public GroceryListDto transferToDto (GroceryList groceryList) {

        GroceryListDto dto = new GroceryListDto();

        dto.setName(groceryList.getName());
        dto.setAddress(groceryList.getAddress());
        dto.setProducts(groceryList.getProducts());
        dto.setDeliveryInstructions(groceryList.getDeliveryInstructions());
        dto.setDateTime(groceryList.getDateTime());
        dto.setStatus(groceryList.getStatus());
        dto.setAccount(groceryList.getAccount());

        return dto;
    }
    public static void assignGroceryListToDelivery(String id, String deliveryId) {
        Optional<GroceryList> optionalGroceryList = groceryListRepository.findById(id);
        Optional<Delivery> optionalDelivery = deliveryRequestRepository.findById(deliveryId);
        if (optionalGroceryList.isPresent() && optionalDelivery.isPresent()) {
            GroceryList groceryList = optionalGroceryList.get();
            Delivery delivery = optionalDelivery.get();
            groceryList.setDelivery(delivery);
            groceryListRepository.save(groceryList);
        } else {
            throw new RecordNotFoundException();
        }
    }

    }


