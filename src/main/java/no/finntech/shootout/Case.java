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

package no.finntech.shootout;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.infra.BenchmarkParams;

/**
 * Harness to benchmark serialization/deserialization using a suitable mechanism.
 *
 * A post-object with a common structure and data should be created. Make sure it
 * carries the same information as the post-objects used in other Cases, so the
 * benchmarks are comparable. Use the constants defined here to set fieldvalues.
 *
 * @param <T> The type of the post-object
 */
public abstract class Case<T> {
    public static final String PUBLISHED = "2015-02-10T15:04:55Z";
    public static final String PERSON_ID = "urn:example:person:morten";
    public static final String PERSON_NAME = "Morten Lied Johansen";
    public static final String ARTICLE_ID = "urn:example:blog:abc123/xyz";
    public static final String ARTICLE_NAME = "Why I'm testing serialization performance";

    private static final Map<String, Integer> SIZES = new ConcurrentHashMap<>();

    private static void saveSize(String benchmark, int size) {
        SIZES.put(benchmark, size);
    }

    public static Map<String, Integer> getSizes() {
        return SIZES;
    }

    @Benchmark
    @Fork(0)
    public void sizer(BenchmarkParams params) {
        saveSize(params.getBenchmark(), getSize());
    }

    /**
     * Return the size of the content, after serialization.
     *
     * This will typically just be the length of the bytebuffer in the
     * ByteArrayOutputStream returned from write, but you might want to
     * create it in a separate setup step.
     *
     * @return size of serialization
     */
    public abstract int getSize();

    /**
     * Write the post-object to a ByteArrayOutputStream using the
     * serialization mechanism to test.
     *
     * Must be annotated with @Benchmark
     *
     * @return A completed ByteArrayOutputStream
     * @throws Exception
     */
    public abstract ByteArrayOutputStream write() throws Exception;

    /**
     * Load a pre-created byte[], using the appropriate deserialization
     * mechanism. Should return an object similar to the post-object
     * serialized in write.
     *
     * Must be annotated with @Benchmark
     *
     * @return post-object of suitable type
     * @throws Exception
     */
    public abstract T read() throws Exception;
}
