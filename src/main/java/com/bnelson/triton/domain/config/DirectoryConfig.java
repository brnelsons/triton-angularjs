package com.bnelson.triton.domain.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class DirectoryConfig {

    public static final String HOME_DIR = "dir.home";
    public static final String SECURITY_DIR = "dir.security";
    public static final String CONFIG_DIR = "dir.config";
    public static final String GAME_DIR = "dir.game";

    private static final String SLASH = File.separator;
    private static final String TRITON = "triton";

    @Bean(HOME_DIR)
    public String getUserHome() {
        return System.getProperty("user.home");
    }

    @Bean(SECURITY_DIR)
    public String getSecurityHome(@Qualifier(HOME_DIR) String homeDir) {
        return homeDir + SLASH + TRITON + SLASH + "security";
    }

    @Bean(CONFIG_DIR)
    public String getConfigDir(@Qualifier(HOME_DIR) String homeDir) {
        return homeDir + SLASH + TRITON + SLASH + "config";
    }

    @Bean(GAME_DIR)
    public String getGameDir(@Qualifier(HOME_DIR) String homeDir) {
        return homeDir + SLASH + TRITON + SLASH + "games";
    }

//    @Bean(GAME_DIR)
//    public String getGameDir(@Qualifier(HOME_DIR) String homeDir,
//                             FileRepository<BasicConfig, String> basicConfigRepository){
////        if(basicConfigRepository.get)
//    }

}
