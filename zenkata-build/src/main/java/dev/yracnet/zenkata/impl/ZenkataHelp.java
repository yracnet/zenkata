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
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Willyams Yujra
 */
public class ZenkataHelp {
	public static String processMaskXml(File file) throws EntryException {
		try {
			String xml = new String(Files.readAllBytes(Paths.get(file.toURI())));
			xml = xml.replace("<%", "<!--<%");
			xml = xml.replace("%>", "%>-->");
			TransformerFactory factory = TransformerFactory.newInstance();
			Source processMask = new StreamSource(ZenkataHelp.class.getResourceAsStream("/process-mask.xslt"));
			StringWriter out = new StringWriter();
			Transformer transformer = factory.newTransformer(processMask);
			Source text = new StreamSource(new StringReader(xml));
			transformer.transform(text, new StreamResult(out));
			xml = out.toString();
			xml = xml.replace("--BEGIN-CDATA--", "<![CDATA[");
			xml = xml.replace("--END-CDATA--", "]]>");
			xml = xml.replace("--BEGIN-GROOVY--", "<%");
			xml = xml.replace("--END-GROOVY--", "%>");
			xml = xml.replace("<!--<%", "<%");
			xml = xml.replace("%>-->", "%>");
			return xml;
		} catch (TransformerException | IOException e) {
			throw new EntryException("Error mask xml", e);
		}
	}

	// public static String processResultXml(String xml) throws ResultException {
	// try {
	// TransformerFactory factory = TransformerFactory.newInstance();
	// Source processMask = new StreamSource(ZenkataHelp.class.getResourceAsStream("/process-result.xslt"));
	// StringWriter out = new StringWriter();
	// Transformer transformer = factory.newTransformer(processMask);
	// Source text = new StreamSource(new StringReader(xml));
	// transformer.transform(text, new StreamResult(out));
	// xml = out.toString();
	// xml = xml.replace("--BEGIN-CDATA--", "<![CDATA[");
	// xml = xml.replace("--END-CDATA--", "]]>");
	// return xml;
	// } catch (TransformerException e) {
	// throw new ResultException("Error result xml", e);
	// }
	// }
	public static File sanetizePath(File path) {
		String value = path.getAbsolutePath();
		value = value.replace("/./", "/");
		while (value.contains("..")) {
			value = value.replaceAll("\\/([^\\/]*)\\/(\\.\\.)", "");
		}
		return new File(value);
	}
}
