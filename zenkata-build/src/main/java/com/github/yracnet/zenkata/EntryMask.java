/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.zenkata;

import groovy.text.Template;
import java.io.File;

/**
 *
 * @author yracnet
 */
public interface EntryMask extends Entry {

    File getFile();
    
    boolean isExist();

    Template getTemplate();

}
