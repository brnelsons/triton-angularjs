package com.bnelson.triton.service;

import com.bnelson.triton.domain.data.CredentialsRepository;
import com.bnelson.triton.domain.model.Credential;
import com.bnelson.triton.domain.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CredentialsServiceImpl implements CredentialsService {

    private final CredentialsRepository credentialsRepository;

    @Autowired
    public CredentialsServiceImpl(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    public boolean create(Credential credential){
        return credentialsRepository.create(credential);
    }

    @Override
    public boolean delete(Credential credential){
        return credentialsRepository.delete(credential);
    }

    @Override
    public Iterable<Credential> getAll(){
        return credentialsRepository.getAll().values();
    }

    @Override
    public Iterable<Credential> getAllForRole(Role role){
        return credentialsRepository.getAll().values()
                .stream()
                .filter(credentials -> role.equals(credentials.getRole()))
                .collect(Collectors.toList());
    }
}
