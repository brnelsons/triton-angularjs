package com.bnelson.triton.domain.data;

import com.bnelson.triton.domain.model.Credential;

import java.util.List;

public interface CredentialsRepository extends BasicRepository<Credential> {
    List<Credential> getAll();

    boolean create(Credential credential);

    boolean update(Credential credential);

    boolean delete(Credential credential);

    boolean saveAll(List<Credential> credentials);
}
