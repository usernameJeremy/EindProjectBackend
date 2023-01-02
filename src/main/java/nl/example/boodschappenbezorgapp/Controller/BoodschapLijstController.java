package nl.example.boodschappenbezorgapp.Controller;

import nl.example.boodschappenbezorgapp.DTO.BezorgVerzoekDto;
import nl.example.boodschappenbezorgapp.DTO.BoodschapLijstDto;
import nl.example.boodschappenbezorgapp.Service.BezorgVerzoekService;
import nl.example.boodschappenbezorgapp.Service.BoodschapLijstService;
import nl.example.boodschappenbezorgapp.Utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "grocerylists")
public class BoodschapLijstController {
    private final BoodschapLijstService boodschapLijstService;

    public BoodschapLijstController(BoodschapLijstService boodschapLijstService) {
        this.boodschapLijstService = boodschapLijstService;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<BoodschapLijstDto>> getAllgroceryLists() {

        return ResponseEntity.ok(boodschapLijstService.getAllGroceryLists());
    }

    @GetMapping("{id}")
    public ResponseEntity<BoodschapLijstDto> getGroceryList(@PathVariable Long  id) {


        return ResponseEntity.ok(boodschapLijstService.getGroceryList(id));
    }

    @PostMapping("")
    public ResponseEntity<String> createBoodschapLijst(@Valid @RequestBody BoodschapLijstDto boodschapLijstDto, BindingResult br) {

        if (br.hasErrors()) {

            String errorStr = Utils.reportErrors(br);
            return new ResponseEntity<>(errorStr, HttpStatus.BAD_REQUEST);
        } else {

            Long createdId = boodschapLijstService.createGroceryList(boodschapLijstDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/grocerylists/" + createdId).toUriString());
            return ResponseEntity.created(uri).body("grocerylists Lijst created!");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBoodschapLijst(@PathVariable Long id) {
        return ResponseEntity.ok(boodschapLijstService.deleteGroceryList(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<BoodschapLijstDto> overWriteBoodschapLijst(@PathVariable Long id,@RequestBody BoodschapLijstDto boodschapLijstDto ) {

        return ResponseEntity.ok(boodschapLijstService.overWriteGroceryList(id, boodschapLijstDto ));

    }

}
