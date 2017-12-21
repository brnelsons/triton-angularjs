package com.bnelson.triton.domain.data;

import com.bnelson.triton.domain.model.Credential;
import com.bnelson.triton.domain.model.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CredentialsRepositoryImpl implements CredentialsRepository{
    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialsRepositoryImpl.class);
    private static final String BASE_PATH = "C:/Users/brnel/Documents/triton/security";//TODO inject this directory

    private final FileRepository<CredentialsWrapper, Role> baseRepository;

    public CredentialsRepositoryImpl() {
        baseRepository = new FileRepository<>(
                BASE_PATH,
                new ObjectMapper(new YAMLFactory()),//todo inject this somehow.
                CredentialsWrapper.class,
                CredentialsWrapper::getRole,
                role -> role.name() + "_credentials.yml"
        );
    }
    @VisibleForTesting
    CredentialsRepositoryImpl(FileRepository<CredentialsWrapper, Role> testFileRepo) {
        baseRepository = testFileRepo;
    }

    @Override
    public boolean create(Credential credential){
        CredentialsWrapper credentialsWrapper = new CredentialsWrapper();
        credentialsWrapper.setRole(credential.getRole());
        CredentialsWrapper oneLike = baseRepository.getOneLike(credentialsWrapper);
        if(oneLike != null) {
            oneLike.getCredentials().add(credential);
        }else{
            oneLike = new CredentialsWrapper();
            oneLike.setRole(credential.getRole());
            oneLike.setCredentials(Lists.newArrayList(credential));
        }
        return baseRepository.save(oneLike, true);
    }

    @Override
    public boolean update(Credential credential) {
        CredentialsWrapper updatedWrapper = null;
        for (CredentialsWrapper wrapper : baseRepository.getAll()) {
            for (Credential cred : wrapper.getCredentials()) {
                if (cred.getUsername().equals(credential.getUsername())) {
                    updatedWrapper = wrapper;
                    cred.setRole(credential.getRole());
                    cred.setPassword(credential.getPassword());
                }
            }
        }
        return updatedWrapper != null && baseRepository.save(updatedWrapper, true);
    }

    @Override
    public boolean delete(Credential credential) {
        CredentialsWrapper credentialsWrapper = new CredentialsWrapper();
        credentialsWrapper.setRole(credential.getRole());
        CredentialsWrapper oneLike = baseRepository.getOneLike(credentialsWrapper);
        if(oneLike != null) {
            oneLike.getCredentials().remove(credential);
            baseRepository.save(oneLike, true);
            return true;
        }
        LOGGER.warn("Could not find credential to delete credential={}", credential);
        return false;

    }

    @Override
    public Multimap<Role, Credential> getAll(){
        Multimap<Role, Credential> map = ArrayListMultimap.create();
        for (CredentialsWrapper wrapper : baseRepository.getAll()) {
            for (Credential credential : wrapper.getCredentials()) {
                map.put(credential.getRole(), credential);
            }
        }
        return map;
    }

    @VisibleForTesting
    static class CredentialsWrapper{
        private List<Credential> credentials;
        private Role role;

        public List<Credential> getCredentials() {
            return credentials;
        }

        public void setCredentials(List<Credential> list) {
            this.credentials = list;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CredentialsWrapper that = (CredentialsWrapper) o;
            return Objects.equal(credentials, that.credentials) &&
                    role == that.role;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(credentials, role);
        }
    }

}
