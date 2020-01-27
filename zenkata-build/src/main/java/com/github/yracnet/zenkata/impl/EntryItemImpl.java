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
package com.github.yracnet.zenkata.impl;

import groovy.json.JsonSlurper;
import java.util.List;
import java.util.HashMap;
import com.github.yracnet.zenkata.EntryItem;

/**
 *
 * @author Willyams Yujra
 */
public class EntryItemImpl implements EntryItem {
    
    public static final EntryItem BLANK = new EntryItemImpl(new HashMap<>());

    private final JsonSlurper jsonSlurper = new JsonSlurper();
    private Object value;

    public EntryItemImpl(Object value) {
        this.value = value;
    }

    @Override
    public Object getObject() {
        String jsonString = createJson(value);
        return jsonSlurper.parseText(jsonString);
    }

    @Override
    public List<EntryItem> getChildren() {
        return List.of();
    }

    public String createJson(Object value) {
        return "{}";
    }
}
