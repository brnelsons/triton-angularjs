package com.bnelson.triton.domain.data;

import com.bnelson.triton.domain.config.DirectoryConfig;
import com.bnelson.triton.domain.model.Credential;
import com.bnelson.triton.domain.model.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.collect.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class CredentialsRepositoryImpl implements CredentialsRepository{
    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialsRepositoryImpl.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(new YAMLFactory());

    private final FileRepository<CredentialsWrapper, Role> baseRepository;

    @Autowired
    public CredentialsRepositoryImpl(@Qualifier(DirectoryConfig.SECURITY_DIR) String securityDir) {
        baseRepository = new FileRepository<>(
                securityDir,
                OBJECT_MAPPER,
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
        CredentialsWrapper oneLike = baseRepository.getOneLike(credential.getRole());
        if(oneLike != null) {
            oneLike.getCredentials().add(credential);
        }else{
            oneLike = new CredentialsWrapper();
            oneLike.setRole(credential.getRole());
            oneLike.setCredentials(Lists.newArrayList(credential));
        }
        return baseRepository.update(oneLike);
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
        return updatedWrapper != null && baseRepository.update(updatedWrapper);
    }

    @Override
    public boolean delete(Credential credential) {
        CredentialsWrapper oneLike = baseRepository.getOneLike(credential.getRole());
        if(oneLike != null) {
            oneLike.getCredentials().remove(credential);
            baseRepository.update(oneLike);
            return true;
        }
        LOGGER.warn("Could not find credential to delete credential={}", credential);
        return false;

    }

    @Override
    public boolean saveAll(List<Credential> credentials) {
        boolean success = true;
        Multimap<Role, Credential> map = ArrayListMultimap.create();
        for(Credential credential : credentials){
            map.put(credential.getRole(), credential);
        }
        for (Map.Entry<Role, Collection<Credential>> entry : map.asMap().entrySet()) {
            CredentialsWrapper wrapper = new CredentialsWrapper();
            wrapper.setRole(entry.getKey());
            wrapper.setCredentials(Lists.newArrayList(entry.getValue()));
            success = success && baseRepository.update(wrapper);
        }

        return success;
    }

    @Override
    public List<Credential> getAll(){
        List<Credential> credentials = new ArrayList<>();
        for (CredentialsWrapper wrapper : baseRepository.getAll()) {
            credentials.addAll(wrapper.getCredentials());
        }
        return credentials;
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
