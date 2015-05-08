Serdes Shootout
===============

This project attempts to compare speed and size when using different forms of
serialization in Java. A similar object is serialized and deserialized using
a number of serialization frameworks/techniques, and the time spent and size
of serialized output is compared in a simple report when completed.


Setup for development
---------------------

Most things should already be present, except:

The thrift and protobuf compilers must be on your `PATH`.

* Thrift: Should be version `0.9.1`, and named `thrift-0.9.1`
    * Maven properties: `thrift.version` and `thrift.executable`
* Protobuf: Should be version `2.5.0`, and named `protoc`
    * Maven properties: `protobuf.version` and `protobuf.executable`


Adding a framework to test
--------------------------

Implement one or more `Case`s. Make sure to read the javadoc.

When you are satisfied with your implementation, send a pull-request to get
it added to the main.


Run the benchmarks
------------------

You can run the benchmarks in two ways:

* Run `mvn exec:exec`
* Build the project with `mvn package`, and then use the shaded uberjar:
    `java -jar target/serdes-shootout-1.0-SNAPSHOT.jar`


Some results
------------

The latest run of this benchmark gave the following results:

```
n.f.s.avro.BinaryAvro                    ====> 347 bytes
n.f.s.avro.BinaryAvro.read               score:      15,80 us/op
n.f.s.avro.BinaryAvro.write              score:      56,98 us/op
n.f.s.avro.DeflateJsonAvro               ====> 491 bytes
n.f.s.avro.DeflateJsonAvro.read          score:    1608,64 us/op
n.f.s.avro.DeflateJsonAvro.write         score:     219,25 us/op
n.f.s.avro.GzipJsonAvro                  ====> 503 bytes
n.f.s.avro.GzipJsonAvro.read             score:    3459,38 us/op
n.f.s.avro.GzipJsonAvro.write            score:     799,78 us/op
n.f.s.avro.JsonAvro                      ====> 853 bytes
n.f.s.avro.JsonAvro.read                 score:     414,50 us/op
n.f.s.avro.JsonAvro.write                score:     879,67 us/op
n.f.s.avro.XzJsonAvro                    ====> 576 bytes
n.f.s.avro.XzJsonAvro.read               score:  421557,67 us/op
n.f.s.avro.XzJsonAvro.write              score:  550520,83 us/op
n.f.s.protobuf.Protobuf                  ====> 359 bytes
n.f.s.protobuf.Protobuf.read             score:      35,47 us/op
n.f.s.protobuf.Protobuf.write            score:       7,06 us/op
n.f.s.streams.DeflateStreams             ====> 476 bytes
n.f.s.streams.DeflateStreams.read        score:    1416,09 us/op
n.f.s.streams.GzipStreams                ====> 488 bytes
n.f.s.streams.GzipStreams.read           score:    1885,56 us/op
n.f.s.streams.GzipStreams.write          score:      81,40 us/op
n.f.s.streams.Streams                    ====> 740 bytes
n.f.s.streams.Streams.read               score:     290,45 us/op
n.f.s.streams.Streams.write              score:     163,36 us/op
n.f.s.streams.XzStreams                  ====> 552 bytes
n.f.s.streams.XzStreams.read             score:   63448,29 us/op
n.f.s.streams.XzStreams.write            score:  461514,12 us/op
n.f.s.thrift.BinaryThrift                ====> 449 bytes
n.f.s.thrift.BinaryThrift.read           score:      18,87 us/op
n.f.s.thrift.BinaryThrift.write          score:      65,28 us/op
n.f.s.thrift.CompactThrift               ====> 359 bytes
n.f.s.thrift.CompactThrift.read          score:      60,60 us/op
n.f.s.thrift.CompactThrift.write         score:      52,20 us/op
n.f.s.thrift.JsonThrift                  ====> 640 bytes
n.f.s.thrift.JsonThrift.read             score:      50,43 us/op
n.f.s.thrift.JsonThrift.write            score:     283,83 us/op
```

For some reason, the DeflateStreams.write benchmark has gone missing.
The benchmark was run on a shared server with many users, so some variations
are probably present. The server had an average load of around 4 at the time
of the test, and with 64 CPUs, that should mean our test was not hampered.
Hopefully, the number of iterations will have averaged out the remaining
variations.

Some information about the server:

```
Architecture:          x86_64
CPU op-mode(s):        32-bit, 64-bit
Byte Order:            Little Endian
CPU(s):                64
On-line CPU(s) list:   0-63
Thread(s) per core:    2
Core(s) per socket:    8
Socket(s):             4
NUMA node(s):          4
Vendor ID:             GenuineIntel
CPU family:            6
Model:                 46
Stepping:              6
CPU MHz:               1861.805
BogoMIPS:              3723.48
Virtualization:        VT-x
L1d cache:             32K
L1i cache:             32K
L2 cache:              256K
L3 cache:              24576K
```


TODO
----

* Output a nicer report, including information about the machine used
* Automatically generate report on push to github, and update some website
