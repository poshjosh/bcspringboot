package com.bc.app.spring.lifecycle;

import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 21, 2019 12:29:15 PM
 */
public class OnDestroyContainer {
 
    private transient static final Logger LOG = LoggerFactory.getLogger(OnDestroyContainer.class);

    @PreDestroy
    public void onDestroy() throws Exception {
        LOG.info("Spring Container is destroyed");
    }
}