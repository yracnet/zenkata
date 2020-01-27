/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.zenkata;

import java.io.File;

/**
 *
 * @author yracnet
 */
public interface ResultWriter {

    public void write(Result result, File dir);
    
}
