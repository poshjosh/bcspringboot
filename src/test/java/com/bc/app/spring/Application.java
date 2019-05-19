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

package com.bc.app.spring;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 3, 2019 4:19:15 PM
 */
@SpringBootApplication
public class Application {

    public static void main(String... args) {
        
//        final ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        final ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Application.class)
//                .web(WebApplicationType.SERVLET)
                .headless(false).run(args);
        
        ctx.registerShutdownHook();
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame("Test Frame - Closing this Frame shuts down the web server");
            frame.setPreferredSize(new Dimension(500, 350));
            frame.pack();
            frame.setVisible(true);
            frame.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        });
    }
}
