package com.bc.app.spring.lifecycle;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 21, 2019 12:11:19 PM
 * @see https://dzone.com/articles/graceful-shutdown-spring-boot-applications
 */
@Configuration
public class LifecycleConfiguration {

    @Bean
    public OnDestroyContainer getTerminateBean() {
        return new OnDestroyContainer();
    }
    
    @Bean
    public GracefulShutdown getGracefulShutdownBean() {
        return new GracefulShutdown();
    }
    
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory(final GracefulShutdown gracefulShutdown) {
        final TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(gracefulShutdown);
        return factory;
    }
}
