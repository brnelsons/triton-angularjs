package com.bnelson.triton.security;

import com.bnelson.triton.domain.data.CredentialsRepository;
import com.bnelson.triton.domain.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final boolean SECURE = true;
    private PersistentTokenRepository persistentTokenRepository;
    private CredentialsRepository credentialsRepository;

    @Autowired
    public SecurityConfiguration(PersistentTokenRepository persistentTokenRepository,
                                 CredentialsRepository credentialsRepository) {
        this.persistentTokenRepository = persistentTokenRepository;
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if(SECURE) {
            http.authorizeRequests()
                    .antMatchers("/res/**").permitAll()
                    .anyRequest().authenticated();
            http.formLogin().failureUrl("/login?error")
                    .defaultSuccessUrl("/")
                    .loginPage("/login")
                    .permitAll()
                    .and()
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                    .permitAll();
            http.rememberMe()
                    .tokenRepository(persistentTokenRepository)
                    .tokenValiditySeconds((int)TimeUnit.HOURS.toSeconds(8));
        }else{
            http.authorizeRequests().anyRequest().permitAll();
        }
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> authBuilder = auth.inMemoryAuthentication();
        Collection<Credential> values = credentialsRepository.getAll().values();
        for (Credential credential : values) {
            authBuilder.withUser(credential.getUsername())
                    .password(credential.getPassword())
                    .roles(credential.getRole().name());
        }
        if(values.size() == 0) {
            authBuilder.withUser("admin").password("password").roles("ADMIN");
        }
    }
}