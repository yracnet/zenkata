/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.zenkata;

/**
 *
 * @author yracnet
 */
public interface Factory {

    public boolean test(Object o);

    public Object apply(Object o);
}
