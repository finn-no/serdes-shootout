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

package no.finntech.shootout.streams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import no.finntech.shootout.Case;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.streams.data.util.JsonUtil;
import org.apache.streams.pojo.json.Activity;
import org.openjdk.jmh.annotations.Benchmark;

public abstract class StreamsBase extends Case<Activity> {
    @Override
    protected Activity buildPost() {
        return new Activity();
/*
                .withPublished(RFC3339Utils.parseUTC(PUBLISHED))
                .withActor((Actor)new Actor()
                    .withId(PERSON_ID)
                    .withDisplayName(PERSON_NAME))
                .withObject(new Article()
                        .withId(ARTICLE_ID)
                        .withDisplayName(ARTICLE_NAME));
*/
    }

    @Override
    @Benchmark
    public ByteArrayOutputStream write() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writeTo(baos);
        return baos;
    }

    protected abstract void writeTo(OutputStream out) throws IOException, CompressorException;

    @Override
    @Benchmark
    public Activity read() throws Exception {
        String json = getJson();
        return JsonUtil.jsonToObject(json, Activity.class);
    }

    protected abstract String getJson() throws CompressorException, IOException;
}
