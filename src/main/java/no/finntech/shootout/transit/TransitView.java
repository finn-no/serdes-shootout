package no.finntech.shootout.transit;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.cognitect.transit.Link;
import com.cognitect.transit.ReadHandler;
import com.cognitect.transit.impl.AbstractWriteHandler;

import static java.util.Optional.ofNullable;

public final class TransitView {
    private static final String PUBLISHED = "published";
    private static final String ACTOR = "actor";
    private static final String OFFER = "offer";
    private static final String GENERATOR = "generator";
    private static final String ATTRIBUTED_TO = "attributedTo";

    private final Instant published;
    private final Person actor;
    private final Offer offer;
    private final Optional<Application> generator;
    private final Optional<Link> attributedTo;

    public TransitView(Instant published, Person actor, Offer offer, Optional<Application> generator, Optional<Link> attributedTo) {
        this.actor = actor;
        this.published = published;
        this.offer = offer;
        this.generator = generator;
        this.attributedTo = attributedTo;
    }

    public Instant getPublished() {
        return published;
    }

    public Person getActor() {
        return actor;
    }

    public Offer getOffer() {
        return offer;
    }

    public Optional<Application> getGenerator() {
        return generator;
    }

    public Optional<Link> getAttributedTo() {
        return attributedTo;
    }

    static final class TVWriter extends AbstractWriteHandler<TransitView, Object> {

        @Override
        public String tag(TransitView transitView) {
            return "case";
        }

        @Override
        public Object rep(TransitView tv) {
            final HashMap<Object, Object> m = Writers.map(
                    PUBLISHED, tv.getPublished(),
                    ACTOR, tv.getActor(),
                    OFFER, tv.getOffer()
            );
            tv.getGenerator().ifPresent(g -> m.put(GENERATOR, g));
            tv.getAttributedTo().ifPresent(a -> m.put(ATTRIBUTED_TO, a));
            return m;
        }
    }

    static final class TVReader implements ReadHandler<Object, Map<String, Object>> {

        @Override
        public Object fromRep(Map<String, Object> m) {
            return new TransitView(
                    required(m, PUBLISHED, Instant.class),
                    required(m, ACTOR, Person.class),
                    required(m, OFFER, Offer.class),
                    optional(m, GENERATOR, Application.class),
                    optional(m, ATTRIBUTED_TO, Link.class));
        }

        private static <T> Optional<T> optional(Map<String, Object> m, String key, Class<T> aClass) {
            return ofNullable(m.get(key)).map(aClass::cast);
        }

        private static <T> T required(Map<String, Object> m, String key, Class<T> aClass) {
            return optional(m, key, aClass).orElseThrow(() -> new IllegalArgumentException("Missing " + key));
        }
    }
}
