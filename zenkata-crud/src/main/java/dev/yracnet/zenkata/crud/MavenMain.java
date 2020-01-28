/**
 * Copyright © 2020 ${owner} (${email})
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.zenkata.crud;

import dev.yracnet.zenkata.ZenkataBuild;
import java.io.File;

/**
 *
 * @author Willyams Yujra
 */
public class MavenMain {
	public static void main(String[] args) {
		ZenkataBuild build = new ZenkataBuild();
		File dir = build.getCurrentDirectory();
		build.addItemBlank();
		build.addMaskDirectory("config");
		build.setOutputString("../temp", dir);
		System.out.println("-->" + build.getOutput());
		build.putContext("group", "dev.yracnet.exmaple");
		build.putContext("name", "demo");
		build.putContext("name", "react");
		build.putContext("managerList", new String[]{"manager"});
		build.putContext("providerList", new String[]{"provider"});
		build.generate();
	}
}
