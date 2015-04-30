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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import no.finntech.shootout.AbstractCase;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.Encoder;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AvroBase extends AbstractCase {
    private static final Logger LOG = LogManager.getLogger(BinaryAvro.class);
    private static final AvroPost POST = AvroPost.newBuilder()
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
    private static final CompressorStreamFactory FACTORY = new CompressorStreamFactory();

    private Optional<String> compressor;

    public AvroBase() {
        compressor = Optional.empty();
    }

    public AvroBase(String compressor) {
        this.compressor = Optional.of(compressor);
    }

    @Override
    public void write() {
        DatumWriter<AvroPost> datumWriter = new SpecificDatumWriter<>(AvroPost.class);
        try {
            OutputStream actualOut = getOutputStream();
            Encoder encoder = getEncoder(actualOut);
            datumWriter.write(POST, encoder);
            encoder.flush();
            actualOut.close();
        } catch (IOException e) {
            LOG.error(e);
        }
    }

    private OutputStream getOutputStream() {
        return compressor.map(this::wrapOutput).orElseGet(this::getOut);
    }

    private OutputStream wrapOutput(String name) {
        try {
            return FACTORY.createCompressorOutputStream(name, getOut());
        } catch (CompressorException e) {
            LOG.error(e);
        }
        return getOut();
    }

    protected abstract Encoder getEncoder(OutputStream out) throws IOException;

    @Override
    public void read() {
        DatumReader<AvroPost> datumReader = new SpecificDatumReader<>(AvroPost.class);
        try {
            InputStream actualIn = getInputStream();
            Decoder decoder = getDecoder(actualIn);
            datumReader.read(null, decoder);
            actualIn.close();
        } catch (IOException e) {
            LOG.error(e);
        }
    }

    private InputStream getInputStream() {
        return compressor.map(this::wrapInput).orElseGet(this::getIn);
    }

    private InputStream wrapInput(String name) {
        try {
            return FACTORY.createCompressorInputStream(name, getIn());
        } catch (CompressorException e) {
            LOG.error(e);
        }
        return getIn();
    }

    protected abstract Decoder getDecoder(InputStream in) throws IOException;

    protected Optional<String> getCompressor() {
        return compressor;
    }
}
