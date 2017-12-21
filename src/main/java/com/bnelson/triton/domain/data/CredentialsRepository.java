package com.bnelson.triton.domain.data;

import com.bnelson.triton.domain.model.Credential;
import com.bnelson.triton.domain.model.Role;
import com.google.common.collect.Multimap;

public interface CredentialsRepository {
    boolean create(Credential credential);

    boolean update(Credential credential);

    boolean delete(Credential credential);

    Multimap<Role, Credential> getAll();
}
