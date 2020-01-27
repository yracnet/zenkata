/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.zenkata;

import java.util.List;

/**
 *
 * @author yracnet
 */
public interface EntryReader {

    void addDirectory(String name);
    
    EntryMask readMask(String name);

    List<EntryMask> readMaskRecursive(String name);
}
