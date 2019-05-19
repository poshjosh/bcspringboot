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

package com.bc.app.spring.services;

import com.bc.app.spring.exceptions.FileStorageException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 27, 2019 10:43:15 PM
 */
public class StoreFileToLocalDisc implements FileStorage<Path> {

//    private static final Logger LOG = LoggerFactory.getLogger(StoreFileToLocalDisc.class);

    @Override
    public Path store(Path path, InputStream in) {
        
        try {
            this.copy(in, path);

            return path;
            
        } catch (IOException ex) {
            
            throw new FileStorageException("Could not store file to: " + path + ". Please try again!", ex);
        }
    }

    @Override
    public InputStream load(Path path) {
        
        try {
            
            return Files.newInputStream(path);
            
        } catch (IOException ex) {
            
            throw new FileStorageException("Could not read content from: " + path + ". Please try again!", ex);
        }
    }
    
    public void copy(InputStream source, Path target) throws IOException{
        
        Files.copy(source, target, this.getCopyOptions());
    }
    
    public CopyOption[] getCopyOptions() {
        return new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
    }
}
