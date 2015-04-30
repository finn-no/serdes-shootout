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

package no.finntech.shootout.thrift;

import java.io.IOException;

import no.finntech.shootout.AbstractCase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;

public final class Thrift extends AbstractCase {
    private static final ThriftPost POST = new ThriftPost()
            .setPublished(PUBLISHED)
            .setActor(new Person()
                .setId(PERSON_ID)
                .setDisplayName(PERSON_NAME))
            .setObject(new Article()
                .setId(ARTICLE_ID)
                .setDisplayName(ARTICLE_NAME));
    private static final Logger LOG = LogManager.getLogger(Thrift.class);

    @Override
    public String getName() {
        return "Thrift";
    }

    @Override
    public void write() {
        try {
            byte[] bytes = new TSerializer().serialize(POST);
            getOut().write(bytes);
        } catch (TException | IOException e) {
            LOG.error(e);
        }
    }

    @Override
    public void read() {
        try {
            new TDeserializer().deserialize(new ThriftPost(), getOut().toByteArray());
        } catch (TException e) {
            LOG.error(e);
        }
    }
}
