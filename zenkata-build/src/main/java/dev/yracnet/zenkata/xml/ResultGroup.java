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
package dev.yracnet.zenkata.xml;

import dev.yracnet.zenkata.Result;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Willyams Yujra
 */
@XmlRootElement(name = "group", namespace = Result.NAMESPACE)
@XmlAccessorType(XmlAccessType.NONE)
@Getter
@Setter
@ToString
public class ResultGroup implements Result {

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

    @XmlElements({
        @XmlElement(name = "group", type = ResultGroup.class, namespace = Result.NAMESPACE),
        //@XmlElement(name = "result-java", type = ResultFileJava.class, namespace = Result.NAMESPACE),
        //@XmlElement(name = "result-html", type = ResultFileHtml.class, namespace = Result.NAMESPACE),
        //@XmlElement(name = "result-xml", type = ResultFileXml.class, namespace = Result.NAMESPACE),
        //@XmlElement(name = "result-js", type = ResultFileJs.class, namespace = Result.NAMESPACE),
        @XmlElement(name = "file", type = ResultFile.class, namespace = Result.NAMESPACE)
    })
    private final List<Result> resutlList = new ArrayList<>();

    public void addResult(Result result) {
        if (result != null) {
            resutlList.add(result);
        }
    }
}
