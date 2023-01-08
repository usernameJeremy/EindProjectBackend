package nl.example.boodschappenbezorgapp.Service;


import lombok.*;
import nl.example.boodschappenbezorgapp.Model.FileDB;
import nl.example.boodschappenbezorgapp.Repository.FileDBRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.stream.Stream;


@AllArgsConstructor
@NoArgsConstructor

@Service
public class FileStorageService {

        private FileDBRepository fileDBRepository;

        public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

        return fileDBRepository.save(FileDB);
        }

        public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
        }

        public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
        }
}

