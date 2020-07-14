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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
	public static String readContent(File file) throws Exception {
		return new String(Files.readAllBytes(Paths.get(file.toURI())));
	}

	public static String readContent(String name) throws Exception {
		return readContent(name, ZenkataHelp.class.getClassLoader());
	}

	public static String readContent(String name, ClassLoader loader) throws Exception {
		URL url = loader.getResource(name);
		return new String(Files.readAllBytes(Paths.get(url.toURI())));
	}
	private static final Pattern SIMPLE_PATTERN = Pattern.compile("<result-file([^>]*)\\/>", Pattern.DOTALL);
	private static final Pattern GROOVY_PATTERN = Pattern.compile("(<%([^%>]*)%>)", Pattern.DOTALL);
	private static final Pattern CONTENT_PATTERN = Pattern.compile("<result-file([^>]*)>(.*?)<\\/result-file>", Pattern.DOTALL);
	private static final Pattern CDATA_PATTERN = Pattern.compile("^(\\s*)<!\\[CDATA\\[(.*?)]]>(\\s*)$", Pattern.DOTALL);
	public static String processMaskXml(File file) throws EntryException {
		try {
			String xml = new String(Files.readAllBytes(Paths.get(file.toURI())));
			// expands tag result-file
			Matcher simpleMatcher = SIMPLE_PATTERN.matcher(xml);
			xml = simpleMatcher.replaceAll(matchResult -> {
				String params = matchResult.group(1);
				return "<result-file" + params + "></result-file>";
			});
			TreeMap<String, String> reference = new TreeMap<>();
			int count[] = {0};
			// detect groovy-script content
			Matcher groovyMatcher = GROOVY_PATTERN.matcher(xml);
			xml = groovyMatcher.replaceAll(matchResult -> {
				String content = matchResult.group(1);
				String key = String.format("REF-%010d-GROOVY", ++count[0]);
				reference.put(key, content);
				return "<!--" + key + "-->";
			});
			// detect refult-file content
			Matcher contentMatcher = CONTENT_PATTERN.matcher(xml);
			xml = contentMatcher.replaceAll(matchResult -> {
				String content = matchResult.group(2);
				String key = String.format("REF-%010d-CONTENT", ++count[0]);
				reference.put(key, content);
				return "<result-file$1><!--" + key + "--></result-file>";
			});
			// transform and validate xml
			TransformerFactory factory = TransformerFactory.newInstance();
			Source processMask = new StreamSource(ZenkataHelp.class.getResourceAsStream("/process-mask.xslt"));
			StringWriter out = new StringWriter();
			Transformer transformer = factory.newTransformer(processMask);
			Source text = new StreamSource(new StringReader(xml));
			transformer.transform(text, new StreamResult(out));
			xml = out.toString();
			// restore reference content
			for (String key : reference.descendingKeySet()) {
				String content = reference.get(key);
				if (key.endsWith("-CONTENT")) {
					boolean inCData = CDATA_PATTERN.matcher(content).find();
					if (!inCData) {
						content = "<![CDATA[" + content + "]]>";
					}
				}
				key = "<!--" + key + "-->";
				xml = xml.replace(key, content);
			}
			// replace custom CDATA
			xml = xml.replace("--BEGIN-CDATA--", "<![CDATA[");
			xml = xml.replace("--END-CDATA--", "]]>");
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
