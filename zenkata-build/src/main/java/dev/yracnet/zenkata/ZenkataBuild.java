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
package dev.yracnet.zenkata;

import dev.yracnet.zenkata.impl.ZenkataBuildImpl;
import dev.yracnet.zenkata.xml.ResultGroup;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

/**
 *
 * @author wyujra
 */
public abstract class ZenkataBuild {
	static {
		InputStream stream = ZenkataBuild.class.getClassLoader().getResourceAsStream("logging.properties");
		try {
			LogManager.getLogManager().readConfiguration(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static final ZenkataBuild INSTANCE = new ZenkataBuildImpl();

	public static ZenkataBuild getInstance() {
		return INSTANCE;
	}

	public abstract File getCurrentDirectory();

	public abstract File getOutput();

	public abstract void setOutput(File output);

	public abstract void setOutput(String output);

	public abstract void setOutput(String output, File current);

	public abstract void addDirectory(String value);

	public abstract void addMask(EntryMask value);

	public abstract void addMaskString(String name);

	public abstract void addMaskDirectory(String name);

	public abstract void addItem(EntryItem value);

	public abstract void addItemBlank();

	public abstract void addFactory(EntryConvert value);

	public abstract void putParser(String name, ResultParser value);

	public abstract ResultParser getParser(String name);

	public abstract void putLayer(String name, String value);

	public abstract void putContext(String name, Object value);

	public abstract Result generate();

	public abstract void processMask(EntryItem item, EntryItem parent, EntryMask mask, ResultGroup result);

	public abstract Object applyConvert(EntryItem item);
}
