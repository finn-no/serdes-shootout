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

public interface Case {
    String PUBLISHED = "2015-02-10T15:04:55Z";
    String PERSON_ID = "urn:example:person:martin";
    String PERSON_NAME = "Martin Smith";
    String ARTICLE_ID = "urn:example:blog:abc123/xyz";
    String ARTICLE_NAME = "Why I'm testing serialization performance";

    void init();
    String getName();
    void write();
    int getSize();
    void read();
}
