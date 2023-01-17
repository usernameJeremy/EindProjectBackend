package nl.example.boodschappenbezorgapp.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nl.example.boodschappenbezorgapp.DTO.RekeningDto;
import nl.example.boodschappenbezorgapp.Exceptions.RecordNotFoundException;
import nl.example.boodschappenbezorgapp.Model.Invoice;
import nl.example.boodschappenbezorgapp.Repository.RekeningRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
@NoArgsConstructor
@AllArgsConstructor

@Service
public class InvoiceService {


    private RekeningRepository rekeningRepository;



    public RekeningDto transferToDto (Invoice rekening) {

        RekeningDto dto = new RekeningDto();

        dto.setId(rekening.getId());
        dto.setProducts(rekening.getProducts());
        dto.setDeliveryGuy(rekening.getDeliveryGuy());
        dto.setReciever(rekening.getReciever());
        dto.setDeliveryAddress(rekening.getDeliveryAddress());
        dto.setTotalAmount(rekening.getTotalAmount());

        return dto;
    }

    public Invoice transferFromDto (RekeningDto rekeningDto) {


        Invoice rekening = new Invoice();

        rekening.setId(rekeningDto.getId());
        rekening.setProducts(rekeningDto.getProducts());
        rekening.setDeliveryGuy(rekeningDto.getDeliveryGuy());
        rekening.setReciever(rekeningDto.getReciever());
        rekening.setDeliveryAddress(rekeningDto.getDeliveryAddress());
        rekening.setTotalAmount(rekeningDto.getTotalAmount());

        return rekening;
    }


    public Long createBill(RekeningDto rekeningDto) {

        Invoice newBill = new Invoice();
        //map DTO entity
        newBill.setId(rekeningDto.getId());
        newBill.setProducts(rekeningDto.getProducts());
        newBill.setDeliveryGuy(rekeningDto.getDeliveryGuy());
        newBill.setReciever(rekeningDto.getReciever());
        newBill.setDeliveryAddress(rekeningDto.getDeliveryAddress());
        newBill.setTotalAmount(rekeningDto.getTotalAmount());

        Invoice savedBill = rekeningRepository.save(newBill);
        return savedBill.getId();

    }

    public Iterable<RekeningDto> getAllBills() {

        Iterable<Invoice> allBills = rekeningRepository.findAll();

        ArrayList<RekeningDto> resultList = new ArrayList<>();

        for (Invoice t : allBills) {
            resultList.add(transferToDto(t));
        }
        return resultList;
    }
    //ophalen 1 object
    public RekeningDto getBill(Long id) {
        Optional<Invoice> requestedBill = rekeningRepository.findById(id);
        if (requestedBill.isEmpty()) {
            throw new RecordNotFoundException("No Bill found with this ID: " + id);
        } else {
            return transferToDto(requestedBill.get());
        }
    }
    //verwijderen
    public String deleteBill(Long id) {
        Optional<Invoice> optionalBill = rekeningRepository.findById(id);
        if (optionalBill.isPresent()) {
            rekeningRepository.deleteById(id);
            return "Bill with " + id + " removed";
        } else {
            throw new RecordNotFoundException(" no Bill found with ID " + id);
        }
    }
    //UPDATEN
    public RekeningDto overWriteBill( Long id, RekeningDto rekeningDto) {
        Optional<Invoice> toOverWriteBill = rekeningRepository.findById(id);
        if (toOverWriteBill.isPresent()) {
            Invoice writeOverBill = toOverWriteBill.get();

            writeOverBill.setId(rekeningDto.getId());
            writeOverBill.setProducts(rekeningDto.getProducts());
            writeOverBill.setDeliveryGuy(rekeningDto.getDeliveryGuy());
            writeOverBill.setReciever(rekeningDto.getReciever());
            writeOverBill.setDeliveryAddress(rekeningDto.getDeliveryAddress());
            writeOverBill.setTotalAmount(rekeningDto.getTotalAmount());

            rekeningRepository.save(writeOverBill);

            return transferToDto(writeOverBill);
        }else {
            throw new RecordNotFoundException("Bill with ID: " + id + " doesn't exist or is not found");
        }
    }

}
