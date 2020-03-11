/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.zenkata;

import org.junit.Test;

/**
 *
 * @author Willyams Yujra
 */
public class Test03 {
	@Test
	public void run() throws Exception {
		String xmls[] = new String[]{"<result-file name=\"name\"><![CDATA[<a>a</a> ]]></result-file>", "<result-file name='name'><![CDATA[<a>a</a> ]]></result-file>",
				"<result-file\nname='name'><![CDATA[<a>a</a> ]]></result-file>", "<result-file><![CDATA[<a>a</a> ]]></result-file>",
				// "<result-file> <![CDATA[<a>a</a> ]]></result-file>",
				// "<result-file>\n\n <![CDATA[<a>a</a> ]]></result-file>",
				// "<result-file>\n\n\t\t <![CDATA[<a>a</a> ]]></result-file>",
				// "<result-file> <![CDATA[ <a>a</a> ]]></result-file>",
				// "<result-file>\n\n <![CDATA[ \n\n<a>a</a> ]]></result-file>",
				// "<result-file>\n\n\t\t <![CDATA[\t\t \n\n<a>a</a> ]]></result-file>",
		};
		for (String xml : xmls) {
			// System.out.println("original-->" + xml);
			xml = xml.replaceAll("<result-file([^>]*)>(\\s)*<!\\[CDATA\\[(\\s)*", "<result-file$1>");
			// xml = xml.replaceAll("<result-file>(\\s)*<!\\[CDATA\\[(\\s)*", "<result-file>");
			xml = xml.replaceAll("(\\s)*\\]\\]>(\\s)*</result-file>", "</result-file>");
			System.out.println("-->" + xml);
		}
	}
}
