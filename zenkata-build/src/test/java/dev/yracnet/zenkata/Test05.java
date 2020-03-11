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
public class Test05 {
	@Test
	public void run() throws Exception {
		Map<String, Object> values = new HashMap<>();
		values.put("a", 1);
		EntryItem item = new EntryItemImpl(values);
		ZenkataBuild build = ZenkataBuild.getCreateInstance();
		build.setOutput("output");
		build.addMaskString("test/05.xml");
		build.addItem(item);
		build.putContext("a", "");
		Result result = build.generate();
		System.out.println("-->" + result);
	}
}
