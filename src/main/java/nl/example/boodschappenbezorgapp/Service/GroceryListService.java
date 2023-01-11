package nl.example.boodschappenbezorgapp.Service;

import nl.example.boodschappenbezorgapp.DTO.GroceryListDto;
import nl.example.boodschappenbezorgapp.Exceptions.RecordNotFoundException;
import nl.example.boodschappenbezorgapp.Model.GroceryList;
import nl.example.boodschappenbezorgapp.Repository.GroceryListRepository;
import org.springframework.stereotype.Service;
import java.util.*;





@Service
public class GroceryListService {


        private GroceryListRepository groceryListRepository;

    public GroceryListService(GroceryListRepository groceryListRepository) {
        this.groceryListRepository = groceryListRepository;
    }


    public GroceryListDto transferToDto (GroceryList boodschapLijst) {

        GroceryListDto dto = new GroceryListDto();

        dto.setId(boodschapLijst.getId());
        dto.setName(boodschapLijst.getName());
        dto.setAddress(boodschapLijst.getAddress());
        dto.setProducts(boodschapLijst.getProducts());
        dto.setBezorginstructies(boodschapLijst.getBezorginstructies());
        dto.setDateTime(boodschapLijst.getDateTime());

        return dto;
    }

        public GroceryList transferFromDto (GroceryListDto boodschaplijstDto) {

        GroceryList boodschapLijst = new GroceryList();

        boodschapLijst.setName(boodschaplijstDto.getName());
        boodschapLijst.setAddress(boodschaplijstDto.getAddress());
        boodschapLijst.setBezorginstructies(boodschaplijstDto.getBezorginstructies());
        boodschapLijst.setDateTime(boodschaplijstDto.getDateTime());
        boodschapLijst.setProducts(boodschaplijstDto.getProducts());

        return boodschapLijst;
    }


        public Long createGroceryList(GroceryListDto boodschapLijstDto) {

            GroceryList newGroceryLists = new GroceryList();
        //map DTO entity
            newGroceryLists.setName(boodschapLijstDto.getName());
            newGroceryLists.setAddress(boodschapLijstDto.getAddress());
            newGroceryLists.setProducts(boodschapLijstDto.getProducts());
            newGroceryLists.setBezorginstructies(boodschapLijstDto.getBezorginstructies());
            newGroceryLists.setDateTime(boodschapLijstDto.getDateTime());

            GroceryList savedGroceryList = groceryListRepository.save(newGroceryLists);
        return savedGroceryList.getId();

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
        public GroceryListDto getGroceryList(Long id) {
        Optional<GroceryList> requestedGroceryList = groceryListRepository.findById(id);
        if (requestedGroceryList.isEmpty()) {
            throw new RecordNotFoundException("No Grocery List found with this ID: " + id);
        } else {
            return transferToDto(requestedGroceryList.get());
        }
    }
        //verwijderen
        public String deleteGroceryList(Long id) {
        Optional<GroceryList> optionalGroceryList = groceryListRepository.findById(id);
        if (optionalGroceryList.isPresent()) {
            groceryListRepository.deleteById(id);
            return "Grocery list with " + id + " removed";
        } else {
            throw new RecordNotFoundException(" no GroceryList Request found with ID " + id);
        }
    }
        //UPDATEN
        public GroceryListDto overWriteGroceryList(Long id, GroceryListDto boodschapLijstDto) {
        Optional<GroceryList> toOverWriteGroceryList = groceryListRepository.findById(id);
        if (toOverWriteGroceryList.isPresent()) {
            GroceryList writeOverGroceryList = toOverWriteGroceryList.get();

            writeOverGroceryList.setId(boodschapLijstDto.getId());
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
    }


