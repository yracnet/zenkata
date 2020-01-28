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

import java.io.File;

/**
 *
 * @author Willyams Yujra
 */
public class ZenkataHelp {
	public static String sanetizeCDATA(String xml) {
		// REMOVE CDATA
		xml = xml.replaceAll("<result-file([^>]*)>(\\s)*<!\\[CDATA\\[(\\s)*", "<result-file$1>");
		// xml = xml.replaceAll("<result-file>(\\s)*<!\\[CDATA\\[(\\s)*", "<result-file>");
		xml = xml.replaceAll("(\\s)*\\]\\]>(\\s)*</result-file>", "</result-file>");
		// ATTACH CDATA
		xml = xml.replaceAll("<result-file([^>]*)>", "<result-file$1><![CDATA[");
		// xml = xml.replace("<result-file>", "<result-file><![CDATA[");
		xml = xml.replace("</result-file>", "]]></result-file>");
		return xml;
	}

	public static File sanetizePath(File path) {
		String value = path.getAbsolutePath();
		value = value.replace("/./", "/");
		while (value.contains("..")) {
			value = value.replaceAll("\\/([^\\/]*)\\/(\\.\\.)", "");
		}
		return new File(value);
	}
}
