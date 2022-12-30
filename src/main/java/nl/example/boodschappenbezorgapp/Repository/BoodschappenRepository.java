package nl.example.boodschappenbezorgapp.Repository;

import nl.example.boodschappenbezorgapp.Model.BoodschapLijst;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoodschappenRepository extends JpaRepository<BoodschapLijst, Long> {
}
