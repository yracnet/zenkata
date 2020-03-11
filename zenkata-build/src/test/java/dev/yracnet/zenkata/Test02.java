/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.zenkata;

import dev.yracnet.zenkata.impl.ResultReaderImpl;
import dev.yracnet.zenkata.xml.ResultFile;
import dev.yracnet.zenkata.xml.ResultGroup;
import org.junit.Test;

/**
 *
 * @author Willyams Yujra
 */
public class Test02 {
	// @Test
	public void run() throws Exception {
		ResultReader resultReader = new ResultReaderImpl();
		String xmls[] = new String[]{
				// "<result-file name='a'><![CDATA[<a>a</a> ]]></result-file>",
				// "<result-file><![CDATA[<f:a xmlns:f='http://xmlns.jcp.org/jsf/core'>a</f:a>]]> </result-file>",
				// "<result-file>\t\tDemo:::::::::</result-file>",
				// "<result-group><result-file><f:a xmlns:f='http://xmlns.jcp.org/jsf/core'>a</f:a></result-file> </result-group>",
				// "<result-group><result-file> <![CDATA[ <a>a</a> ]]></result-file></result-group>",
				// "<result-group><result-file>\n\n <![CDATA[ \n\n<a>a</a> ]]></result-file></result-group>",
				// "<result-group><result-file>\n\n\t\t <![CDATA[\t\t \n\n<a>a</a> ]]></result-file></result-group>",
				// "<result-group><result-file>\tResult string.....</result-file></result-group>",
				// "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<result-group>\n" + "    <result-file name=\"02-A\" type=\"xml\">\n" + "        Archivo 02-A\n" + "    </result-file>\n" + "    <result-file name=\"02-B\" type=\"xml\">\n"
						+ "        <text>Archivo 02-B</text>\n" + "    </result-file>\n" + "</result-group>"};
		for (String xml : xmls) {
			Object result = resultReader.read(xml);
			System.out.println("1-->" + result);
			if (result instanceof ResultGroup) {
				ResultGroup resultGroup = (ResultGroup) result;
				System.out.println("2-->" + resultGroup.getResutlList());
				result = resultGroup.getResutlList().get(0);
			}
			if (result instanceof ResultFile) {
				ResultFile resultFile = (ResultFile) result;
				System.out.println("3-->" + resultFile.getContent());
			}
		}
	}
}
