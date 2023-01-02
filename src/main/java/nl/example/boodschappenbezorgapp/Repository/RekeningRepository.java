package nl.example.boodschappenbezorgapp.Repository;

import nl.example.boodschappenbezorgapp.Model.Rekening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RekeningRepository extends JpaRepository<Rekening, Long> {
}
