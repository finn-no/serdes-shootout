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

public final class Tracker {
    private static final String HEADER = String.format("%-30s|%10s|%10s|%10s", "Name", "Writes", "Size", "Reads");
    private static final String FORMAT = "%-30s|%8.2fμs|%9.0fB|%8.2fμs";
    private final String name;
    private final Accumulator write;
    private final Accumulator size;
    private final Accumulator read;

    public Tracker(String name) {
        this.name = name;
        write = new Accumulator();
        size = new Accumulator();
        read = new Accumulator();
    }

    public void addWrite(long value) {
        write.add(value);
    }

    public void addSize(long value) {
        size.add(value);
    }

    public void addRead(long value) {
        read.add(value);
    }

    public static void printHeader() {
        System.out.println(HEADER);
    }

    public void printReport() {
        System.out.println(String.format(FORMAT, name, write.getAverage(), size.getAverage(), read.getAverage()));
    }

    private static final class Accumulator {
        private long count;
        private double total;

        public void add(long value) {
            count++;
            total += value;
        }

        public double getAverage() {
            return total / count;
        }
    }
}
