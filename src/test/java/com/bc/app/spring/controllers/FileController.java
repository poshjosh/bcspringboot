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

package com.bc.app.spring.controllers;

import com.bc.app.spring.services.FileStorageHandler;
import com.bc.app.spring.entities.UploadFileResponse;
import com.bc.app.spring.functions.GetUniquePathForFilename;
import com.bc.app.spring.services.FileStorage;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 27, 2019 7:42:52 PM
 */
@RestController
public class FileController {

//    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    private final String downloadPathContext = "/downloadFile";

    private final FileStorageHandler delegate;

    public FileController(
            @Autowired GetUniquePathForFilename getPathForFilename, 
            @Autowired FileStorage fileStorage) {
        this.delegate = new FileStorageHandler(getPathForFilename, fileStorage, this.downloadPathContext);
    }
    
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        return delegate.uploadFile(file);
    }

    @PostMapping("/uploadFiles")
    public List<UploadFileResponse> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        return delegate.uploadFiles(files);
    }

    @GetMapping(downloadPathContext + "/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        return delegate.downloadFile(fileName, request);
    }
}