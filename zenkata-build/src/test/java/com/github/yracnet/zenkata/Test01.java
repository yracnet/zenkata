/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.zenkata;

import com.github.yracnet.zenkata.impl.EntryItemImpl;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yracnet
 */
public class Test01 {

    public static void main(String[] args) throws Exception {

        Map<String, Object> values = new HashMap<>();
        values.put("a", 1);
        EntryItem item = new EntryItemImpl(values);

        ZenkataBuild build = new ZenkataBuild();
        //build.addDirectory("");
        build.setOutput("/output");
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
