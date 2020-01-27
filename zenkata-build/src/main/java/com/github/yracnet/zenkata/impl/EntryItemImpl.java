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
 * @author yracnet
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
