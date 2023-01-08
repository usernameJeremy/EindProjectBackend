package nl.example.boodschappenbezorgapp.Repository;

import nl.example.boodschappenbezorgapp.Model.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
}
