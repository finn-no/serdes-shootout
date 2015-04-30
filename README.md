Serdes Shootout
===============

This project attempts to compare speed and size when using different forms of
serialization in Java. A common object is serialized and deserialized using
a number of serialization frameworks/techniques, and the time spent and size
of serialized output is compared in a simple table when completed.


Setup for development
---------------------

Most things should already be present, except:

* You will need the thrift compiler on your `PATH`, with the name `thrift-0.9.1`.


Adding a framework to test
--------------------------

Implement one or more `Case`s, and add them to the `cases` list in `Main`.
When you are satisfied with your implementation, send a pull-request to get
it added to the main.


TODO
----

* Maven setup to run the test
* Output a nicer report, including information about the machine used
* Automatically generate report on push to github, and update some website
