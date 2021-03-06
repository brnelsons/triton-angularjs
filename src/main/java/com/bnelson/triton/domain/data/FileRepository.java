package com.bnelson.triton.domain.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileRepository<T, K> implements BasicRepository<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRepository.class);

    private final String directory;
    private final File file;
    private final FileNameConverter<T, K> fileNameConverter;
    private final FileNameBuilder<K> fileNameBuilder;
    private final ObjectMapper objectMapper;
    private final Class<T> tClass;

    public FileRepository(String directory,
                          ObjectMapper objectMapper,
                          Class<T> tClass,
                          FileNameConverter<T, K> fileNameConverter,
                          FileNameBuilder<K> fileNameBuilder) {
        this.directory = directory;
        this.file = new File(directory);
        this.objectMapper = objectMapper;
        this.tClass = tClass;
        this.fileNameConverter = fileNameConverter;
        this.fileNameBuilder = fileNameBuilder;
        ensureFolder();
    }

    @Override
    public List<T> getAll() {
        List<T> ts = new ArrayList<>();
        for (String gameName : getAllFiles()) {
            ts.add(getByFileName(gameName));
        }
        return ts;
    }

    @Override
    public boolean update(T t) {
        return save(t, true);
    }

    @Override
    public boolean create(T t) {
        return save(t, false);
    }

    @Override
    public boolean delete(T t) {
        String path = getAbsolutePath(t);
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        LOGGER.error("Could not delete file {}", path);
        return false;
    }

    private boolean save(T t, boolean override) {
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

    private String getAbsolutePath(T t) {
        return directory + "/" + fileNameBuilder.Build(fileNameConverter.convert(t));
    }

    @Nullable
    T getOneLike(K k) {
        return getByFileName(fileNameBuilder.Build(k));
    }

    @Nullable
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

    private void ensureFolder() {
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public interface FileNameBuilder<T> {
        String Build(T t);
    }

    public interface FileNameConverter<T, K> {
        K convert(T t);
    }
}
