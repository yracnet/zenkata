/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.zenkata;

import dev.yracnet.zenkata.impl.EntryItemImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 *
 * @author Willyams Yujra
 */
public class Test01 extends TestBase {
	@Test
	public void run() throws Exception {
		Map<String, Object> values = new HashMap<>();
		values.put("text", "Mi mensaje....");
		EntryItem item = new EntryItemImpl(values);
		ZenkataBuild build = ZenkataBuild.getCreateInstance();
		build.setOutput("output/Test01");
		build.addMaskString("test/Test01/01.xml");
		build.addItem(item);
		Result result = build.generate();
		String xmlResult = marshallerResult(result);
		System.out.println("-->" + xmlResult);
		// assertXmlFile(xmlResult, "");
	}
}
