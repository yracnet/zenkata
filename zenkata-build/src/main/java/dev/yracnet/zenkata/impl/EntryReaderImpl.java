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

import dev.yracnet.zenkata.EntryException;
import dev.yracnet.zenkata.EntryMask;
import dev.yracnet.zenkata.EntryReader;
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
 * @author Willyams Yujra
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
	public void addSearchDirectory(String path) {
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
		if (dir != null && dir.isDirectory()) {
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
				String xml = ZenkataHelp.processMaskXml(file);
				Template template = engine.createTemplate(xml);
				mask.setTemplate(template);
			} catch (EntryException | IOException | ClassNotFoundException | CompilationFailedException e) {
				// mask.setException(e);
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
