package nl.example.boodschappenbezorgapp.Service;

import lombok.NoArgsConstructor;
import nl.example.boodschappenbezorgapp.DTO.DeliveryRequestDto;
import nl.example.boodschappenbezorgapp.Exceptions.RecordNotFoundException;
import nl.example.boodschappenbezorgapp.Model.DeliveryRequest;
import nl.example.boodschappenbezorgapp.Repository.DeliveryRequestRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
@NoArgsConstructor

@Service
public class DeliveryRequestService {

        private DeliveryRequestRepository bezorgVerzoekRepository;



        public DeliveryRequestDto transferToDto (DeliveryRequest bezorgVerzoek) {

            DeliveryRequestDto dto = new DeliveryRequestDto();

            dto.setId(bezorgVerzoek.getId());
            dto.setSupermarkt(bezorgVerzoek.getSupermarkt());
            dto.setBezorgInstructies(bezorgVerzoek.getBezorgInstructies());
            dto.setDateOfDelivery(bezorgVerzoek.getDateOfDelivery());
            dto.setBezorgVerzoek(bezorgVerzoek.getBezorgVerzoek());

            return dto;
        }

        public DeliveryRequest transferFromDto (DeliveryRequestDto bezorgVerzoekDto) {


            DeliveryRequest bezorgVerzoek = new DeliveryRequest();

            bezorgVerzoek.setId(bezorgVerzoekDto.getId());
            bezorgVerzoek.setSupermarkt(bezorgVerzoekDto.getSupermarkt());
            bezorgVerzoek.setBezorgInstructies(bezorgVerzoekDto.getBezorgInstructies());
            bezorgVerzoek.setDateOfDelivery(bezorgVerzoekDto.getDateOfDelivery());
            bezorgVerzoek.setBezorgVerzoek(bezorgVerzoekDto.getBezorgVerzoek());


            return bezorgVerzoek;
        }


        public Long createBezorgVerzoek(DeliveryRequestDto bezorgVerzoekDto) {

            DeliveryRequest newBezorgVerzoek = new DeliveryRequest();

            newBezorgVerzoek.setId(bezorgVerzoekDto.getId());
            newBezorgVerzoek.setSupermarkt(bezorgVerzoekDto.getSupermarkt());
            newBezorgVerzoek.setBezorgInstructies(bezorgVerzoekDto.getBezorgInstructies());
            newBezorgVerzoek.setDateOfDelivery(bezorgVerzoekDto.getDateOfDelivery());
            newBezorgVerzoek.setBezorgVerzoek(bezorgVerzoekDto.getBezorgVerzoek());


            DeliveryRequest savedBezorgVerzoek = bezorgVerzoekRepository.save(newBezorgVerzoek);
            return savedBezorgVerzoek.getId();

        }

        public Iterable<DeliveryRequestDto> getAllDeliveryRequests() {

            Iterable<DeliveryRequest> allDeliveryRequest = bezorgVerzoekRepository.findAll();

            ArrayList<DeliveryRequestDto> resultList = new ArrayList<>();

            for (DeliveryRequest t : allDeliveryRequest) {
                resultList.add(transferToDto(t));
            }
            return resultList;
        }
        //ophalen 1 object
        public DeliveryRequestDto getDeliveryRequest(Long id) {
            Optional<DeliveryRequest> requestedBezorgVerzoek = bezorgVerzoekRepository.findById(id);
            if (requestedBezorgVerzoek.isEmpty()) {
                throw new RecordNotFoundException("No delivery request found with this ID: " + id);
            } else {
                return transferToDto(requestedBezorgVerzoek.get());
            }
        }
        //verwijderen
        public String deleteDeliveryRequest(Long id) {
            Optional<DeliveryRequest> optionalBezorgVerzoekt = bezorgVerzoekRepository.findById(id);
            if (optionalBezorgVerzoekt.isPresent()) {
                bezorgVerzoekRepository.deleteById(id);
                return "Delivery Request With " + id + " removed";
            } else {
                throw new RecordNotFoundException(" no Delivery Request found with ID " + id);
            }
        }
        //UPDATEN
        public DeliveryRequestDto overWriteDeliveryRequest(Long id, DeliveryRequestDto bezorgVerzoekDto) {
            Optional<DeliveryRequest> toOverWriteBezorgVerzoek = bezorgVerzoekRepository.findById(id);
            if (toOverWriteBezorgVerzoek.isPresent()) {
                DeliveryRequest writeOverDeliveryRequest = toOverWriteBezorgVerzoek.get();

                writeOverDeliveryRequest.setId(bezorgVerzoekDto.getId());
                writeOverDeliveryRequest.setSupermarkt(bezorgVerzoekDto.getSupermarkt());
                writeOverDeliveryRequest.setBezorgInstructies(bezorgVerzoekDto.getBezorgInstructies());
                writeOverDeliveryRequest.setDateOfDelivery(bezorgVerzoekDto.getDateOfDelivery());
                writeOverDeliveryRequest.setBezorgVerzoek(bezorgVerzoekDto.getBezorgVerzoek());


                bezorgVerzoekRepository.save(writeOverDeliveryRequest);

                return transferToDto(writeOverDeliveryRequest);
            }else {
                throw new RecordNotFoundException("Leverings verzoek met ID: " + id + " bestaat niet of is niet gevonden");
            }
        }
    }




