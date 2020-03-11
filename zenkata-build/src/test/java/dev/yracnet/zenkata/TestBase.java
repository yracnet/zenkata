/*
 * Copyright 2020 wyujra.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.yracnet.zenkata;

import dev.yracnet.zenkata.impl.ZenkataHelp;
import dev.yracnet.zenkata.xml.ResultFile;
import dev.yracnet.zenkata.xml.ResultGroup;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.junit.Assert;

/**
 *
 * @author wyujra
 */
public class TestBase {
	public void assertXml(String xmlResult, String xmlAssert) {
		xmlResult = xmlResult.replaceAll("([\t|\n|\r|\\s]*)", "");
		xmlAssert = xmlAssert.replaceAll("([\t|\n|\r|\\s]*)", "");
		Assert.assertEquals(xmlResult, xmlAssert);
	}

	public void assertXmlFile(String xmlResult, String xmlFile) throws Exception {
		String xmlAssert = ZenkataHelp.readContent(xmlFile);
		xmlResult = xmlResult.replaceAll("([\t|\n|\r|\\s]*)", "");
		xmlAssert = xmlAssert.replaceAll("([\t|\n|\r|\\s]*)", "");
		Assert.assertEquals(xmlResult, xmlAssert);
	}

	public String marshallerResult(Result result) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(new Class<?>[]{ResultGroup.class, ResultFile.class});
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter out = new StringWriter();
		marshaller.marshal(result, out);
		return out.toString();
	}
}
