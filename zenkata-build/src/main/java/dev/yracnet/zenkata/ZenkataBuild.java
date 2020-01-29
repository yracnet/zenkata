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
 @author Willyams Yujra
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

	/**
	 * Return Current directory
	 * 
	 * @return
	 */
	public abstract File getCurrentDirectory();

	/**
	 * Return the output directory
	 * 
	 * @return
	 */
	public abstract File getOutput();

	/**
	 * Set Output directory file
	 * 
	 * @param output
	 */
	public abstract void setOutput(File output);

	/**
	 * Set Output directory as String
	 * 
	 * @param output
	 */
	public abstract void setOutput(String output);

	/**
	 * Set Output directory as string and parent directory
	 * 
	 * @param output
	 *         sub directory
	 * @param current
	 *         parent directory
	 */
	public abstract void setOutput(String output, File current);

	/**
	 * Add directory for search mask
	 * 
	 * @param value
	 */
	public abstract void addSearchDirectory(String value);

	/**
	 * Add EntryMask Element
	 * 
	 * @param value
	 */
	public abstract void addMask(EntryMask value);

	/**
	 * Add EntryMask String
	 * 
	 * @param name
	 */
	public abstract void addMaskString(String name);

	/**
	 * Add EntryMask Element in directory
	 * 
	 * @param name
	 */
	public abstract void addMaskDirectory(String name);

	/**
	 * Add EntryElement for process
	 * 
	 * @param value
	 */
	public abstract void addItem(EntryItem value);

	/**
	 * Add Blank EntryElement for process
	 */
	public abstract void addItemBlank();

	/**
	 * Add EntryConvert for Phase execution
	 * 
	 * @param value
	 */
	public abstract void addConvert(EntryConvert value);

	/**
	 * Add a ResultParset for phase execution
	 * 
	 * @param name
	 * @param value
	 */
	public abstract void putParser(String name, ResultParser value);

	/**
	 * Return ResultParser by name
	 * 
	 * @param name
	 * @return
	 */
	public abstract ResultParser getParser(String name);

	/**
	 * Add Object in Context execution
	 * 
	 * @param name
	 * @param value
	 */
	public abstract void putContext(String name, Object value);

	/**
	 * Execute all Phase for generate
	 * 
	 * FoeEach (EntryItem item, EntryMask mask) Then
	 * 
	 * mask = EntryRead(mask)
	 * 
	 * item = EntryConvert (item)
	 * 
	 * result = Apply (item, mask, context)
	 * 
	 * result = ResultRead(result)
	 * 
	 * result = ResultParser(result)
	 * 
	 * ResultWrite(result)
	 * 
	 * @return Result Object for the Process Finish
	 */
	public abstract Result generate();

	/**
	 * Execution Process on Demand
	 * 
	 * @param item
	 * @param parent
	 * @param mask
	 * @param result
	 */
	public abstract void processMask(EntryItem item, EntryItem parent, EntryMask mask, ResultGroup result);

	/**
	 * Apply Convert
	 * 
	 * @param item
	 * @return
	 */
	public abstract Object applyConvert(EntryItem item);
}
