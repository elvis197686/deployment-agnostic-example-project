package com.scw.devops.collector;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( basePackages = { "com.scw.devops.collector", "com.scw.devops.contract.store", "com.scw.devops.contract.collector",
								 "com.scw.devops.deploy.config" } )
public class Application {

    public static void main(final String[] args) {
        new SpringApplicationBuilder(Application.class)
            .run(args);
    }

}
