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

import dev.yracnet.zenkata.xml.ResultFile;
import dev.yracnet.zenkata.xml.ResultGroup;
import dev.yracnet.zenkata.Result;
import dev.yracnet.zenkata.ResultWriter;
import dev.yracnet.zenkata.ZenkataBuild;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import dev.yracnet.zenkata.ResultParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author Willyams Yujra
 */
public class ResultWriterImpl implements ResultWriter {

    private static final Logger LOGGER = Logger.getLogger(ResultWriterImpl.class.getName());

    private final ZenkataBuild build;

    public ResultWriterImpl(ZenkataBuild build) {
        this.build = build;
    }

    @Override
    public void write(Result result, File dir) {
        if (result.isSkip()) {
            LOGGER.log(Level.INFO, "Ignore Write Result: {0}", result);
        } else if (result instanceof ResultFile) {
            try {
                writeFile((ResultFile) result, dir);
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.log(Level.SEVERE, "Error Write Result", e);
            }
        } else if (result instanceof ResultGroup) {
            ResultGroup group = (ResultGroup) result;
            group.getResutlList().forEach(it -> write(it, dir));
        }
    }

    private void writeFile(ResultFile result, File dir) throws IOException {
        LOGGER.log(Level.FINE, "Write Directory: {0}", dir);
        LOGGER.log(Level.FINE, "Write Result: {0}", result);
        LOGGER.log(Level.INFO, "Write Result: {0}", result.getResultPath());
        ResultParser parser = build.getParser(result.getParser());
        result = parser.parser(result);
        File out = new File(dir, result.getResultPath());
        File parent = out.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (!result.isAppend()) {
            out.delete();
        }
        Path path = Paths.get(out.toURI());
        LOGGER.log(Level.INFO, "Write Result: {0}", path);
        byte[] comment = result.getCommentBytes();
        if (comment != null) {
            StandardOpenOption option = out.exists() ? StandardOpenOption.APPEND : StandardOpenOption.CREATE;
            Files.write(path, comment, option);
        }
        byte[] content = result.getContentBytes();
        if (content != null) {
            StandardOpenOption option = out.exists() ? StandardOpenOption.APPEND : StandardOpenOption.CREATE;
            Files.write(path, content, option);
        }
    }
}
