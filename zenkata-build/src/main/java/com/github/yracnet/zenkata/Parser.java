/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.zenkata;

/**
 *
 * @author yracnet
 * @param <T>
 */
public interface Parser<T extends Result> {

    public static final Parser NONE = (it) -> it;

    public T parser(T item);
}
