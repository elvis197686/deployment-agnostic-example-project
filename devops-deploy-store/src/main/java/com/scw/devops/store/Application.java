package com.scw.devops.store;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( basePackages = { "com.scw.devops.store", "com.scw.devops.contract.store", "com.scw.devops.deploy.config" } )
public class Application {

    public static void main(final String[] args) {
        new SpringApplicationBuilder(Application.class)
            .run(args);
    }

}
