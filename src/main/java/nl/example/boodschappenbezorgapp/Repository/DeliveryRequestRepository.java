package nl.example.boodschappenbezorgapp.Repository;

import nl.example.boodschappenbezorgapp.Model.DeliveryRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRequestRepository extends JpaRepository<DeliveryRequest, Long> {
}
