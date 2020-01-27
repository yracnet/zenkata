/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.zenkata.impl;

import com.github.yracnet.zenkata.EntryMask;
import groovy.text.Template;
import java.io.File;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author yracnet
 */
@Getter
@Setter
@ToString
public class EntryMaskImpl implements EntryMask {
    
    private File file;
    private Template template;
    
    @Override
    public boolean isExist() {
        return file != null && template != null;
    }
    
}
