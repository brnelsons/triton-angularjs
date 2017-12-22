package com.bnelson.triton.service;

import com.bnelson.triton.domain.model.Credential;
import com.bnelson.triton.domain.model.Role;

import java.util.List;

public interface CredentialsService {
    boolean create(Credential credential);

    boolean delete(Credential credential);

    List<Credential> getAll();

    List<Credential> getAllForRole(Role role);

    boolean saveAll(List<Credential> credentials);

    List<Credential> getByUsername(String name);
}
