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

import no.finntech.shootout.Case;

import org.apache.streams.data.util.JsonUtil;
import org.apache.streams.data.util.RFC3339Utils;
import org.apache.streams.pojo.json.Activity;
import org.apache.streams.pojo.json.Actor;
import org.apache.streams.pojo.json.objectTypes.Article;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class Streams extends Case<Activity> {
    private Activity post;
    private byte[] bytes;

    @Setup
    public void prepare() {
        post = new Activity()
                .withPublished(RFC3339Utils.parseUTC(PUBLISHED))
                .withActor((Actor)new Actor()
                    .withId(PERSON_ID)
                    .withDisplayName(PERSON_NAME))
                .withObject(new Article()
                        .withId(ARTICLE_ID)
                        .withDisplayName(ARTICLE_NAME));
        String json = JsonUtil.objectToJson(post);
        bytes = json.getBytes();
    }

    @Override
    public int getSize() {
        return bytes.length;
    }

    @Override
    @Benchmark
    public ByteArrayOutputStream write() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String json = JsonUtil.objectToJson(post);
        baos.write(json.getBytes());
        return baos;
    }

    @Override
    @Benchmark
    public Activity read() throws Exception {
        String json = new String(bytes);
        return JsonUtil.jsonToObject(json, Activity.class);
    }
}
