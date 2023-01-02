package nl.example.boodschappenbezorgapp.Service;

import lombok.NoArgsConstructor;
import nl.example.boodschappenbezorgapp.DTO.BoodschapLijstDto;
import nl.example.boodschappenbezorgapp.Exceptions.RecordNotFoundException;
import nl.example.boodschappenbezorgapp.Model.BoodschapLijst;
import nl.example.boodschappenbezorgapp.Repository.BoodschaplijstRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@NoArgsConstructor
@Service
public class BoodschapLijstService {


        private BoodschaplijstRepository boodschaplijstRepository;



        public BoodschapLijstDto transferToDto (BoodschapLijst boodschapLijst) {

        BoodschapLijstDto dto = new BoodschapLijstDto();

        dto.setId(boodschapLijst.getId());
        dto.setName(boodschapLijst.getName());
        dto.setAddress(boodschapLijst.getAddress());
        dto.setProducts(boodschapLijst.getProducts());
        dto.setBezorginstructies(boodschapLijst.getBezorginstructies());
        dto.setDateTime(boodschapLijst.getDateTime());

        return dto;
    }

        public BoodschapLijst transferFromDto (BoodschapLijstDto boodschaplijstDto) {


        BoodschapLijst boodschapLijst = new BoodschapLijst();

        boodschapLijst.setId(boodschaplijstDto.getId());
        boodschapLijst.setName(boodschaplijstDto.getName());
        boodschapLijst.setAddress(boodschaplijstDto.getAddress());
        boodschapLijst.setProducts(boodschaplijstDto.getProducts());
        boodschapLijst.setBezorginstructies(boodschaplijstDto.getBezorginstructies());
        boodschapLijst.setDateTime(boodschaplijstDto.getDateTime());

        return boodschapLijst;
    }


        public Long createGroceryList(BoodschapLijstDto boodschapLijstDto) {

            BoodschapLijst newGroceryLists = new BoodschapLijst();
        //map DTO entity
            newGroceryLists.setId(boodschapLijstDto.getId());
            newGroceryLists.setName(boodschapLijstDto.getName());
            newGroceryLists.setAddress(boodschapLijstDto.getAddress());
            newGroceryLists.setProducts(boodschapLijstDto.getProducts());
            newGroceryLists.setBezorginstructies(boodschapLijstDto.getBezorginstructies());
            newGroceryLists.setDateTime(boodschapLijstDto.getDateTime());

            BoodschapLijst savedGroceryList = boodschaplijstRepository.save(newGroceryLists);
        return savedGroceryList.getId();

    }

        public Iterable<BoodschapLijstDto> getAllGroceryLists() {

        Iterable<BoodschapLijst> allGroceryLists = boodschaplijstRepository.findAll();

        ArrayList<BoodschapLijstDto> resultList = new ArrayList<>();

        for (BoodschapLijst t : allGroceryLists) {
            resultList.add(transferToDto(t));
        }
        return resultList;
    }
        //ophalen 1 object
        public BoodschapLijstDto getGroceryList(Long id) {
        Optional<BoodschapLijst> requestedGroceryList = boodschaplijstRepository.findById(id);
        if (requestedGroceryList.isEmpty()) {
            throw new RecordNotFoundException("No Grocery List found with this ID: " + id);
        } else {
            return transferToDto(requestedGroceryList.get());
        }
    }
        //verwijderen
        public String deleteGroceryList(Long id) {
        Optional<BoodschapLijst> optionalGroceryList = boodschaplijstRepository.findById(id);
        if (optionalGroceryList.isPresent()) {
            boodschaplijstRepository.deleteById(id);
            return "Grocery list with " + id + " removed";
        } else {
            throw new RecordNotFoundException(" no GroceryList Request found with ID " + id);
        }
    }
        //UPDATEN
        public BoodschapLijstDto overWriteGroceryList( Long id, BoodschapLijstDto boodschapLijstDto) {
        Optional<BoodschapLijst> toOverWriteGroceryList = boodschaplijstRepository.findById(id);
        if (toOverWriteGroceryList.isPresent()) {
            BoodschapLijst writeOverGroceryList = toOverWriteGroceryList.get();

            writeOverGroceryList.setId(boodschapLijstDto.getId());
            writeOverGroceryList.setName(boodschapLijstDto.getName());
            writeOverGroceryList.setAddress(boodschapLijstDto.getAddress());
            writeOverGroceryList.setProducts(boodschapLijstDto.getProducts());
            writeOverGroceryList.setBezorginstructies(boodschapLijstDto.getBezorginstructies());
            writeOverGroceryList.setDateTime(boodschapLijstDto.getDateTime());

            boodschaplijstRepository.save(writeOverGroceryList);

            return transferToDto(writeOverGroceryList);
        }else {
            throw new RecordNotFoundException("Grocery list with ID: " + id + " doesn't exist or is not found");
        }
    }
    }


