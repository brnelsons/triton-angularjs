package com.bnelson.triton.api;

import com.bnelson.triton.domain.data.ConfigRepository;
import com.bnelson.triton.domain.model.BasicConfig;
import com.bnelson.triton.domain.model.Credential;
import com.bnelson.triton.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/settings")
public class SettingsRestController {

    private final ConfigRepository configRepository;
    private final CredentialsService credentialsService;

    @Autowired
    public SettingsRestController(ConfigRepository configRepository,
                                  CredentialsService credentialsService) {
        this.configRepository = configRepository;
        this.credentialsService = credentialsService;
    }

    @GetMapping("/config")
    public List<BasicConfig> getAllConfigs() {
        return configRepository.getAll();
    }

    @PostMapping("/config/save")
    public boolean saveAllConfigs(@RequestBody List<BasicConfig> basicConfigs) {
        return configRepository.save(basicConfigs);
    }

    @PostMapping("/config/update")
    public boolean updateConfig(@RequestBody BasicConfig basicConfig) {
        return configRepository.update(basicConfig);
    }

    @GetMapping("/users")
    public List<Credential> getAllUsers(Authentication authentication) {
//        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
//            if(grantedAuthority.getAuthority().equals(Role.ADMIN.name())){
//                return credentialsService.getAll();
//            }
//        }
//        return credentialsService.getByUsername(((Principal)authentication.getPrincipal()).getName());
        return credentialsService.getAll();
    }

    @PostMapping("/users/save")
    public boolean saveAllUsers(@RequestBody List<Credential> credentials) {
        return credentialsService.saveAll(credentials);
    }
}
