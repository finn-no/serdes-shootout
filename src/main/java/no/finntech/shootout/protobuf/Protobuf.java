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

package no.finntech.shootout.protobuf;

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
import no.finntech.shootout.protobuf.CaseProtos.*;

import org.openjdk.jmh.annotations.Benchmark;

public class Protobuf extends Case<ProtobufView> {

    @Override
    protected ProtobufView buildObject() {
        return ProtobufView.newBuilder()
                .setPublished(Constants.PUBLISHED)
                .setActor(Person.newBuilder()
                        .setId(Viewer.ID)
                        .setUniqueVisitorId(Viewer.UNIQUE_ID)
                        .setSessionId(Viewer.SESSION_ID)
                        .setUserAgent(Viewer.USER_AGENT)
                        .setClientDevice(Viewer.CLIENT_DEVICE)
                        .setRemoteAddr(Viewer.REMOTE_ADDR))
                .setObject(Offer.newBuilder()
                        .setId(Ad.ID)
                        .setName(Ad.NAME)
                        .setCategory(Ad.CATEGORY)
                        .setSeller(Person.newBuilder()
                                .setId(Seller.ID))
                        .setAvailableAt(Place.newBuilder()
                                .setId(AvailableAt.ID))
                        .setPrice(Ad.PRICE))
                .setGenerator(Application.newBuilder()
                        .setId(Generator.ID))
                .setAttributedTo(Link.newBuilder()
                        .setHref(AttributedTo.HREF)
                        .setRel(AttributedTo.REL))
                .build();
    }

    @Override
    @Benchmark
    public ByteArrayOutputStream write() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        getObject().writeTo(baos);
        return baos;
    }

    @Override
    @Benchmark
    public ProtobufView read() throws Exception {
        return ProtobufView.parseFrom(getBytes());
    }
}
