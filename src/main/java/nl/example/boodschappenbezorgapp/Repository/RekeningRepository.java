package nl.example.boodschappenbezorgapp.Repository;

import nl.example.boodschappenbezorgapp.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RekeningRepository extends JpaRepository<Invoice, Long> {
}
