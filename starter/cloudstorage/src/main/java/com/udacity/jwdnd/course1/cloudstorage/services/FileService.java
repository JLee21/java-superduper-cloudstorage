package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public Integer addFile(MultipartFile multipartFile, Integer userId) throws IOException {
            File file = new File(
                    multipartFile.getOriginalFilename(),
                    multipartFile.getContentType(),
                    multipartFile.getSize(),
                    multipartFile.getBytes(),
                    userId
            );
            return this.fileMapper.insert(file);
    }

    public void deleteFile(Long fileId, Integer userId) {
        this.fileMapper.delete(fileId, userId);
    }

    public List<File> list(Integer userId) {
        return this.fileMapper.list(userId);
    }

    public Boolean isFilenameAvailable(String filename, Integer userId){
        return this.fileMapper.listFilesWithFilenameAndUserId(filename, userId).size() == 0;
    }
}
