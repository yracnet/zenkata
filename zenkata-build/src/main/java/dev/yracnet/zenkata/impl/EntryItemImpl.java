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
package dev.yracnet.zenkata.impl;

import java.util.List;
import dev.yracnet.zenkata.EntryItem;
import java.util.ArrayList;

/**
 *
 * @author Willyams Yujra
 */
public class EntryItemImpl implements EntryItem {
    private Object value;
    private final List<Object> children = new ArrayList<>();

    public EntryItemImpl(Object value) {
        this.value = value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void addChildren(Object value) {
        children.add(value);
    }

    @Override
    public List<Object> getChildren() {
        return children;
    }
}
