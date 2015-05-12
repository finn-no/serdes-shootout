package no.finntech.shootout.transit;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.cognitect.transit.ReadHandler;
import com.cognitect.transit.impl.AbstractWriteHandler;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static com.google.common.base.Preconditions.checkNotNull;

public final class Person {
    private static final String ID = "id";
    private static final String UNIQUE_VISITOR_ID = "uniqueVisitorId";
    private static final String SESSION_ID = "sessionId";
    private static final String USER_AGENT = "userAgent";
    private static final String CLIENT_DEVICE = "clientDevice";
    private static final String REMOTE_ADDR = "remoteAddr";

    private final String id;
    private final Optional<String> uniqueVisitorId;
    private final Optional<String> sessionId;
    private final Optional<String> userAgent;
    private final Optional<String> clientDevice;
    private final Optional<String> remoteAddress;

    public Person(String id) {
        this(id, empty(), empty(), empty(), empty(), empty());
    }

    public Person(String id, Optional<String> uniqueVisitorId, Optional<String> sessionId, Optional<String> userAgent,
                  Optional<String> clientDevice, Optional<String> remoteAddress) {
        this.id = checkNotNull(id);
        this.uniqueVisitorId = uniqueVisitorId;
        this.sessionId = sessionId;
        this.userAgent = userAgent;
        this.clientDevice = clientDevice;
        this.remoteAddress = remoteAddress;
    }


    public String getId() {
        return id;
    }

    public Optional<String> getUniqueVisitorId() {
        return uniqueVisitorId;
    }

    public Optional<String> getSessionId() {
        return sessionId;
    }

    public Optional<String> getUserAgent() {
        return userAgent;
    }

    public Optional<String> getClientDevice() {
        return clientDevice;
    }

    public Optional<String> getRemoteAddr() {
        return remoteAddress;
    }

    static final class PersonWriter extends AbstractWriteHandler<Person, Object> {

        @Override
        public String tag(Person p) {
            return "person";
        }

        @Override
        public Object rep(Person p) {
            final HashMap<String, String> m = new HashMap<>(8);
            m.put(ID, p.getId());
            putIfPresent(m, p.getUniqueVisitorId(), UNIQUE_VISITOR_ID);
            putIfPresent(m, p.getSessionId(), SESSION_ID);
            putIfPresent(m, p.getUserAgent(), USER_AGENT);
            putIfPresent(m, p.getClientDevice(), CLIENT_DEVICE);
            putIfPresent(m, p.getRemoteAddr(), REMOTE_ADDR);
            return m;
        }

        private void putIfPresent(final HashMap<String, String> m, Optional<String> opt, String key) {
            opt.ifPresent(i -> m.put(key, i));
        }
    }

    static final class PersonReader implements ReadHandler<Object, Map<String, String>> {

        @Override
        public Object fromRep(Map<String, String> map) {
            return new Person(
                    map.get(ID),
                    ofNullable(map.get(UNIQUE_VISITOR_ID)),
                    ofNullable(map.get(SESSION_ID)),
                    ofNullable(map.get(USER_AGENT)),
                    ofNullable(map.get(CLIENT_DEVICE)),
                    ofNullable(map.get(REMOTE_ADDR))
            );
        }
    }
}
