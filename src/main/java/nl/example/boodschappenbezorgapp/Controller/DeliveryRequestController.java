package nl.example.boodschappenbezorgapp.Controller;

import nl.example.boodschappenbezorgapp.DTO.DeliveryRequestDto;
import nl.example.boodschappenbezorgapp.Service.DeliveryRequestService;
import nl.example.boodschappenbezorgapp.Utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@CrossOrigin
@RestController
@RequestMapping(value = "/deliveryrequests")
public class DeliveryRequestController {


        private final DeliveryRequestService bezorgVerzoekService;

        public DeliveryRequestController(DeliveryRequestService bezorgVerzoekService) {
            this.bezorgVerzoekService = bezorgVerzoekService;
        }

        @GetMapping("")
        public ResponseEntity<Iterable<DeliveryRequestDto>> getAllBezorgVerzoek() {

            return ResponseEntity.ok(bezorgVerzoekService.getAllDeliveryRequests());
        }

        @GetMapping("{id}")
        public ResponseEntity<DeliveryRequestDto> getBezorgVerzoek(@PathVariable Long  id) {

            return ResponseEntity.ok(bezorgVerzoekService.getDeliveryRequest(id));
        }

        @PostMapping("")
        public ResponseEntity<String> createBezorgVerzoek(@Valid @RequestBody DeliveryRequestDto bezorgVerzoekDto, BindingResult br) {

            if (br.hasErrors()) {

                String errorStr = Utils.reportErrors(br);
                return new ResponseEntity<>(errorStr, HttpStatus.BAD_REQUEST);
            } else {

                Long createdId = bezorgVerzoekService.createBezorgVerzoek(bezorgVerzoekDto);

                URI uri = URI.create(
                        ServletUriComponentsBuilder
                                .fromCurrentContextPath()
                                .path("/accounts/" + createdId).toUriString());/////////////<<<<<<<
                return ResponseEntity.created(uri).body("Account created!");
            }
        }

        @DeleteMapping("{id}")
        public ResponseEntity<String> deleteBezorgVerzoek(@PathVariable Long id) {
            return ResponseEntity.ok(bezorgVerzoekService.deleteDeliveryRequest(id));
        }

        @PutMapping("{id}")
        public ResponseEntity<DeliveryRequestDto> overWriteBezorgVerzoek(@PathVariable Long id, @RequestBody DeliveryRequestDto bezorgVerzoekDto ) {

            return ResponseEntity.ok(bezorgVerzoekService.overWriteDeliveryRequest(id, bezorgVerzoekDto ));

        }
    }





