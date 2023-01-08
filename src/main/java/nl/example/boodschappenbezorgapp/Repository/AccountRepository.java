package nl.example.boodschappenbezorgapp.Repository;

import nl.example.boodschappenbezorgapp.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, String> {

}
