package com.example.mbanking.api.file;

import com.example.mbanking.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    @Value("${file.download-url}")
    private String fileDownloadUrl;

    private FileUtil fileUtil;


    @Override
    public void deletedByName(String name) {
        fileUtil.deleteByNane(name);
    }

    @Override
    public FileDto findByName(String name) throws IOException {

        Resource resource = fileUtil.findByName(name);
        return FileDto.builder()
                .name(resource.getFilename())
                .extension(fileUtil.getExtension(resource.getFilename()))
                .url(String.format("%s%s" ,fileUtil.getFileBaseUrl(),resource.getFilename()))
                .downloadUrl(String.format("%s%s" ,fileDownloadUrl,name))
                .size(resource.contentLength())
                .build();

    }

    @Autowired
    public void setFileUtil(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Override
    public Resource downloadFile(String name) {
         return fileUtil.findByName(name);
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
