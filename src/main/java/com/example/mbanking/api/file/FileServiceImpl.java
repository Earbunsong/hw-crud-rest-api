package com.example.mbanking.api.file;

import com.example.mbanking.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;





@Service
@Slf4j
public class FileServiceImpl implements  FileService {

    @Value("${file.server-path}")
    private String fileServerPath;

    @Value("${file.client-path}")
    private String fileClientPath;

    @Value("${file.server-path}")
    private String filesServerPath;

    @Value("${file.base-url}")
    private String fileBaseUrl;
    private FileUtil fileUtil;

    @Autowired
    public void setFileUtil(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Override
    public Resource downloadFile(String name) {
        File file = new File(fileServerPath);
        File [] files = file.listFiles();
        Path path ;
        Resource resource;
        try {
            for(File file1 : files){

                if(file1.getName().startsWith(name)){
                    path  = Paths.get(fileServerPath + file1.getName()).toAbsolutePath().normalize();
                   return new UrlResource(path.toUri());
                }
            }
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"File is not found.");
        }
        return null;
    }


    @Override
    public List<FileDto> findAllFile() {
        File file = new File(fileServerPath);
        File[] file1 = file.listFiles();
        List<FileDto> fileDtoList = new ArrayList<>();
        for (File file2: file1){
            String name = file2.getName().substring(0,file2.getName().length()-4);

            fileDtoList
                    .add(new FileDto(
                            file2.getName()
                            ,fileBaseUrl + file2.getName()
                            ,"http://localhost:15000/files/download/" + file2.getName()
                            ,file2.getName().substring(file2.getName().lastIndexOf(".")+ 1)
                            ,file2.length()
                    ));

        }
        return fileDtoList;
    }


    @Override
    public FileDto uploadSingle(MultipartFile file) {
        return fileUtil.upload(file);
    }

    @Override
    public List<FileDto> uploadMultiple(List<MultipartFile> files) {
        List<FileDto> filesDto = new ArrayList<>();

        for (MultipartFile file : files) {
            filesDto.add(fileUtil.upload(file));
        }
        return filesDto;
    }
}
