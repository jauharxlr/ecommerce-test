package com.ecommerce.userservice.config;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableEurekaClient
@Configuration
@EnableJpaAuditing
@ComponentScan("com.ecommerce.securitymodule.*")
public class GeneralConfig {
}
