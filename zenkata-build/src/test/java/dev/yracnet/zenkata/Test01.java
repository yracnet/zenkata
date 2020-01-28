/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.zenkata;

import dev.yracnet.zenkata.impl.EntryItemImpl;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Willyams Yujra
 */
public class Test01 {
	public static void main(String[] args) throws Exception {

        Map<String, Object> values = new HashMap<>();
        values.put("a", 1);
        EntryItem item = new EntryItemImpl(values);

        ZenkataBuild build = ZenkataBuild.getInstance();
        build.setOutput("output");
        build.addMaskString("test/01.xml");
        build.addMaskString("test/02.xml");
        //build.putLayer("view", "${it.group}");
        //build.addFactory(null);
        build.addItem(item);
        //build.putParser(null, null);
        build.putContext("a", "");
        Result result = build.generate();
        System.out.println("-->" + result);
    }
}
