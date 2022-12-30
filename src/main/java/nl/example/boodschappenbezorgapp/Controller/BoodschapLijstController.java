package nl.example.boodschappenbezorgapp.Controller;

import nl.example.boodschappenbezorgapp.DTO.BoodschapLijstDto;
import nl.example.boodschappenbezorgapp.Service.BoodschapLijstService;
import nl.example.boodschappenbezorgapp.Utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

public class BoodschapLijstController {
    private final BoodschapLijstService service;

    public BoodschapLijstController(BoodschapLijstService s) {
        this.service = s;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<BoodschapLijstDto>> getGroceryLists() {

        return ResponseEntity.ok(service.getAllGroceryLists());
    }

    @GetMapping("{id}")
    public ResponseEntity<BoodschapLijstDto> getGroceryList(@PathVariable long id) {


        return ResponseEntity.ok(service.getGroceryList(id));
    }

    @PostMapping("")
    public ResponseEntity<String> createGroceryList(@Valid @RequestBody BoodschapLijstDto boodschapLijstDto, BindingResult br) {

        if (br.hasErrors()) {
            // something's wrong
            String errorStr = Utils.reportErrors(br);
            return new ResponseEntity<>(errorStr, HttpStatus.BAD_REQUEST);
        } else {
            // happy flow
            Long createdId = service.createGroceryList(boodschapLijstDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/television/" + createdId).toUriString());
            return ResponseEntity.created(uri).body("GroceryList created!");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteGroceryList(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteGroceryList(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<BoodschapLijstDto> overWriteGroceryList(@PathVariable Long id, @RequestBody TelevisionInputDto televisionInputDto) {

        return ResponseEntity.ok(service.overWriteGroceryList(id, televisionInputDto));

    }

    @PutMapping("/{id}/deliveryreqeusts/{drid}")
    public ResponseEntity<Object> addGroceryListToDeliveryReqeust(@PathVariable Long id, @PathVariable Long drid){

        service.addGroceryListToDeliveryReqeust(id,drid);


        return ResponseEntity.noContent().build();
    }
}
