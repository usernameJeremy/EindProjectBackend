package nl.example.boodschappenbezorgapp.Controller;

import nl.example.boodschappenbezorgapp.DTO.RekeningDto;
import nl.example.boodschappenbezorgapp.Service.RekeningService;
import nl.example.boodschappenbezorgapp.Utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
@RestController
@RequestMapping(value = "/rekening/")
public class RekeningController {
    private final RekeningService rekeningService;

    public RekeningController(RekeningService rekeningService) {
        this.rekeningService = rekeningService;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<RekeningDto>> getAllBills() {

        return ResponseEntity.ok(rekeningService.getAllBills());
    }

    @GetMapping("{id}")
    public ResponseEntity<RekeningDto> getBill(@PathVariable Long  id) {


        return ResponseEntity.ok(rekeningService.getBill(id));
    }

    @PostMapping("")
    public ResponseEntity<String> createBill (@Valid @RequestBody RekeningDto rekeningDto, BindingResult br) {

        if (br.hasErrors()) {

            String errorStr = Utils.reportErrors(br);
            return new ResponseEntity<>(errorStr, HttpStatus.BAD_REQUEST);
        } else {

            Long createdId = rekeningService.createBill(rekeningDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/rekening/" + createdId).toUriString());
            return ResponseEntity.created(uri).body("Bill created!");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBill(@PathVariable Long id) {
        return ResponseEntity.ok(rekeningService.deleteBill(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<RekeningDto> overWriteBill(@PathVariable Long id,@RequestBody RekeningDto rekeningDto ) {

        return ResponseEntity.ok(rekeningService.overWriteBill(id, rekeningDto ));

    }

}
