/*
 * Copyright 2019 NUROX Ltd.
 *
 * Licensed under the NUROX Ltd Software License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.looseboxes.com/legal/licenses/software.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bc.app.spring.lifecycle;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.ResolvableType;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 21, 2019 3:47:10 PM
 * @see https://stackoverflow.com/questions/45810352/register-custom-log-appender-in-spring-boot-starter
 */
public class LoggingConfigurationApplicationListener implements GenericApplicationListener {
    
//    private transient static final Logger LOG = LoggerFactory.getLogger(LoggingConfigurationApplicationListener.class);

    private boolean addedCustomAppender;
    
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if ( ! addedCustomAppender) {
//            ApplicationPreparedEvent applicationEvent = (ApplicationPreparedEvent) event;
//            EventsPublisher eventPublisher = applicationEvent.getApplicationContext().getBean(EventsPublisher.class);
//            final Appender<ILoggingEvent> newAppender = new MyCustomAppender(eventPublisher);
//            final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
//            final ch.qos.logback.classic.Logger root = context.getLogger("ROOT");
//            newAppender.setName("Custom Appender");
//            newAppender.setContext(context);
//            newAppender.start();
//            root.addAppender(newAppender);
//            LOG.info("Added custom logback appender of type: {} to ROOT Logger", newAppender.getClass());
            
            addedCustomAppender = true;
        }
    }

    @Override
    public int getOrder() {
        // this must be higher than LoggingApplicationListener.DEFAULT_ORDER
        return Ordered.HIGHEST_PRECEDENCE + 21;
    }

    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        // this is the earliest 'event type' which is capable of exposing the application context
        return ApplicationPreparedEvent.class.isAssignableFrom(eventType.getRawClass());
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }
}