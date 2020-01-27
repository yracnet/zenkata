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
package com.github.yracnet.zenkata.impl;

import com.github.yracnet.zenkata.xml.ResultGroup;
import com.github.yracnet.zenkata.xml.ResultFile;
import com.github.yracnet.zenkata.Result;
import com.github.yracnet.zenkata.ResultReader;
import groovy.lang.Writable;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Willyams Yujra
 */
public class ResultReaderImpl implements ResultReader {

    private static final Logger LOGGER = Logger.getLogger(ResultReaderImpl.class.getName());

    private static final Unmarshaller unmarshaller;

    static {
        Unmarshaller val = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(new Class<?>[]{ResultGroup.class, ResultFile.class});
            val = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            LOGGER.log(Level.SEVERE, "Error Instance", e);
        }
        unmarshaller = val;
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
                //REMOVE CDATA
                xml = xml.replaceAll("<result-file([^>]*)>(\\s)*<!\\[CDATA\\[(\\s)*", "<result-file$1>");
                //xml = xml.replaceAll("<result-file>(\\s)*<!\\[CDATA\\[(\\s)*", "<result-file>");
                xml = xml.replaceAll("(\\s)*\\]\\]>(\\s)*</result-file>", "</result-file>");
                //ATTACH CDATA
                xml = xml.replaceAll("<result-file([^>]*)>", "<result-file$1><![CDATA[");
                //xml = xml.replace("<result-file>", "<result-file><![CDATA[");
                xml = xml.replace("</result-file>", "]]></result-file>");
                LOGGER.log(Level.INFO, "XML Fixer: {0}", xml.replace("\n", "\\n"));
                StreamSource source = new StreamSource(new StringReader(xml));
                return (Result) unmarshaller.unmarshal(source);
            } catch (JAXBException e) {
                LOGGER.log(Level.SEVERE, "Error XML Result", e);
            }
        }
        return null;
    }

}
