/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.zenkata.impl;

import com.github.yracnet.zenkata.EntryMask;
import com.github.yracnet.zenkata.EntryReader;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import groovy.text.TemplateEngine;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.codehaus.groovy.control.CompilationFailedException;

/**
 *
 * @author yracnet
 */
@Setter
@Getter
@ToString
public class EntryReaderImpl implements EntryReader {

    private static final Logger LOGGER = Logger.getLogger(EntryReaderImpl.class.getName());

    private TemplateEngine engine = new SimpleTemplateEngine();
    private ClassLoader classLoader = EntryReaderImpl.class.getClassLoader();
    private final List<File> directory = new ArrayList<>();

    @Override
    public void addDirectory(String path) {
        directory.add(new File(path));
    }

    public void addDirectory(File path) {
        directory.add(path);
    }

    @Override
    public EntryMask readMask(String name) {
        File file = searchFirstFile(name);
        return createMask(file);
    }

    @Override
    public List<EntryMask> readMaskRecursive(String name) {
        LOGGER.log(Level.INFO, "ADD MASK NAME {0}", name);
        File dir = searchFirstFile(name);
        List<EntryMask> list = new ArrayList<>();
        if (dir.isDirectory()) {
            File files[] = dir.listFiles(it -> it.isFile());
            for (File file : files) {
                LOGGER.log(Level.INFO, "ADD MASK {0}", file);
                EntryMask mask = createMask(file);
                list.add(mask);
            }
        }
        return list;
    }

    private EntryMask createMask(File file) {
        EntryMaskImpl mask = new EntryMaskImpl();
        mask.setFile(file);
        if (file != null && file.exists()) {
            try {
                Template template = engine.createTemplate(file);
                mask.setTemplate(template);
            } catch (IOException | ClassNotFoundException | CompilationFailedException e) {
                //mask.setException(e);
            }
        }
        return mask;
    }

    private File searchFirstFile(String name) {
        for (File dir : directory) {
            File file = new File(dir, name);
            if (file.exists()) {
                return file;
            }
        }
        URL url = classLoader.getResource(name);
        if (url != null) {
            return new File(url.getFile());
        }
        return null;
    }

}
