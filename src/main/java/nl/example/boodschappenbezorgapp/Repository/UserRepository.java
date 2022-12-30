package nl.example.boodschappenbezorgapp.Repository;

import nl.example.boodschappenbezorgapp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {


}
