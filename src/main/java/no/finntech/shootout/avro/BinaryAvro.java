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

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;

public final class BinaryAvro extends AvroBase {
    public BinaryAvro() {
    }

    public BinaryAvro(String compressor) {
        super(compressor);
    }

    @Override
    public String getName() {
        return "Avro" + getCompressor().map(c -> "+" + c).orElse("");
    }

    @Override
    protected Encoder getEncoder(OutputStream out) {
        return EncoderFactory.get().binaryEncoder(out, null);
    }

    @Override
    protected Decoder getDecoder(InputStream in) {
        return DecoderFactory.get().binaryDecoder(in, null);
    }
}
