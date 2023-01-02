package nl.example.boodschappenbezorgapp.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nl.example.boodschappenbezorgapp.DTO.AccountDto;
import nl.example.boodschappenbezorgapp.Exceptions.RecordNotFoundException;
import nl.example.boodschappenbezorgapp.Model.Account;
import nl.example.boodschappenbezorgapp.Repository.AccountRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@NoArgsConstructor


@Service
public class AccountService {


    private AccountRepository accountRepository;



    public AccountDto transferToDto (Account account) {

        AccountDto dto = new AccountDto();

        dto.setUsername(account.getUsername());
        dto.setPassword(account.getPassword());
        dto.setName(account.getName());
        dto.setLastName(account.getLastName());
        dto.setAdres(account.getAddress());

        return dto;
    }

    public Account transferFromDto (AccountDto accountDto) {


        Account account = new Account();

        account.setUsername(accountDto.getUsername());
        account.setPassword(accountDto.getPassword());
        account.setName(accountDto.getName());
        account.setLastName(accountDto.getLastName());
        account.setAddress(accountDto.getAdres());

        return account;
    }


    public Long createAccount(AccountDto accountDto) {

        Account newAccount = new Account();
        //map DTO entity
        newAccount.setUsername(accountDto.getUsername());
        newAccount.setPassword(accountDto.getPassword());
        newAccount.setName(accountDto.getName());
        newAccount.setLastName(accountDto.getLastName());
        newAccount.setAddress(accountDto.getAdres());

        Account savedAccount = accountRepository.save(newAccount);
        return savedAccount.getId();

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
    public AccountDto getAccount(Long id) {
        Optional<Account> requestedAccount = accountRepository.findById(id);
        if (requestedAccount.isEmpty()) {
            throw new RecordNotFoundException("No account found with this ID " + id);
        } else {
            return transferToDto(requestedAccount.get());
        }
    }
    //verwijderen
    public String deleteAccount(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            accountRepository.deleteById(id);
            return "Account With " + id + " removed";
        } else {
            throw new RecordNotFoundException(" no account found with ID " + id);
        }
    }
    //UPDATEN
    public AccountDto overWriteAccount( Long id, AccountDto accountDto) {
        Optional<Account> toOverWriteAccount = accountRepository.findById(id);
        if (toOverWriteAccount.isPresent()) {
            Account writeOverAccount = toOverWriteAccount.get();

            writeOverAccount.setUsername(accountDto.getUsername());
            writeOverAccount.setPassword(accountDto.getPassword());
            writeOverAccount.setName(accountDto.getName());
            writeOverAccount.setLastName(accountDto.getLastName());
            writeOverAccount.setAddress(accountDto.getAdres());

            accountRepository.save(writeOverAccount);

            return transferToDto(writeOverAccount);
        }else {
            throw new RecordNotFoundException("Account with ID: " + id + " doesn't exist or is not found");
        }
    }
}


