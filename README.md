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


TODO
----

* Output a nicer report, including information about the machine used
* Automatically generate report on push to github, and update some website
