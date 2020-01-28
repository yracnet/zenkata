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
package dev.yracnet.zenkata.impl;

import dev.yracnet.zenkata.xml.ResultGroup;
import dev.yracnet.zenkata.xml.ResultFile;
import dev.yracnet.zenkata.Result;
import dev.yracnet.zenkata.ResultReader;
import groovy.lang.Writable;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Unmarshaller.Listener;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Willyams Yujra
 */
public class ResultReaderImpl implements ResultReader {

    private static final Logger LOGGER = Logger.getLogger(ResultReaderImpl.class.getName());

    private static final Unmarshaller UNMARSHALLER;

    static {
        Unmarshaller val = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(new Class<?>[]{ResultGroup.class, ResultFile.class});
            val = jaxbContext.createUnmarshaller();
            ParentListener parentListener = new ParentListener();
            val.setListener(parentListener);
        } catch (JAXBException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Error Instance", e);
        }
        UNMARSHALLER = val;
    }

    static class ParentListener extends Listener {

        @Override
        public void beforeUnmarshal(Object target, Object parent) {
            ResultGroup resultGroup = null;
            if (parent instanceof ResultGroup) {
                resultGroup = (ResultGroup) parent;
            }
            if (target instanceof ResultFile) {
                ResultFile resultFile = (ResultFile) target;
                resultFile.setParent(resultGroup);
            }
        }

    }

    @Override
    public Result read(Object value) {
        if (value instanceof Result) {
            return (Result) value;
        }
        if (value instanceof Writable) {
            value = value.toString();
        }
        if (value instanceof String) {
            try {
                String xml = (String) value;
                LOGGER.log(Level.FINE, "XML From: {0}", xml.replace("\n", "\\n"));
                xml = ZenkataHelp.sanetizeCDATA(xml);
                LOGGER.log(Level.INFO, "XML Fixer: {0}", xml.replace("\n", "\\n"));
                StreamSource source = new StreamSource(new StringReader(xml));
                return (Result) UNMARSHALLER.unmarshal(source);
            } catch (JAXBException e) {
                LOGGER.log(Level.SEVERE, "Error XML Result", e);
            }
        }
        return null;
    }

}
