package com.bnelson.triton.domain.data;

import com.bnelson.triton.domain.model.Credential;
import com.bnelson.triton.domain.model.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CredentialRepositoryImplTest {

    @Mock
    private FileRepository<CredentialsRepositoryImpl.CredentialsWrapper, Role> baseRepository;

    private CredentialsRepository credentialsRepository;

    @Before
    public void setup() {
        credentialsRepository = new CredentialsRepositoryImpl(baseRepository);
        when(baseRepository.save(any(), eq(true)))
                .thenReturn(true);
    }

    @Test
    public void save() {
        Credential cred = new Credential("Admin", "Password", Role.ADMIN);
        credentialsRepository.create(cred);
        verify(baseRepository, times(1)).getOneLike(any());
        verify(baseRepository, times(1)).save(any(), eq(true));
        verifyNoMoreInteractions(baseRepository);
    }

}