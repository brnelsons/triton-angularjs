package com.bnelson.triton.service;

import com.bnelson.triton.domain.data.CredentialsRepository;
import com.bnelson.triton.domain.model.Credential;
import com.bnelson.triton.domain.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<Credential> getAll(){
        return credentialsRepository.getAll();
    }

    @Override
    public List<Credential> getAllForRole(Role role){
        return credentialsRepository.getAll()
                .stream()
                .filter(credentials -> role.equals(credentials.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean saveAll(List<Credential> credentials) {
        return credentialsRepository.saveAll(credentials);
    }

    @Override
    public List<Credential> getByUsername(String name) {
        return credentialsRepository.getAll().stream()
                .filter(credential -> name.equals(credential.getUsername()))
                .collect(Collectors.toList());
    }
}
