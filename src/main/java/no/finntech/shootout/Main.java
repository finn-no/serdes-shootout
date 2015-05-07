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
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.results.Result;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;
import org.openjdk.jmh.util.ClassUtils;

import static java.util.stream.Collectors.toSet;

public final class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException, RunnerException {
        Runner runner = new Runner(new OptionsBuilder()
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MICROSECONDS)
                .warmupIterations(0)
                .measurementIterations(1)
                .verbosity(VerboseMode.SILENT)
                .build());
        LOG.info("Starting run...");
        Collection<RunResult> results = runner.run();
        Map<String, String> denseNames = getDenseNames(results);
        Map<String, Integer> sizes = Case.getSizes();
        SortedSet<String> output = new TreeSet<>();
        for (RunResult result : results) {
            BenchmarkParams params = result.getParams();
            String benchmark = params.getBenchmark();
            if (benchmark.contains(".sizer")) {
                output.add(buildBenchmarkHeader(denseNames.get(benchmark), sizes.get(benchmark)));
            } else {
                output.add(buildBenchmarkResult(result.getPrimaryResult(), denseNames.get(benchmark)));
            }
        }
        for (String line : output) {
            System.out.println(line);
        }
        LOG.info("Run completed");
    }

    private static String buildBenchmarkResult(Result primaryResult, String denseName) {
        return String.format("%-40s score: %10.2f %s",
                denseName,
                primaryResult.getScore(),
                primaryResult.getScoreUnit()
        );
    }

    private static String buildBenchmarkHeader(String denseName, Integer size) {
        return String.format("%-40s ====> %d bytes",
                mainName(denseName),
                size
        );
    }

    private static Map<String, String> getDenseNames(Collection<RunResult> results) {
        return ClassUtils.denseClassNames(results.stream()
                .map(RunResult::getParams)
                .map(p -> p.getBenchmark())
                .collect(toSet()));
    }

    private static String mainName(String denseName) {
        return denseName.replace(".sizer", "");
    }
}
