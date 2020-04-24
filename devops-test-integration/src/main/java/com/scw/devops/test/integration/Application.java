package com.scw.devops.test.integration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

// We must use Spring as that has flexible testing, DI and config, which isn't offered to the same quality in any other projects
@SpringBootApplication
@ComponentScan( basePackages = { "com.scw.devops.collector", "com.scw.devops.store", "com.scw.devops.query",
								 "com.scw.devops.test.integration.config", "com.scw.devops.test.integration.contract.store" } )
public class Application {

    public static void main(final String[] args) {
        new SpringApplicationBuilder(Application.class)
            .run(args);
    }

}
