package com.bnelson.triton.domain.data;

import com.bnelson.triton.domain.model.GameModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class FileRepository<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRepository.class);

    private final String directory;
    private final FileNameBuilder<T> fileNameBuilder;
    private final ObjectMapper objectMapper;
    private final Class<T> tClass;

    FileRepository(String directory,
                   ObjectMapper objectMapper,
                   Class<T> tClass,
                   FileNameBuilder<T> fileNameBuilder){
        this.directory = directory;
        this.objectMapper = objectMapper;
        this.tClass = tClass;
        this.fileNameBuilder = fileNameBuilder;
    }

    List<T> getAll(){
        List<T> ts = new ArrayList<>();
        for (String gameName : getAllFiles()) {
            ts.add(getByFileName(gameName));
        }
        return ts;
    }

    boolean save(T t, boolean override){
        String fileName = getAbsolutePath(t);
        if (!override && new File(fileName).exists()) {
            LOGGER.error("File {} already exists!", fileName);
            return false;
        }
        try {
            objectMapper.writeValue(new FileWriter(fileName), t);
            return true;
        } catch (IOException e) {
            LOGGER.error("Was unable to write ", fileName, e);
        }
        return false;
    }

    boolean delete(T t){
        String path = getAbsolutePath(t);
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        LOGGER.error("Could not delete file {}", path);
        return false;
    }

    private String getAbsolutePath(T t) {
        return directory + "/" + fileNameBuilder.Build(t);
    }

    T getOneLike(T t){
        return getByFileName(fileNameBuilder.Build(t));
    }

    private T getByFileName(String filename) {
        String fullPath = directory + "/" + filename;
        try {
            return objectMapper.readValue(new File(fullPath), tClass);
        } catch (IOException e) {
            LOGGER.error("Was unable to parse file at {} into {}", fullPath, tClass.getSimpleName(), e);
        }
        return null;
    }

    private List<String> getAllFiles() {
        //noinspection ConstantConditions
        String[] list = new File(directory).list((dir, name) -> name.endsWith(".yml"));
        if (list == null) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList(list);
    }


    public interface FileNameBuilder<T>{
        String Build(T t);
    }
}
