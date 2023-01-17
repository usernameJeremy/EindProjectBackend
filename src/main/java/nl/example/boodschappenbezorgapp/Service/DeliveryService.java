package nl.example.boodschappenbezorgapp.Service;

import nl.example.boodschappenbezorgapp.DTO.DeliveryDto;
import nl.example.boodschappenbezorgapp.Enum.DeliveryRequestEnum;
import nl.example.boodschappenbezorgapp.Exceptions.RecordNotFoundException;
import nl.example.boodschappenbezorgapp.Model.Account;
import nl.example.boodschappenbezorgapp.Model.Delivery;
import nl.example.boodschappenbezorgapp.Model.GroceryList;
import nl.example.boodschappenbezorgapp.Repository.AccountRepository;
import nl.example.boodschappenbezorgapp.Repository.DeliveryRepository;
import nl.example.boodschappenbezorgapp.Repository.GroceryListRepository;
import org.springframework.stereotype.Service;
import java.util.*;



@Service
public class DeliveryService {

        private static DeliveryRepository deliveryRepository;
        private static GroceryListRepository groceryListRepository;
        private static AccountRepository accountRepository;

    public DeliveryService(DeliveryRepository deliveryRepository, AccountRepository accountRepository, GroceryListRepository groceryListRepository) {
        this.deliveryRepository = deliveryRepository;
        this.accountRepository = accountRepository;
        this.groceryListRepository = groceryListRepository;
    }



    public static void updateDelivery(String id, String accountId, String groceryId) {
        Optional<Delivery> optionalDelivery = deliveryRepository.findById(id);
        Optional<GroceryList> optionalGroceryList = groceryListRepository.findById(groceryId);
        if (optionalDelivery.isEmpty() && optionalGroceryList.isEmpty()) {
            throw new RecordNotFoundException("Geen levering of boodschappenlijst gevonden");
        }
        Delivery delivery = optionalDelivery.get();
        GroceryList groceryList = optionalGroceryList.get();
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isEmpty()) {
            throw new RecordNotFoundException("Geen account gevonden");
        }
        Account account = optionalAccount.get();
        assignAccountToDelivery(id, account.getUsername());

        groceryList.setStatus(DeliveryRequestEnum.ACCEPTED);
        delivery.setStatus(DeliveryRequestEnum.ACCEPTED);

        deliveryRepository.save(delivery);
    }



    public Iterable<DeliveryDto> getAllDeliveryRequests() {

            Iterable<Delivery> allDeliveryRequest = deliveryRepository.findAll();

            ArrayList<DeliveryDto> resultList = new ArrayList<>();

            for (Delivery t : allDeliveryRequest) {
                resultList.add(transferToDto(t));
            }
            return resultList;
        }
        //ophalen 1 object
        public DeliveryDto getDeliveryRequest(String id) {
            Optional<Delivery> requestedBezorgVerzoek = deliveryRepository.findById(id);
            if (requestedBezorgVerzoek.isEmpty()) {
                throw new RecordNotFoundException("No delivery request found with this ID: " + id);
            } else {
                return transferToDto(requestedBezorgVerzoek.get());
            }
        }
        //verwijderen
        public String deleteDeliveryRequest(String id) {
            Optional<Delivery> optionalBezorgVerzoekt = deliveryRepository.findById(id);
            if (optionalBezorgVerzoekt.isPresent()) {
                deliveryRepository.deleteById(id);
                return "Delivery Request With " + id + " removed";
            } else {
                throw new RecordNotFoundException(" no Delivery Request found with ID " + id);
            }
        }
        //UPDATEN
        public DeliveryDto overWriteDeliveryRequest(String id, DeliveryDto bezorgVerzoekDto) {
            Optional<Delivery> toOverWriteBezorgVerzoek = deliveryRepository.findById(id);
            if (toOverWriteBezorgVerzoek.isPresent()) {
                Delivery writeOverDeliveryRequest = toOverWriteBezorgVerzoek.get();

                writeOverDeliveryRequest.setAddress(bezorgVerzoekDto.getAddress());

                deliveryRepository.save(writeOverDeliveryRequest);

                return transferToDto(writeOverDeliveryRequest);
            }else {
                throw new RecordNotFoundException("Leverings verzoek met ID: " + id + " bestaat niet of is niet gevonden");
            }
        }

    public static void completedDeliveryStatus(String id, String groceryId) {
        Optional<Delivery> setDeliveryStatus = deliveryRepository.findById(id);
        Optional<GroceryList> optionalGroceryList = groceryListRepository.findById(groceryId);
        if (setDeliveryStatus.isPresent()) {
            Delivery updateSatus = setDeliveryStatus.get();
            GroceryList groceryList = optionalGroceryList.get();

            groceryList.setStatus(DeliveryRequestEnum.COMPLETED);
            updateSatus.setStatus(DeliveryRequestEnum.COMPLETED);

            deliveryRepository.save(updateSatus);
            groceryListRepository.save(groceryList);
            transferToDto(updateSatus);
        }else {
            throw new RecordNotFoundException("Leverings verzoek met ID: " + id + " bestaat niet of is niet gevonden");
        }
    }

    public static DeliveryDto transferToDto(Delivery delivery) {

        DeliveryDto dto = new DeliveryDto();

        dto.setAddress(delivery.getAddress());
        dto.setGrocerylist(delivery.getGrocerylist());
        dto.setAccount(delivery.getAccount());

        return dto;
    }

    public static void assignAccountToDelivery(String id, String accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        Optional<Delivery> optionalDelivery = deliveryRepository.findById(id);
        if (optionalAccount.isPresent() && optionalDelivery.isPresent()) {
            Account account = optionalAccount.get();
            Delivery delivery = optionalDelivery.get();
            delivery.setAccount(account);
            deliveryRepository.save(delivery);
        } else {
            throw new RecordNotFoundException();
        }
    }

    }




