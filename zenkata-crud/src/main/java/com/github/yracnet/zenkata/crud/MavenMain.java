/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.zenkata.crud;

import com.github.yracnet.zenkata.ZenkataBuild;

/**
 *
 * @author yracnet
 */
public class MavenMain {
    public static void main(String[] args) {
        ZenkataBuild build = new ZenkataBuild();
        build.addDirectory("/work/dev/bcb-01/R00/template/zenkata-crud/src/main/resources");
        build.addItemBlank();
        build.addMaskDirectory("config");
        build.setOutput("/output");
        build.putContext("group", "dev.yracnet.exmaple");
        build.putContext("name", "demo");
        build.putContext("name", "react");
        build.putContext("managerList", new String []{"manager"});
        build.putContext("providerList", new String []{"provider"});
        build.generate();
    }
}
