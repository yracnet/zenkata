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

import dev.yracnet.zenkata.ResultParser;
import dev.yracnet.zenkata.xml.ResultFile;

/**
 *
 * @author Willyams Yujra
 */
public class ResultParserImpl implements ResultParser {
	@Override
	public ResultFile parser(ResultFile item) {
		String type = item.getType();
		if ("txt".equals(type)) {
			String content = item.getContent();
			content = content == null ? "<!-- ERROR -->" : content.trim();
			item.setContent(content);
		}
		if ("java".equals(type)) {
			String content = item.getContent();
			content = content == null ? "<!-- ERROR -->" : content.trim();
			if (!item.isAppend()) {
				content = "package " + item.getPkg() + ";\n\n" + content;
			}
			item.setContent(content);
		}
		if ("xml".equals(type) || "xhtml".equals(type) || "html".equals(type)) {
			String content = item.getContent();
			content = content == null ? "<!-- ERROR -->" : content.trim();
			item.setContent(content);
		}
		return item;
	}
}
