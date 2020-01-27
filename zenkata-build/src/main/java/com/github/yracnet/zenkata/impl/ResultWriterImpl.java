/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.zenkata.impl;

import com.github.yracnet.zenkata.xml.ResultFile;
import com.github.yracnet.zenkata.xml.ResultGroup;
import com.github.yracnet.zenkata.Result;
import com.github.yracnet.zenkata.ResultWriter;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yracnet
 */
public class ResultWriterImpl implements ResultWriter {

    private static final Logger LOGGER = Logger.getLogger(ResultWriterImpl.class.getName());

    @Override
    public void write(Result result, File dir) {
        if (result.isSkip()) {

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
