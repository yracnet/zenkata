/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.zenkata.xml;

import com.github.yracnet.zenkata.Result;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author yracnet
 */
@XmlRootElement(name = "result-file")
@XmlAccessorType(XmlAccessType.NONE)
@Getter
@Setter
@ToString
public class ResultFile implements Result {

    @XmlAttribute(name = "skip")
    private boolean skip;
    @XmlAttribute(name = "parser")
    private String parser;
    @XmlAttribute(name = "module")
    private String module;
    @XmlAttribute(name = "layer")
    private String layer;
    @XmlAttribute(name = "dir")
    private String dir;
    @XmlAttribute(name = "pkg")
    private String pkg;
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "type")
    private String type;
    @XmlAttribute(name = "append")
    private boolean append;
    @XmlAttribute(name = "comment")
    private String comment;
    @XmlValue
    private String content;

    public String getFile() {
        StringBuilder file = new StringBuilder("/");
        if (dir != null) {
            file.append(dir);
        }
        if (layer != null) {
            file.append("/[").append(layer).append("]/");
        }
        if (pkg != null) {
            file.append("/[").append(pkg.replace(".", "/")).append("]/");
        }
        file.append("/").append(name).append(".").append(type);
        return file.toString().replace("//", "/");
    }

}
