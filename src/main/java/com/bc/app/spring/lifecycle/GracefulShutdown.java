package com.bc.app.spring.lifecycle;

import com.bc.app.spring.server.TomcatProperties;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 21, 2019 12:05:34 PM
 * @see https://dzone.com/articles/graceful-shutdown-spring-boot-applications
 */
public class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
    
    private transient static final Logger LOG = LoggerFactory.getLogger(GracefulShutdown.class);
    
    private volatile Connector connector;
    
    @Autowired
    private TomcatProperties tomcatProperties;
    
    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }
    
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        
        if(this.connector == null) {
            LOG.info("Tomcat may not have been started");
            return;
        }

        this.connector.pause();

        final Executor executor = this.connector.getProtocolHandler().getExecutor();

        if (executor instanceof ThreadPoolExecutor) {
            try {
                
                final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                
                LOG.info("Shutting down Tomcat thread pool");
                
                threadPoolExecutor.shutdown();

                final int serverShutdownTimeoutMillis = tomcatProperties.getShutdownTimeoutMillis();
                
                LOG.debug("Tomcat shutdownTimeoutMillis: {}", serverShutdownTimeoutMillis);
                
                if ( ! threadPoolExecutor.awaitTermination(serverShutdownTimeoutMillis, TimeUnit.SECONDS)) {
                    
                    LOG.warn("Tomcat thread pool did not shut down gracefully within {} millis. Proceeding with forceful shutdown", serverShutdownTimeoutMillis);
                    
                }else{
                    
                    LOG.info("Tomcat thread pool shutdown complete");
                }
            } catch (InterruptedException ex) {
                
                LOG.warn(ex.toString());
                
                Thread.currentThread().interrupt();
            }
        }
    }
}