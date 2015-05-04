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

package no.finntech.shootout.avro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import no.finntech.shootout.Case;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.Encoder;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.commons.compress.compressors.CompressorException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public abstract class AvroBase extends Case<AvroPost> {
    private AvroPost post;
    private byte[] bytes;

    @Setup
    public void prepare() throws IOException, CompressorException {
        post = AvroPost.newBuilder()
            .setPublished(PUBLISHED)
            .setActor(Person.newBuilder()
                    .setId(PERSON_ID)
                    .setDisplayName(PERSON_NAME)
                    .build())
            .setObject(Article.newBuilder()
                    .setId(ARTICLE_ID)
                    .setDisplayName(ARTICLE_NAME)
                    .build())
            .build();
        ByteArrayOutputStream out = encode();
        bytes = out.toByteArray();
    }

    private ByteArrayOutputStream encode() throws IOException, CompressorException {
        DatumWriter<AvroPost> datumWriter = new SpecificDatumWriter<>(AvroPost.class);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Encoder encoder = getEncoder(out);
        datumWriter.write(post, encoder);
        encoder.flush();
        out.flush();
        out.close();
        return out;
    }

    @Override
    @Benchmark
    public ByteArrayOutputStream write() throws IOException, CompressorException {
        return encode();
    }

    @Override
    @Benchmark
    public AvroPost read() throws IOException, CompressorException {
        DatumReader<AvroPost> datumReader = new SpecificDatumReader<>(AvroPost.class);
        Decoder decoder = getDecoder(bytes);
        return datumReader.read(null, decoder);
    }

    @Override
    public int getSize() {
        return bytes.length;
    }

    protected abstract Encoder getEncoder(OutputStream out) throws IOException, CompressorException;
    protected abstract Decoder getDecoder(byte[] bytes) throws IOException, CompressorException;
}
