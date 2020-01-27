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
package com.github.yracnet.zenkata.xml;

import com.github.yracnet.zenkata.Result;
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
 * @param <T>
 */
@XmlRootElement(name = "result-group")
@XmlAccessorType(XmlAccessType.NONE)
@Getter
@Setter
@ToString
public class ResultGroup implements Result {

    @XmlAttribute(name = "skip")
    private boolean skip;

    @XmlElements({
        @XmlElement(name = "result-group", type = ResultGroup.class),
        //@XmlElement(name = "result-java", type = ResultFileJava.class),
        //@XmlElement(name = "result-html", type = ResultFileHtml.class),
        //@XmlElement(name = "result-xml", type = ResultFileXml.class),
        //@XmlElement(name = "result-js", type = ResultFileJs.class),
        @XmlElement(name = "result-file", type = ResultFile.class)
    })
    private final List<Result> resutlList = new ArrayList<>();

    public void addResult(Result result) {
        if (result != null) {
            resutlList.add(result);
        }
    }
}
