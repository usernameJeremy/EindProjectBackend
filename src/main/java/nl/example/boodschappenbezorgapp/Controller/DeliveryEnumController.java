package nl.example.boodschappenbezorgapp.Controller;

import nl.example.boodschappenbezorgapp.Enum.DeliveryRequestEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping(value = "/enumstatus")
public class DeliveryEnumController {

    @GetMapping("")
    public ResponseEntity<DeliveryRequestEnum[]> getValues() {
        return new ResponseEntity<>(DeliveryRequestEnum.values(), HttpStatus.OK);
    }


    @PostMapping("/{name}/status")
    public ResponseEntity<DeliveryRequestEnum> postMyEnumStatus(@PathVariable("name") String name, @RequestBody String newStatus) {
        DeliveryRequestEnum enumStatus = DeliveryRequestEnum.valueOf(name);
        if (enumStatus != null) {
            enumStatus.setStatus(newStatus);
            return new ResponseEntity<>(enumStatus, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{name}/status")
    public ResponseEntity<DeliveryRequestEnum> updateMyEnumStatus(@PathVariable("name") String name, @RequestBody String newStatus) {
        DeliveryRequestEnum enumStatus = DeliveryRequestEnum.valueOf(name);
        if (enumStatus != null) {
            enumStatus.setStatus(newStatus);
            return new ResponseEntity<>(enumStatus, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
