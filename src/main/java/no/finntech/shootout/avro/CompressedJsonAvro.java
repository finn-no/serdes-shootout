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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.util.Utf8;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

public abstract class CompressedJsonAvro extends AvroBase {
    private static final CompressorStreamFactory FACTORY = new CompressorStreamFactory();

    protected abstract String getCompressor();

    @Override
    protected Encoder getEncoder(OutputStream out) throws IOException, CompressorException {
        return new EncoderWrapper(out);
    }

    @Override
    protected Decoder getDecoder(byte[] bytes) throws IOException, CompressorException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        CompressorInputStream inputStream = FACTORY.createCompressorInputStream(getCompressor(), in);
        return DecoderFactory.get().jsonDecoder(AvroPost.getClassSchema(), inputStream);
    }

    public class EncoderWrapper extends Encoder {
        private CompressorOutputStream outputStream;
        private Encoder encoder;

        public EncoderWrapper(OutputStream out) throws CompressorException, IOException {
            outputStream = FACTORY.createCompressorOutputStream(getCompressor(), out);
            encoder = EncoderFactory.get().jsonEncoder(AvroPost.getClassSchema(), outputStream);
        }

        @Override
        public void flush() throws IOException {
            encoder.flush();
            outputStream.flush();
            outputStream.close();
        }

        @Override
        public void writeNull() throws IOException {
            encoder.writeNull();
        }

        @Override
        public void writeBoolean(boolean b) throws IOException {
            encoder.writeBoolean(b);
        }

        @Override
        public void writeInt(int n) throws IOException {
            encoder.writeInt(n);
        }

        @Override
        public void writeLong(long n) throws IOException {
            encoder.writeLong(n);
        }

        @Override
        public void writeFloat(float f) throws IOException {
            encoder.writeFloat(f);
        }

        @Override
        public void writeDouble(double d) throws IOException {
            encoder.writeDouble(d);
        }

        @Override
        public void writeString(Utf8 utf8) throws IOException {
            encoder.writeString(utf8);
        }

        @Override
        public void writeBytes(ByteBuffer bytes) throws IOException {
            encoder.writeBytes(bytes);
        }

        @Override
        public void writeBytes(byte[] bytes, int start, int len) throws IOException {
            encoder.writeBytes(bytes, start, len);
        }

        @Override
        public void writeFixed(byte[] bytes, int start, int len) throws IOException {
            encoder.writeFixed(bytes, start, len);
        }

        @Override
        public void writeEnum(int e) throws IOException {
            encoder.writeEnum(e);
        }

        @Override
        public void writeArrayStart() throws IOException {
            encoder.writeArrayStart();
        }

        @Override
        public void setItemCount(long itemCount) throws IOException {
            encoder.setItemCount(itemCount);
        }

        @Override
        public void startItem() throws IOException {
            encoder.startItem();
        }

        @Override
        public void writeArrayEnd() throws IOException {
            encoder.writeArrayEnd();
        }

        @Override
        public void writeMapStart() throws IOException {
            encoder.writeMapStart();
        }

        @Override
        public void writeMapEnd() throws IOException {
            encoder.writeMapEnd();
        }

        @Override
        public void writeIndex(int unionIndex) throws IOException {
            encoder.writeIndex(unionIndex);
        }
    }
}
