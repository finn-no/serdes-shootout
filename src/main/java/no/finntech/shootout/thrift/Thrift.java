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

package no.finntech.shootout.thrift;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import no.finntech.shootout.Case;

import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TProtocolFactory;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public abstract class Thrift extends Case<ThriftPost> {
    private ThriftPost post;
    private byte[] bytes;

    @Setup
    public void prepare() throws TException {
        post = new ThriftPost()
            .setPublished(PUBLISHED)
            .setActor(new Person()
                .setId(PERSON_ID)
                .setDisplayName(PERSON_NAME))
            .setObject(new Article()
                .setId(ARTICLE_ID)
                .setDisplayName(ARTICLE_NAME));
        bytes = new TSerializer(getProtocolFactory()).serialize(post);
    }

    @Override
    public int getSize() {
        return bytes.length;
    }

    @Override
    @Benchmark
    public ByteArrayOutputStream write() throws TException, IOException, InterruptedException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(new TSerializer(getProtocolFactory()).serialize(post));
        return outputStream;
    }

    @Override
    @Benchmark
    public ThriftPost read() throws TException, InterruptedException {
        ThriftPost base = new ThriftPost();
        new TDeserializer(getProtocolFactory()).deserialize(base, bytes);
        return base;
    }

    protected abstract TProtocolFactory getProtocolFactory();
}
