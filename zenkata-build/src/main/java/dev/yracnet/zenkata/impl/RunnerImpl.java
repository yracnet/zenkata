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

import dev.yracnet.zenkata.Runner;
import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.codehaus.groovy.control.CompilationFailedException;

/**
 *
 * @author Willyams Yujra
 */
@ToString
@Getter
@Setter
public class RunnerImpl implements Runner {
	private File file;
	private File directory;
	public RunnerImpl(File file) {
		this.file = file;
		this.directory = file.getParentFile();
	}

	@Override
	public String include(String name, Map binding) {
		return include(name, directory, binding);
	}

	@Override
	public String include(String name, File directory, Map binding) {
		if (directory == null) {
			directory = new File("");
		}
		File template = new File(directory, name);
		if (!template.exists()) {
			return "Not Found: " + template.getPath();
		}
		try {
			SimpleTemplateEngine engine = new SimpleTemplateEngine();
			Map current = new HashMap();
			if (binding != null) {
				current.putAll(binding);
			}
			Writable writable = engine.createTemplate(template).make(current);
			return writable.toString();
		} catch (IOException | ClassNotFoundException | CompilationFailedException e) {
			return "Exception: " + e.getMessage();
		}
	}
}
