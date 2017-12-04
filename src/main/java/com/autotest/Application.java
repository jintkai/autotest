package com.autotest;

import com.autotest.dao.CaseMapper;
import com.autotest.model.Case;
import com.autotest.model.Variable;
import com.autotest.service.impl.VarExpressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com"})
@EnableAutoConfiguration
public class Application implements EmbeddedServletContainerCustomizer {

    @Autowired
    VarExpressServiceImpl varExpressService;

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        configurableEmbeddedServletContainer.setPort(8096);
    }

    }

