package com.example.mbanking.api.file;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public interface FileService {


    Resource downloadFile(String name);
    List<FileDto> findAllFile();

    /**
     * uses to upload a single file
     * @param file request form data from client
     * @return FileDto
     */


    FileDto uploadSingle(MultipartFile file);
    /**
     * uses to upload a multiple file
     * @param files request form data from client
     * @return FileDto
     */
    List<FileDto>  uploadMultiple(List<MultipartFile> files);



}
