/*
 * The MIT License
 *
 * Copyright 2024 samueladebowale.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.cometbid.component.ut.jpa;

import java.io.Serializable;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *
 * @author samueladebowale
 * @param <T>
 */
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AbstractEntityId<T extends Serializable> implements Serializable, EntityId<T> {

    /**
     *
     */
    private static final long serialVersionUID = 5962381739484024405L;

    private T id;

    @ArtifactForFramework
    protected AbstractEntityId() {
    }

    protected AbstractEntityId(T id) {
        this.id = Objects.requireNonNull(id, "id must not be null");
    }

    @Override
    public T getId() {
        return id;
    }

    @Override
    public String asString() {
        return id.toString();
    }

}
