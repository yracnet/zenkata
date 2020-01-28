/**
 * Copyright © 2020 ${owner} (${email})
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.zenkata.impl;

import dev.yracnet.zenkata.xml.ResultFile;
import dev.yracnet.zenkata.xml.ResultGroup;
import dev.yracnet.zenkata.Result;
import dev.yracnet.zenkata.ResultWriter;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Willyams Yujra
 */
public class ResultWriterImpl implements ResultWriter {

    private static final Logger LOGGER = Logger.getLogger(ResultWriterImpl.class.getName());

    @Override
    public void write(Result result, File dir) {
        if (result.isSkip()) {
            LOGGER.log(Level.INFO, "Ignore Write Result: {0}", result);
        } else if (result instanceof ResultFile) {
            writeFile((ResultFile) result, dir);
        } else if (result instanceof ResultGroup) {
            ResultGroup group = (ResultGroup) result;
            group.getResutlList().forEach(it -> write(it, dir));
        }
    }

    private void writeFile(ResultFile result, File dir) {
        LOGGER.log(Level.FINE, "Write Directory: {0}", dir);
        LOGGER.log(Level.FINE, "Write Result: {0}", result);
        LOGGER.log(Level.INFO, "Write Result: {0}", result.getFile());
        String content = result.getContent();
        String parse = result.getParser();
        
        //File  file = result.getFile();
        

    }

//    public void writeFile(ResultFile result) {
//        Parser parser = config.getParserByName(result.getParser());
//        result = (ResultFile)parser.parser(result);
//        
//        File file = result.getDir();
//        File directory = file.getParentFile();
//        if(!directory.exists()){
//            directory.mkdirs();
//        }
//        if(!result.isAppend()){
//            file.delete();
//        }
//        Byte[] content = result.getContentBytes();
//       Files.write(Paths.get(file.toURI()), content, StandardOpenOption.APPEND);
//        
//        
//        
//        
//        
//        File dir = new File
//        
//        write(result.getResutlList(), config, writer);
//    }
}
