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
import java.io.OutputStream;

import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;

public class JsonAvro extends AvroBase {
    @Override
    protected Encoder getEncoder(OutputStream out) throws IOException {
        return EncoderFactory.get().jsonEncoder(AvroView.getClassSchema(), out);
    }

    @Override
    protected Decoder getDecoder(byte[] bytes) throws IOException {
        return DecoderFactory.get().jsonDecoder(AvroView.getClassSchema(), new String(bytes));
    }
}
