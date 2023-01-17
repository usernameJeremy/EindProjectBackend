package nl.example.boodschappenbezorgapp.Controller;

import nl.example.boodschappenbezorgapp.DTO.DeliveryDto;
import nl.example.boodschappenbezorgapp.Enum.DeliveryRequestEnum;
import nl.example.boodschappenbezorgapp.Model.Account;
import nl.example.boodschappenbezorgapp.Model.Delivery;
import nl.example.boodschappenbezorgapp.Repository.DeliveryRepository;
import nl.example.boodschappenbezorgapp.Service.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static nl.example.boodschappenbezorgapp.Service.AccountService.accountRepository;
import static nl.example.boodschappenbezorgapp.Service.DeliveryService.assignAccountToDelivery;

@CrossOrigin
@RestController
@RequestMapping(value = "/deliveryrequests")
public class DeliveryController {


        private final DeliveryService bezorgVerzoekService;
        private DeliveryRepository deliveryRepository;


    public DeliveryController(DeliveryService bezorgVerzoekService, DeliveryRepository deliveryRepository) {
            this.bezorgVerzoekService = bezorgVerzoekService;

        this.deliveryRepository = deliveryRepository;
    }

        @GetMapping("")
        public ResponseEntity<Iterable<DeliveryDto>> getAllBezorgVerzoek() {

            return ResponseEntity.ok(bezorgVerzoekService.getAllDeliveryRequests());
        }

        @GetMapping("{id}")
        public ResponseEntity<DeliveryDto> getBezorgVerzoek(@PathVariable String  id) {

            return ResponseEntity.ok(bezorgVerzoekService.getDeliveryRequest(id));
        }

    @PutMapping("/{id}/accounts/{accountId}")
    public ResponseEntity<?> updateDelivery(@PathVariable(value = "id") String id,
                                            @PathVariable(value = "id") String groceryId,
                                            @PathVariable(value = "accountId") String accountId) {
        DeliveryService.updateDelivery(id, accountId, groceryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

        @DeleteMapping("{id}")
        public ResponseEntity<String> deleteBezorgVerzoek(@PathVariable String id) {
            return ResponseEntity.ok(bezorgVerzoekService.deleteDeliveryRequest(id));
        }

        @PutMapping("/{id}/grocerylists/{groceryId}")
    public ResponseEntity<?> completedDeliveryStatus(@PathVariable String id,
                                                     @PathVariable String groceryId)
        {
            DeliveryService.completedDeliveryStatus(id,groceryId);
        return new ResponseEntity<>(HttpStatus.OK);
        }


    }





