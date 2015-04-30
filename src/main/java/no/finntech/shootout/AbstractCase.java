/*
 *    Copyright 2015 FINN.no AS
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package no.finntech.shootout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public abstract class AbstractCase implements Case {
    private ByteArrayOutputStream out;
    private ByteArrayInputStream in;

    @Override
    public final void init() {
        out = new ByteArrayOutputStream();
    }

    @Override
    public abstract String getName();

    @Override
    public abstract void write();

    @Override
    public final int getSize() {
        in = new ByteArrayInputStream(out.toByteArray());
        return out.size();
    }

    @Override
    public abstract void read();

    protected ByteArrayOutputStream getOut() {
        return out;
    }

    protected ByteArrayInputStream getIn() {
        return in;
    }
}
