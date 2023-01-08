package nl.example.boodschappenbezorgapp.Controller;

import nl.example.boodschappenbezorgapp.DTO.AccountDto;
import nl.example.boodschappenbezorgapp.Service.AccountService;
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
@RequestMapping(value = "/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<AccountDto>> getAllAccounts() {

        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable String  id) {


        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @PostMapping("")
    public ResponseEntity<String> createAccount(@Valid @RequestBody AccountDto accountDto, BindingResult br) {

        if (br.hasErrors()) {

            String errorStr = Utils.reportErrors(br);
            return new ResponseEntity<>(errorStr, HttpStatus.BAD_REQUEST);
        } else {

           String createdId = accountService.createAccount(accountDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/accounts/" + createdId).toUriString());
            return ResponseEntity.created(uri).body("Account created!");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable String id) {
        return ResponseEntity.ok(accountService.deleteAccount(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<AccountDto> overWriteAccount(@PathVariable String id,@RequestBody AccountDto accountDto ) {

        return ResponseEntity.ok(accountService.overWriteAccount(id, accountDto ));

    }
}
