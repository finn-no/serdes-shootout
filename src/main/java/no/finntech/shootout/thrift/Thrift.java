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
import no.finntech.shootout.Constants;
import no.finntech.shootout.Constants.Ad;
import no.finntech.shootout.Constants.AttributedTo;
import no.finntech.shootout.Constants.AvailableAt;
import no.finntech.shootout.Constants.Generator;
import no.finntech.shootout.Constants.Seller;
import no.finntech.shootout.Constants.Viewer;

import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TProtocolFactory;
import org.openjdk.jmh.annotations.Benchmark;

public abstract class Thrift extends Case<ThriftView> {

    @Override
    protected ThriftView buildPost() {
        return new ThriftView()
                .setPublished(Constants.PUBLISHED)
                .setActor(new Person()
                        .setId(Viewer.ID)
                        .setUniqueVisitorId(Viewer.UNIQUE_ID)
                        .setSessionId(Viewer.SESSION_ID)
                        .setUserAgent(Viewer.USER_AGENT)
                        .setClientDevice(Viewer.CLIENT_DEVICE)
                        .setRemoteAddr(Viewer.REMOTE_ADDR))
                .setObject(new Offer()
                        .setId(Ad.ID)
                        .setName(Ad.NAME)
                        .setCategory(Ad.CATEGORY)
                        .setSeller(new Person()
                                .setId(Seller.ID))
                        .setAvailableAt(new Place()
                                .setId(AvailableAt.ID))
                        .setPrice(Ad.PRICE))
                .setGenerator(new Application()
                        .setId(Generator.ID))
                .setAttributedTo(new Link()
                        .setHref(AttributedTo.HREF)
                        .setRel(AttributedTo.REL));

    }

    @Override
    @Benchmark
    public ByteArrayOutputStream write() throws TException, IOException, InterruptedException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(new TSerializer(getProtocolFactory()).serialize(getPost()));
        return outputStream;
    }

    @Override
    @Benchmark
    public ThriftView read() throws TException, InterruptedException {
        ThriftView base = new ThriftView();
        new TDeserializer(getProtocolFactory()).deserialize(base, getBytes());
        return base;
    }

    protected abstract TProtocolFactory getProtocolFactory();
}
