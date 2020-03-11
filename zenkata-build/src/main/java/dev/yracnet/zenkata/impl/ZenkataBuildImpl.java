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

import dev.yracnet.zenkata.EntryItem;
import dev.yracnet.zenkata.EntryMask;
import dev.yracnet.zenkata.EntryReader;
import dev.yracnet.zenkata.Result;
import dev.yracnet.zenkata.ResultReader;
import dev.yracnet.zenkata.ResultWriter;
import dev.yracnet.zenkata.ZenkataBuild;
import dev.yracnet.zenkata.xml.ResultGroup;
import dev.yracnet.formatter.FormatterBuild;
import dev.yracnet.formatter.FormatterException;
import groovy.lang.Writable;
import groovy.text.Template;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import dev.yracnet.zenkata.ResultParser;
import dev.yracnet.zenkata.EntryConvert;
import dev.yracnet.zenkata.ResultException;
import groovy.text.SimpleTemplateEngine;
import java.io.IOException;
import org.codehaus.groovy.control.CompilationFailedException;

/**
 *
 * @author Willyams Yujra
 */
@ToString
@Getter
@Setter
public class ZenkataBuildImpl extends ZenkataBuild implements Serializable {
	private static final Logger LOGGER = Logger.getLogger(ZenkataBuild.class.getName());
	private File output;
	private ResultWriter resultWriter = new ResultWriterImpl(this);
	private ResultReader resultReader = new ResultReaderImpl();
	private EntryReader entryReader = new EntryReaderImpl();
	private final List<EntryMask> maskList = new ArrayList();
	private final List<EntryItem> itemList = new ArrayList();
	private final List<EntryConvert> convertList = new ArrayList();
	private final Map<String, ResultParser> parser = new HashMap();
	private final Map<String, Object> context = new HashMap();
	@Override
	public File getCurrentDirectory() {
		return new File("").getAbsoluteFile();
	}

	@Override
	public File getOutput() {
		return output;
	}

	@Override
	public void setOutput(File output) {
		this.output = output;
		this.output = ZenkataHelp.sanetizePath(this.output);
	}

	@Override
	public void setOutput(String output) {
		this.output = new File(output);
		this.output = ZenkataHelp.sanetizePath(this.output);
	}

	@Override
	public void setOutput(String output, File current) {
		this.output = new File(current, output);
		this.output = ZenkataHelp.sanetizePath(this.output);
	}

	@Override
	public void addSearchDirectory(String value) {
		if (value != null) {
			entryReader.addSearchDirectory(value);
		}
	}

	@Override
	public void addMask(EntryMask value) {
		if (value != null) {
			maskList.add(value);
		}
	}

	@Override
	public void addMaskString(String name) {
		if (name != null) {
			EntryMask value = entryReader.readMask(name);
			maskList.add(value);
		}
	}

	@Override
	public void addMaskDirectory(String name) {
		if (name != null) {
			List<EntryMask> values = entryReader.readMaskRecursive(name);
			maskList.addAll(values);
		}
	}

	@Override
	public void addItem(EntryItem value) {
		if (value != null) {
			itemList.add(value);
		}
	}

	@Override
	public void addItemBlank() {
		itemList.clear();
		itemList.add(EntryItemImpl.BLANK);
	}

	@Override
	public void addConvert(EntryConvert value) {
		if (value != null) {
			convertList.add(value);
		}
	}

	@Override
	public void putParser(String name, ResultParser value) {
		parser.put(name, value);
	}

	@Override
	public ResultParser getParser(String name) {
		return parser.getOrDefault(name, ResultParser.DEFAULT);
	}

	@Override
	public void putContext(String name, Object value) {
		context.put(name, value);
	}

	@Override
	public Result generate() {
		ResultGroup result = new ResultGroup();
		itemList.forEach(item -> maskList.forEach(mask -> processMask(item, null, mask, result)));
		try {
			resultWriter.write(result, output);
		} catch (ResultException e) {
			LOGGER.log(Level.SEVERE, "Error write Result", e);
		}
		try {
			FormatterBuild build = FormatterBuild.create();
			build.setBasedir(output);
			build.setDirectories(output);
			build.execute();
		} catch (FormatterException e) {
			LOGGER.log(Level.SEVERE, "Error format Code", e);
		}
		return result;
	}

	@Override
	public void clear() {
		maskList.clear();
		itemList.clear();
		context.clear();
		convertList.clear();
		parser.clear();
	}

	@Override
	public void processMask(EntryItem item, EntryItem itemParent, EntryMask mask, ResultGroup result) {
		LOGGER.log(Level.FINE, "processMask: {0} - {1} - {2}", new Object[]{item, itemParent, mask});
		Map binding = new HashMap();
		Object value = applyConvert(item);
		Object parent = applyConvert(itemParent);
		binding.put("_entry", value);
		binding.put("_parent", parent);
		binding.put("_context", context);
		binding.put("_build", this);
		binding.put("_runner", new RunnerImpl(mask.getFile()));
		LOGGER.log(Level.FINE, "processMask: Context: {0}", binding);
		try {
			Template template = mask.getTemplate();
			Writable out = template.make(binding);
			Result temp = resultReader.read(out);
			LOGGER.log(Level.FINE, "processMask: Result: {0}", temp);
			result.addResult(temp);
		} catch (ResultException e) {
			LOGGER.log(Level.SEVERE, "processMask: Exception Result: {0} : {1}", new Object[]{mask.getFile(), e});
		}
	}

	@Override
	public Object applyConvert(EntryItem item) {
		for (var factory : convertList) {
			if (factory.test(item)) {
				return factory.apply(item);
			}
		}
		return EntryConvert.NONE.apply(item);
	}
}
