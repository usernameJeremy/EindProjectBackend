package nl.example.boodschappenbezorgapp.Service;

import nl.example.boodschappenbezorgapp.DTO.AccountDto;
import nl.example.boodschappenbezorgapp.Exceptions.RecordNotFoundException;
import nl.example.boodschappenbezorgapp.Model.Account;
import nl.example.boodschappenbezorgapp.Model.Delivery;
import nl.example.boodschappenbezorgapp.Model.GroceryList;
import nl.example.boodschappenbezorgapp.Repository.AccountRepository;

import nl.example.boodschappenbezorgapp.Repository.GroceryListRepository;
import nl.example.boodschappenbezorgapp.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class AccountService {


    public  AccountRepository accountRepository;
    public  UserRepository userRepository;
    public  GroceryListRepository groceryListRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository, GroceryListRepository groceryListRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.groceryListRepository = groceryListRepository;
    }


    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public String createAccount(AccountDto accountDto) {

        Account newAccount = new Account();

        newAccount.setUsername(accountDto.getUsername());
        newAccount.setName(accountDto.getName());
        newAccount.setLastName(accountDto.getLastName());
        newAccount.setAddress(accountDto.getAddress());
        newAccount.setUser(accountDto.getUser());


       accountRepository.save(newAccount);
        return newAccount.getUsername();

    }

    public Iterable<AccountDto> getAllAccounts() {

        Iterable<Account> allAccounts = accountRepository.findAll();

        ArrayList<AccountDto> resultList = new ArrayList<>();

        for (Account t : allAccounts) {
            resultList.add(transferToDto(t));
        }
        return resultList;
    }
    //ophalen 1 object
    public AccountDto getAccount(String id) {
        Optional<Account> requestedAccount = accountRepository.findById(id);
        if (requestedAccount.isEmpty()) {
            throw new RecordNotFoundException("No account found with this ID " + id);
        } else {
            return transferToDto(requestedAccount.get());
        }
    }
    //verwijderen
    public String deleteAccount(String id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            accountRepository.deleteById(id);
            return "Account With " + id + " removed";
        } else {
            throw new RecordNotFoundException(" no account found with ID " + id);
        }
    }
    //UPDATEN
    public AccountDto overWriteAccount( String id, AccountDto accountDto) {
        Optional<Account> toOverWriteAccount = accountRepository.findById(id);
        if (toOverWriteAccount.isPresent()) {
            Account writeOverAccount = toOverWriteAccount.get();

            writeOverAccount.setName(accountDto.getName());
            writeOverAccount.setLastName(accountDto.getLastName());
            writeOverAccount.setAddress(accountDto.getAddress());


            accountRepository.save(writeOverAccount);

            return transferToDto(writeOverAccount);
        }else {
            throw new RecordNotFoundException("Account with ID: " + id + " doesn't exist or is not found");
        }
    }
    public AccountDto transferToDto (Object obj) {

        if(!(obj instanceof Account account)) throw new IllegalArgumentException("Object is not of type Account");
        AccountDto dto = new AccountDto();

        dto.setUsername(account.getUsername());
        dto.setName(account.getName());
        dto.setLastName(account.getLastName());
        dto.setAddress(account.getAddress());
        dto.setUser(account.getUser());

        return dto;
    }

}


