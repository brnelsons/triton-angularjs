package com.bnelson.triton.service;

import com.bnelson.triton.domain.model.Credential;
import com.bnelson.triton.domain.model.Role;

public interface CredentialsService {
    boolean create(Credential credential);

    boolean delete(Credential credential);

    Iterable<Credential> getAll();

    Iterable<Credential> getAllForRole(Role role);
}
