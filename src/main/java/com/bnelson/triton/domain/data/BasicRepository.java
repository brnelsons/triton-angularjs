package com.bnelson.triton.domain.data;

import java.util.List;

public interface BasicRepository<T> {

    List<T> getAll();

    boolean update(T t);

    boolean create(T t);

    boolean delete(T t);
}
