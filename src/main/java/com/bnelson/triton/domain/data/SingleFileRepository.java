package com.bnelson.triton.domain.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class SingleFileRepository<T> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(new YAMLFactory());

    private final FileRepository<T, String> baseRepository;
    private final String fileName;

    public SingleFileRepository(String directory, String fileName, Class<T> clazz) {
        this.baseRepository = new FileRepository<>(
                directory,
                OBJECT_MAPPER,
                clazz,
                t -> fileName,
                s -> s
        );
        this.fileName = fileName;
    }

    public T get() {
        return baseRepository.getOneLike(fileName);
    }

    public boolean create(T t) {
        return baseRepository.create(t);
    }

    public boolean update(T t) {
        return baseRepository.update(t);
    }

    public boolean delete(T t) {
        return baseRepository.delete(t);
    }

}
