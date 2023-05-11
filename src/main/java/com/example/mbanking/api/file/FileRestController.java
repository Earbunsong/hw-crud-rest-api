package com.example.mbanking.api.file;

import com.example.mbanking.base.BaseRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@Slf4j
@RequiredArgsConstructor
public class FileRestController {

    private final FileService fileService;


    @GetMapping("/download/{name}")
    public ResponseEntity<?> downloadFile(@PathVariable String name){
        Resource resource  = fileService.downloadFile(name);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .header("Content-Disposition", "attachment; filename=" + resource.getFilename())
                .body(resource);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public void deletedByName(@PathVariable String name){
        fileService.deletedByName(name);
    }

    @GetMapping("/{name}")
    public BaseRest<?> findByName(@PathVariable String name) throws IOException {
        FileDto fileDto = fileService.findByName(name);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been found")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();
    }

//    @GetMapping("/download/{name}")
//    public ResponseEntity<?> download(@PathVariable("name") String filename){
////        Resource resource = fileService.downloadFile(filename);
//          Resource resource = fileService.downloadFile(filename);
//        System.out.println(resource.getFilename());
//        try {
//            return ResponseEntity
//                    .ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                    .body(resource);
//        }catch (ResponseStatusException e){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File is not found.");
//        }
//    }

    @GetMapping
    public BaseRest<?> findAllFile(){
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been uploaded")
                .timestamp(LocalDateTime.now())
                .data(fileService.findAllFile())
                .build();
    }



    @PostMapping
    public BaseRest<?> uploadSingle(@RequestPart MultipartFile file){
        log.info("File Request = {} ", file);
//        fileService.uploadSingle(file);
         FileDto fileDto = fileService.uploadSingle(file);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been uploaded")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();
    }


    @PostMapping("/multiple")
    public BaseRest<?> uploadMultiple(@RequestPart List<MultipartFile>  files){
        log.info("File Request = {} ", files);
//        fileService.uploadSingle(files);
       List<FileDto>  fileDto = fileService.uploadMultiple(files);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been uploaded")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();
    }
}
