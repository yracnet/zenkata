/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.zenkata;

import dev.yracnet.zenkata.xml.ResultFile;
import dev.yracnet.zenkata.xml.ResultGroup;
import org.junit.Test;

/**
 *
 * @author Willyams Yujra
 */
public class Test00 extends TestBase {
	@Test
	public void simple() throws Exception {
		ResultGroup root = new ResultGroup();
		root.setDir("aaaa");
		ResultFile file = new ResultFile();
		file.setName("a1");
		file.setContent("HOLA!");
		root.addResult(file);
		root.addResult(file);
		root.addResult(file);
		String xmlResult = marshallerResult(root);
                System.out.println("-->" +xmlResult);
		assertXmlFile(xmlResult, "assert/Test00/simple.xml");
	}

	@Test
	public void grupo() throws Exception {
		ResultGroup g1 = new ResultGroup();
		g1.setDir("aaaa");
		ResultFile file = new ResultFile();
		file.setName("a1");
		file.setContent("HOLA!");
		g1.addResult(file);
		g1.addResult(file);
		ResultGroup root = new ResultGroup();
		root.setDir("bbb");
		ResultGroup g2 = new ResultGroup();
		g2.setDir("cccc");
		g2.addResult(file);
		root.addResult(g1);
		root.addResult(g2);
		String xmlResult = marshallerResult(root);
		assertXmlFile(xmlResult, "assert/Test00/grupo.xml");
	}
}
