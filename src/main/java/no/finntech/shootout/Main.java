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

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import no.finntech.shootout.avro.BinaryAvro;
import no.finntech.shootout.avro.JsonAvro;
import no.finntech.shootout.thrift.Thrift;

import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.apache.commons.compress.compressors.CompressorStreamFactory.DEFLATE;
import static org.apache.commons.compress.compressors.CompressorStreamFactory.GZIP;

public final class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);
    private List<Case> cases = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        Main app = new Main();
        app.run();
    }

    private void run() {
        addAvros();
        cases.add(new Thrift());
        Tracker.printHeader();
        for (Case c : cases) {
            Tracker tracker = new Tracker(c.getName());
            for (int i = 0; i < 10000; i++) {
                c.init();
                tracker.addWrite(time(c::write));
                tracker.addSize(c.getSize());
                tracker.addRead(time(c::read));
            }
            tracker.printReport();
        }
    }

    private void addAvros() {
        try {
            for (Class<? extends AbstractCase> clz : Arrays.asList(BinaryAvro.class, JsonAvro.class)) {
                cases.add(clz.newInstance());
                for (String compressor : Arrays.asList(DEFLATE, GZIP)) {
                    Constructor<?>[] constructors = clz.getConstructors();
                    for (Constructor<?> constructor : constructors) {
                        if (constructor.getParameterCount() == 1) {
                            cases.add((Case) constructor.newInstance(compressor));
                        }
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            LOG.error(e);
        }
    }

    private long time(TimeTarget t) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        t.run();
        stopwatch.stop();
        return stopwatch.elapsed(TimeUnit.MICROSECONDS);
    }

    private interface TimeTarget {
        void run();
    }
}
