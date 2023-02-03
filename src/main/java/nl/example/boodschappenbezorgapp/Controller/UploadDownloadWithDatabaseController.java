package nl.example.boodschappenbezorgapp.Controller;


import nl.example.boodschappenbezorgapp.FileUploadResponse.FileUploadResponse;
import nl.example.boodschappenbezorgapp.Model.FileDocument;
import nl.example.boodschappenbezorgapp.Service.DatabaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@CrossOrigin
@RestController
public class UploadDownloadWithDatabaseController {

    private final DatabaseService databaseService;

    public UploadDownloadWithDatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @PostMapping("single/uploadDb")
    public FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {


        // next line makes url. example "http://localhost:8080/download/naam.jpg"
        FileDocument fileDocument = databaseService.uploadFileDocument(file);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFromDB/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();

        String contentType = file.getContentType();

        return new FileUploadResponse(fileDocument.getFileName(), url, contentType );
    }

    //    get for single download
    @GetMapping("/downloadFromDB/{fileName}")
    ResponseEntity<byte[]> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

        return databaseService.singleFileDownload(fileName, request);
    }

    @GetMapping("/getAll/db")
    public Collection<FileDocument> getAllFromDB(){
        return databaseService.getALlFromDB();
    }
}
