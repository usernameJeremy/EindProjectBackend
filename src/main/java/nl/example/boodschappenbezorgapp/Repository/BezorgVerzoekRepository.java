package nl.example.boodschappenbezorgapp.Repository;

import nl.example.boodschappenbezorgapp.Model.BezorgVerzoek;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BezorgVerzoekRepository extends JpaRepository<BezorgVerzoek, Long> {
}
