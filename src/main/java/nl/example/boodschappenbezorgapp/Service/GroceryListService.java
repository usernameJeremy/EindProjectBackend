package nl.example.boodschappenbezorgapp.Service;

import nl.example.boodschappenbezorgapp.DTO.GroceryListDto;
import nl.example.boodschappenbezorgapp.Exceptions.RecordNotFoundException;
import nl.example.boodschappenbezorgapp.Model.Delivery;
import nl.example.boodschappenbezorgapp.Model.GroceryList;
import nl.example.boodschappenbezorgapp.Repository.DeliveryRepository;
import nl.example.boodschappenbezorgapp.Repository.GroceryListRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import static nl.example.boodschappenbezorgapp.Enum.DeliveryRequestEnum.*;


@Service
public class GroceryListService {

        private static GroceryListRepository groceryListRepository;
        static DeliveryRepository deliveryRequestRepository;
        private DeliveryService deliveryService;

    public GroceryListService(GroceryListRepository groceryListRepository, DeliveryRepository deliveryRequestRepository, DeliveryService deliveryService) {
        this.groceryListRepository = groceryListRepository;
        this.deliveryRequestRepository = deliveryRequestRepository;
        this.deliveryService = deliveryService;
    }

        public String createGroceryList(GroceryListDto boodschapLijstDto) {

            GroceryList newGroceryLists = new GroceryList();

            newGroceryLists.setName(boodschapLijstDto.getName());
            newGroceryLists.setAddress(boodschapLijstDto.getAddress());
            newGroceryLists.setProducts(boodschapLijstDto.getProducts());
            newGroceryLists.setBezorginstructies(boodschapLijstDto.getBezorginstructies());
            newGroceryLists.setDateTime(boodschapLijstDto.getDateTime());
            newGroceryLists.setStatus(AVAILABLE);

            groceryListRepository.save(newGroceryLists);

            Delivery newDelivery = new Delivery(newGroceryLists.getAddress(), newGroceryLists.getStatus(), newGroceryLists);
            deliveryRequestRepository.save(newDelivery);

            assigGroceryListToDelivery(newDelivery.getAddress(), newGroceryLists.getAddress());
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
        public GroceryListDto overWriteGroceryList(String id, GroceryListDto boodschapLijstDto) {
        Optional<GroceryList> toOverWriteGroceryList = groceryListRepository.findById(id);
        if (toOverWriteGroceryList.isPresent()) {
            GroceryList writeOverGroceryList = toOverWriteGroceryList.get();

            writeOverGroceryList.setName(boodschapLijstDto.getName());
            writeOverGroceryList.setAddress(boodschapLijstDto.getAddress());
            writeOverGroceryList.setProducts(boodschapLijstDto.getProducts());
            writeOverGroceryList.setBezorginstructies(boodschapLijstDto.getBezorginstructies());
            writeOverGroceryList.setDateTime(boodschapLijstDto.getDateTime());

            groceryListRepository.save(writeOverGroceryList);

            return transferToDto(writeOverGroceryList);
        }else {
            throw new RecordNotFoundException("Grocery list with ID: " + id + " doesn't exist or is not found");
        }
    }
    public GroceryListDto transferToDto (GroceryList boodschapLijst) {

        GroceryListDto dto = new GroceryListDto();


        dto.setName(boodschapLijst.getName());
        dto.setAddress(boodschapLijst.getAddress());
        dto.setProducts(boodschapLijst.getProducts());
        dto.setBezorginstructies(boodschapLijst.getBezorginstructies());
        dto.setDateTime(boodschapLijst.getDateTime());
        dto.setStatus(boodschapLijst.getStatus());


        return dto;
    }

    public GroceryList transferFromDto (GroceryListDto boodschaplijstDto) {

        GroceryList boodschapLijst = new GroceryList();

        boodschapLijst.setName(boodschaplijstDto.getName());
        boodschapLijst.setAddress(boodschaplijstDto.getAddress());
        boodschapLijst.setBezorginstructies(boodschaplijstDto.getBezorginstructies());
        boodschapLijst.setDateTime(boodschaplijstDto.getDateTime());
        boodschapLijst.setProducts(boodschaplijstDto.getProducts());
        boodschapLijst.setStatus(boodschaplijstDto.getStatus());
        if (boodschapLijst.getDeliveryRequest() == null){
            boodschapLijst.setDelivery(boodschapLijst.getDeliveryRequest());
        }

        return boodschapLijst;
    }

    public static void assigGroceryListToDelivery(String id, String deliveryId) {
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


