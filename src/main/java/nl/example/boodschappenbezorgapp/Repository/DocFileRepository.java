package nl.example.boodschappenbezorgapp.Repository;

import javax.transaction.Transactional;
import nl.example.boodschappenbezorgapp.Model.FileDocument;
import org.springframework.data.jpa.repository.JpaRepository;
@Transactional
public interface DocFileRepository extends JpaRepository<FileDocument, Long> {
    FileDocument findByFileName(String fileName);
}
