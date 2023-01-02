package nl.example.boodschappenbezorgapp.Service;

import lombok.NoArgsConstructor;
import nl.example.boodschappenbezorgapp.DTO.BezorgVerzoekDto;
import nl.example.boodschappenbezorgapp.Exceptions.RecordNotFoundException;
import nl.example.boodschappenbezorgapp.Model.BezorgVerzoek;
import nl.example.boodschappenbezorgapp.Repository.BezorgVerzoekRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
@NoArgsConstructor

@Service
public class BezorgVerzoekService {

        private BezorgVerzoekRepository bezorgVerzoekRepository;



        public BezorgVerzoekDto transferToDto (BezorgVerzoek bezorgVerzoek) {

            BezorgVerzoekDto dto = new BezorgVerzoekDto();

            dto.setId(bezorgVerzoek.getId());
            dto.setSupermarkt(bezorgVerzoek.getSupermarkt());
            dto.setBezorgInstructies(bezorgVerzoek.getBezorgInstructies());
            dto.setDateOfDelivery(bezorgVerzoek.getDateOfDelivery());
            dto.setBezorgVerzoek(bezorgVerzoek.getBezorgVerzoek());
            dto.setBezorgd(bezorgVerzoek.isBezorgd());

            return dto;
        }

        public BezorgVerzoek transferFromDto (BezorgVerzoekDto bezorgVerzoekDto) {


            BezorgVerzoek bezorgVerzoek = new BezorgVerzoek();

            bezorgVerzoek.setId(bezorgVerzoekDto.getId());
            bezorgVerzoek.setSupermarkt(bezorgVerzoekDto.getSupermarkt());
            bezorgVerzoek.setBezorgInstructies(bezorgVerzoekDto.getBezorgInstructies());
            bezorgVerzoek.setDateOfDelivery(bezorgVerzoekDto.getDateOfDelivery());
            bezorgVerzoek.setBezorgVerzoek(bezorgVerzoekDto.getBezorgVerzoek());
            bezorgVerzoek.setBezorgd(bezorgVerzoekDto.isBezorgd());

            return bezorgVerzoek;
        }


        public Long createBezorgVerzoek(BezorgVerzoekDto bezorgVerzoekDto) {

            BezorgVerzoek newBezorgVerzoek = new BezorgVerzoek();
            //map DTO entity
            newBezorgVerzoek.setId(bezorgVerzoekDto.getId());
            newBezorgVerzoek.setSupermarkt(bezorgVerzoekDto.getSupermarkt());
            newBezorgVerzoek.setBezorgInstructies(bezorgVerzoekDto.getBezorgInstructies());
            newBezorgVerzoek.setDateOfDelivery(bezorgVerzoekDto.getDateOfDelivery());
            newBezorgVerzoek.setBezorgVerzoek(bezorgVerzoekDto.getBezorgVerzoek());
            newBezorgVerzoek.setBezorgd(bezorgVerzoekDto.isBezorgd());

            BezorgVerzoek savedBezorgVerzoek = bezorgVerzoekRepository.save(newBezorgVerzoek);
            return savedBezorgVerzoek.getId();

        }

        public Iterable<BezorgVerzoekDto> getAllDeliveryRequests() {

            Iterable<BezorgVerzoek> allDeliveryRequest = bezorgVerzoekRepository.findAll();

            ArrayList<BezorgVerzoekDto> resultList = new ArrayList<>();

            for (BezorgVerzoek t : allDeliveryRequest) {
                resultList.add(transferToDto(t));
            }
            return resultList;
        }
        //ophalen 1 object
        public BezorgVerzoekDto getDeliveryRequest(Long id) {
            Optional<BezorgVerzoek> requestedBezorgVerzoek = bezorgVerzoekRepository.findById(id);
            if (requestedBezorgVerzoek.isEmpty()) {
                throw new RecordNotFoundException("No delivery request found with this ID: " + id);
            } else {
                return transferToDto(requestedBezorgVerzoek.get());
            }
        }
        //verwijderen
        public String deleteDeliveryRequest(Long id) {
            Optional<BezorgVerzoek> optionalBezorgVerzoekt = bezorgVerzoekRepository.findById(id);
            if (optionalBezorgVerzoekt.isPresent()) {
                bezorgVerzoekRepository.deleteById(id);
                return "Delivery Request With " + id + " removed";
            } else {
                throw new RecordNotFoundException(" no Delivery Request found with ID " + id);
            }
        }
        //UPDATEN
        public BezorgVerzoekDto overWriteDeliveryRequest( Long id, BezorgVerzoekDto bezorgVerzoekDto) {
            Optional<BezorgVerzoek> toOverWriteBezorgVerzoek = bezorgVerzoekRepository.findById(id);
            if (toOverWriteBezorgVerzoek.isPresent()) {
                BezorgVerzoek writeOverDeliveryRequest = toOverWriteBezorgVerzoek.get();

                writeOverDeliveryRequest.setId(bezorgVerzoekDto.getId());
                writeOverDeliveryRequest.setSupermarkt(bezorgVerzoekDto.getSupermarkt());
                writeOverDeliveryRequest.setBezorgInstructies(bezorgVerzoekDto.getBezorgInstructies());
                writeOverDeliveryRequest.setDateOfDelivery(bezorgVerzoekDto.getDateOfDelivery());
                writeOverDeliveryRequest.setBezorgVerzoek(bezorgVerzoekDto.getBezorgVerzoek());
                writeOverDeliveryRequest.setBezorgd(bezorgVerzoekDto.isBezorgd());

                bezorgVerzoekRepository.save(writeOverDeliveryRequest);

                return transferToDto(writeOverDeliveryRequest);
            }else {
                throw new RecordNotFoundException("Account with ID: " + id + " doesn't exist or is not found");
            }
        }
    }




