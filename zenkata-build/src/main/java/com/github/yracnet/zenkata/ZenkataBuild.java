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
package com.github.yracnet.zenkata;

import com.github.yracnet.zenkata.impl.EntryItemImpl;
import com.github.yracnet.zenkata.xml.ResultGroup;
import com.github.yracnet.zenkata.impl.ResultWriterImpl;
import com.github.yracnet.zenkata.impl.ResultReaderImpl;
import com.github.yracnet.zenkata.impl.EntryReaderImpl;
import groovy.lang.Writable;
import groovy.text.Template;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Willyams Yujra
 */
@ToString
@Getter
@Setter
public class ZenkataBuild implements Serializable {

    static {
        InputStream stream = ZenkataBuild.class.getClassLoader().getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Logger LOGGER = Logger.getLogger(ZenkataBuild.class.getName());
    private File output;
    private ResultWriter resultWriter = new ResultWriterImpl();
    private ResultReader resultReader = new ResultReaderImpl();
    private EntryReader entryReader = new EntryReaderImpl();
    private final List<EntryMask> maskList = new ArrayList();
    private final List<EntryItem> itemList = new ArrayList();
    private final List<Factory> factoryList = new ArrayList();
    private final Map<String, Parser> parser = new HashMap();
    private final Map<String, String> layer = new HashMap();
    private final Map<String, Object> context = new HashMap();

    public void setOutput(String output) {
        this.output = new File(output);
    }

    public void addDirectory(String value) {
        if (value != null) {
            entryReader.addDirectory(value);
        }
    }

    public void addMask(EntryMask value) {
        if (value != null) {
            maskList.add(value);
        }
    }

    public void addMaskString(String name) {
        if (name != null) {
            EntryMask value = entryReader.readMask(name);
            maskList.add(value);
        }
    }

    public void addMaskDirectory(String name) {
        if (name != null) {
            List<EntryMask> values = entryReader.readMaskRecursive(name);
            maskList.addAll(values);
        }
    }

    public void addItem(EntryItem value) {
        if (value != null) {
            itemList.add(value);
        }
    }

    public void addItemBlank() {
        itemList.clear();
        itemList.add(EntryItemImpl.BLANK);
    }

    public void addFactory(Factory value) {
        if (value != null) {
            factoryList.add(value);
        }
    }

    public void putParser(String name, Parser value) {
        parser.put(name, value);
    }

    public void putLayer(String name, String value) {
        layer.put(name, value);
    }

    public void putContext(String name, Object value) {
        context.put(name, value);
    }

    public Result generate() {
        ResultGroup result = new ResultGroup();
        itemList.forEach(item -> maskList.forEach(mask -> processMask(item, null, mask, result)));
        resultWriter.write(result, output);
        return result;
    }

    public void processMask(EntryItem item, EntryItem parent, EntryMask mask, ResultGroup result) {
        LOGGER.log(Level.FINE, "processMask: {0} - {1} - {2}", new Object[]{item, parent, mask});
        Map current = new HashMap();
        //Object objParent = factoryApply(parent);
        //Object fn = factoryApply(item);
        //current.put("_fn", fn);
        current.put("_build", this);
        //current.put("_parent", parent.getObject());
        //current.put("_item", item.getObject());
        current.putAll(context);
        LOGGER.log(Level.FINE, "processMask: Context: {0}", current);
        Template template = mask.getTemplate();
        Writable out = template.make(current);
        Result temp = resultReader.read(out);
        LOGGER.log(Level.FINE, "processMask: Result: {0}", temp);
        result.addResult(temp);
    }

    public Object factoryApply(EntryItem item) {
        Object object = item.getObject();
        for (var factory : factoryList) {
            if (factory.test(item)) {
                return factory.apply(object);
            }
        }
        return object;
    }

}
