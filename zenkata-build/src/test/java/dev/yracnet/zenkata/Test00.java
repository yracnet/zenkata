/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.zenkata;

import dev.yracnet.zenkata.xml.ResultFile;
import dev.yracnet.zenkata.xml.ResultGroup;
import javax.xml.bind.JAXBContext;

import javax.xml.bind.Marshaller;
import org.junit.Test;

/**
 *
 * @author Willyams Yujra
 */
public class Test00 {
	@Test
	public void run() throws Exception {
		ResultGroup group = new ResultGroup();
		group.setDir("aaaa");
		ResultFile file = new ResultFile();
		file.setName("a1");
		file.setContent("HOLA!");
		group.addResult(file);
		group.addResult(file);
		group.addResult(file);
		JAXBContext jaxbContext = JAXBContext.newInstance(new Class<?>[]{ResultGroup.class, ResultFile.class});
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(group, System.out);
	}
}
