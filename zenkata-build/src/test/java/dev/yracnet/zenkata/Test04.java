/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.zenkata;

/**
 *
 @author Willyams Yujra
 */
public class Test04 {
	public static void main(String[] args) {
        String path[] = {
            "/store/work/dev/dev-01/zenkata/zenkata-crud/../temp/param-admin/param-admin-faces/pom.xml",
            "/store/work/dev/dev-01/zenkata/zenkata-crud/../../../../pom.xml",
            "/store/dev/../v1/../zenkata-crud/../../../../pom.xml",
            "/store/./dev/../v1/../zenkata-crud/../../../../pom.xml"
        };
        for (String p : path) {
            System.out.println("1--->" + p);
            p = p.replace("/./", "/");
            while(p.contains("..")){
                p = p.replaceAll("\\/([^\\/]*)\\/(\\.\\.)", "");
            }
            System.out.println("2--->" + p);
        }
    }
}
