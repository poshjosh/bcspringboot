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

package com.bc.app.spring.server;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @see https://www.baeldung.com/configuration-properties-in-spring-boot
 * @author Chinomso Bassey Ikwuagwu on Mar 21, 2019 1:01:53 PM
 */
@Configuration
//@PropertySource("classpath:server.properties")
@ConfigurationProperties(prefix = "tomcat")
//@Profile("!test")
public class TomcatProperties {
 
    @Min(1)
    @Max(65536)
    private int shutdownTimeoutMillis = 3000;

    public TomcatProperties() { }
    
// May pecify default value with @Value annotation    
//    public TomcatProperties(@Value("${shutdownTimeoutMillis:3000}") int shutdownTimeoutMillis) {
//        this.shutdownTimeoutMillis = validateShutdownTimeout(shutdownTimeoutMillis);
//    }

    public TomcatProperties(int shutdownTimeoutMillis) {
        this.shutdownTimeoutMillis = validateShutdownTimeout(shutdownTimeoutMillis);
    }
 
    public int validateShutdownTimeout(int shutdownTimeoutMillis) {
        if (shutdownTimeoutMillis < 1) {
            throw new IllegalArgumentException("Shutdown timeout may NOT be < 1");
        }
        return shutdownTimeoutMillis;
    }

    public int getShutdownTimeoutMillis() {
        return shutdownTimeoutMillis;
    }

    public void setShutdownTimeoutMillis(int shutdownTimeoutMillis) {
        this.shutdownTimeoutMillis = shutdownTimeoutMillis;
    }
}
