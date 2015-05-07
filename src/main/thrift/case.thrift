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

namespace java no.finntech.shootout.thrift

struct Application {
    1: required string id;
}

struct Place {
    1: required string id;
}

struct Person {
    1: required string id;
    3: optional string uniqueVisitorId;
    4: optional string sessionId;
    5: optional string userAgent;
    6: optional string clientDevice;
    7: optional string remoteAddr;
}

struct Offer {
    1: required string id;
    2: optional string name;
    3: optional string category;
    4: optional Person seller;
    5: optional Place availableAt;
    6: optional i32 price;
}

struct Link {
    1: required string href;
    2: optional string rel;
}

struct ThriftView {
    1: required string published;
    2: required Person actor;
    3: required Offer object;
    4: optional Application generator;
    5: optional Link attributedTo;
}
