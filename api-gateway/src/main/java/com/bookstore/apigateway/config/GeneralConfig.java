package com.bookstore.apigateway.config;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableEurekaClient
@ComponentScan("com.bookstore.securitymodule.*")
@Configuration
@EnableZuulProxy
public class GeneralConfig {
}
