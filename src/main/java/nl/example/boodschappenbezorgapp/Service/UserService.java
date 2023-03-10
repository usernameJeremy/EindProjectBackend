package nl.example.boodschappenbezorgapp.Service;


import nl.example.boodschappenbezorgapp.DTO.UserDto;
import nl.example.boodschappenbezorgapp.Exceptions.RecordNotFoundException;
import nl.example.boodschappenbezorgapp.Exceptions.UsernameNotFoundException;
import nl.example.boodschappenbezorgapp.Model.Account;
import nl.example.boodschappenbezorgapp.Model.Authority;
import nl.example.boodschappenbezorgapp.Model.User;
import nl.example.boodschappenbezorgapp.Repository.AccountRepository;
import nl.example.boodschappenbezorgapp.Repository.UserRepository;
import nl.example.boodschappenbezorgapp.Utils.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;





@Service
public class UserService {

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    private static UserRepository userRepository;
    private static AccountRepository accountRepository;


    public UserService(UserRepository userRepository, AccountRepository accountRepository ) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserDto getUser(String username) {
        UserDto dto = new UserDto();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()){
            dto = fromUser(user.get());
        }else {
            throw new UsernameNotFoundException(username);
        }
        return dto;
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    public String createUser(UserDto userDto) {

        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setApikey(randomString);
        User newUser = userRepository.save(toUser(userDto));

        Account newAccount = new Account(newUser.getUsername(), "name","lastName","address");
        accountRepository.save(newAccount);
        assignAccountToUser(newUser.getUsername(),newAccount.getUsername());
        userRepository.save(newUser);

        return newUser.getUsername();

    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public void updateUser(String username, UserDto newUser) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException();
        User user = userRepository.findById(username).get();
        user.setPassword(newUser.getPassword());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(user);
    }

    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserDto userDto = fromUser(user);
        return userDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {

        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }



    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    public static UserDto fromUser(User user){

        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.enabled = user.isEnabled();
        dto.apikey = user.getApikey();
        dto.email = user.getEmail();
        dto.authorities = user.getAuthorities();
        if (dto.getAccount() == null){
            dto.setAccount(user.getAccount());
        }

        return dto;
    }

    public static User toUser(UserDto userDto) {

        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.getEnabled());
        user.setApikey(userDto.getApikey());
        user.setEmail(userDto.getEmail());
        if(userDto.getAccount() != null){
            user.setAccount(userDto.getAccount());
        }


        return user;
    }

    public static void assignAccountToUser(String id, String accountId) {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalUser.isPresent() && optionalAccount.isPresent()) {
            User user = optionalUser.get();
            Account account = optionalAccount.get();
            user.setAccount(account);
            userRepository.save(user);
        } else {
            throw new RecordNotFoundException();
        }
    }

    public Object findById(String username) {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserService that = (UserService) o;
        return Objects.equals(passwordEncoder, that.passwordEncoder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passwordEncoder);
    }
}