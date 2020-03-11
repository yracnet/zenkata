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
package dev.yracnet.zenkata.xml;

import dev.yracnet.zenkata.Result;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author Willyams Yujra
 */
@XmlRootElement(name = "result-file")
@XmlAccessorType(XmlAccessType.NONE)
@lombok.Getter
@lombok.Setter
@lombok.ToString(exclude = {"parent", "content", "comment"})
public class ResultFile implements Result {
	@XmlTransient
	private ResultGroup parent;
	@XmlAttribute(name = "skip")
	private boolean skip;
	@XmlAttribute(name = "parser")
	private String parser;
	@XmlAttribute(name = "module")
	private String module;
	@XmlAttribute(name = "layer")
	private String layer;
	@XmlAttribute(name = "dir")
	private String dir;
	@XmlAttribute(name = "pkg")
	private String pkg;
	@XmlAttribute(name = "name")
	private String name;
	@XmlAttribute(name = "type")
	private String type;
	@XmlAttribute(name = "append")
	private boolean append;
	@XmlAttribute(name = "comment")
	private String comment;
	@XmlAttribute(name = "include")
	private String include;
	@XmlValue
	private String content;
	public String getResultPath() {
		StringBuilder path = new StringBuilder("/");
		if (parent != null && parent.getDir() != null) {
			path.append(parent.getDir()).append("/");
		}
		if (dir != null) {
			path.append(dir);
		}
		if (layer != null) {
			path.append("/").append(layer).append("/");
		}
		if (pkg != null) {
			path.append("/").append(pkg.replace(".", "/")).append("/");
		}
		path.append("/").append(name).append(".").append(type);
		return path.toString().replace("//", "/").replace("//", "/");
	}

	public void setParent(ResultGroup parent) {
		this.parent = parent;
		if (parent != null) {
			parser = inherentValue(parser, parent.getParser(), "");
			module = inherentValue(module, parent.getModule(), "");
			layer = inherentValue(layer, parent.getLayer(), "");
		}
	}

	private String inherentValue(String value, String parentValue, String defaultValue) {
		return value != null && !value.isBlank() ? value : parentValue != null && !parentValue.isBlank() ? parentValue : defaultValue;
	}

	public byte[] getContentBytes() {
		return content == null ? null : content.getBytes();
	}

	public byte[] getCommentBytes() {
		return comment == null ? null : comment.getBytes();
	}
}
