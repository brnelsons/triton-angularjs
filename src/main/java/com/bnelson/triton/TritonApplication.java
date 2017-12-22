package com.bnelson.triton;

import com.bnelson.triton.domain.config.DirectoryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TritonApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(TritonApplication.class, args);
        System.out.println(run.getBean(DirectoryConfig.HOME_DIR));
		System.out.println("http://localhost:8080");
	}
}
