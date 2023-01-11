package nl.example.boodschappenbezorgapp.Controller;


import nl.example.boodschappenbezorgapp.DTO.GroceryListDto;
import nl.example.boodschappenbezorgapp.Service.GroceryListService;
import nl.example.boodschappenbezorgapp.Utils.Utils;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping(value = "/grocerylists")
public class GroceryListController {
    private final GroceryListService boodschapLijstService;



    public GroceryListController(GroceryListService boodschapLijstService) {
        this.boodschapLijstService = boodschapLijstService;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<GroceryListDto>> getAllgroceryLists() {

        return ResponseEntity.ok(boodschapLijstService.getAllGroceryLists());
    }

    @GetMapping("{id}")
    public ResponseEntity<GroceryListDto> getGroceryList(@PathVariable Long  id) {


        return ResponseEntity.ok(boodschapLijstService.getGroceryList(id));
    }

    @PostMapping("")
    public ResponseEntity<String> createBoodschapLijst(@Valid @RequestBody GroceryListDto boodschapLijstDto, BindingResult br) {

        if (br.hasErrors()) {

            String errorStr = Utils.reportErrors(br);
            return new ResponseEntity<>(errorStr, HttpStatus.BAD_REQUEST);
        } else {

            Long createdId = boodschapLijstService.createGroceryList(boodschapLijstDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/grocerylists/" + createdId).toUriString());
            return ResponseEntity.created(uri).body("Boodschappen Lijst aangemaakt!");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBoodschapLijst(@PathVariable Long id) {
        return ResponseEntity.ok(boodschapLijstService.deleteGroceryList(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<GroceryListDto> overWriteBoodschapLijst(@PathVariable Long id, @RequestBody GroceryListDto boodschapLijstDto ) {

        return ResponseEntity.ok(boodschapLijstService.overWriteGroceryList(id, boodschapLijstDto ));

    }

}
