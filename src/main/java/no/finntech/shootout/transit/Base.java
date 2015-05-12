package no.finntech.shootout.transit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.util.Map;
import java.util.OptionalInt;

import no.finntech.shootout.Case;
import no.finntech.shootout.Constants;
import no.finntech.shootout.Constants.Ad;
import no.finntech.shootout.Constants.AttributedTo;
import no.finntech.shootout.Constants.AvailableAt;
import no.finntech.shootout.Constants.Seller;
import no.finntech.shootout.Constants.Viewer;

import com.cognitect.transit.ReadHandler;
import com.cognitect.transit.TransitFactory;
import com.cognitect.transit.WriteHandler;
import com.cognitect.transit.impl.AbstractWriteHandler;
import com.google.common.collect.ImmutableMap;
import org.openjdk.jmh.annotations.Benchmark;

import static java.util.Optional.of;
import static no.finntech.shootout.transit.Writers.map;

public abstract class Base extends Case<TransitView> {
    private static final String INSTANT = "instant";
    private static final Map<Class, WriteHandler<?, ?>> WRITERS = ImmutableMap.<Class, WriteHandler<?, ?>>builder()
            .put(Application.class, new Application.AppWriter())
            .put(Offer.class, new Offer.OfferWriter())
            .put(Person.class, new Person.PersonWriter())
            .put(Place.class, new Place.PlaceWriter())
            .put(TransitView.class, new TransitView.TVWriter())
            .put(Instant.class, new InstantWriter())
            .build();
    private static final Map<String, ReadHandler<?, ?>> READERS = ImmutableMap.<String, ReadHandler<?, ?>>builder()
            .put("application", new Application.AppReader())
            .put("offer", new Offer.OfferReader())
            .put("person", new Person.PersonReader())
            .put("place", new Place.PlaceReader())
            .put("case", new TransitView.TVReader())
            .put(INSTANT, new InstantReader())
            .build();
    private static final TransitView VIEW = new TransitView(
            Instant.parse(Constants.PUBLISHED),
            new Person(Viewer.ID, of(Viewer.UNIQUE_ID), of(Viewer.SESSION_ID), of(Viewer.USER_AGENT), of(Viewer.CLIENT_DEVICE),
                    of(Viewer.REMOTE_ADDR)),
            new Offer(Ad.ID, of(Ad.NAME), of(Ad.CATEGORY), of(new Person(Seller.ID)), of(new Place(AvailableAt.ID)),
                    OptionalInt.of(Ad.PRICE)),
            of(new Application(Constants.Generator.ID)),
            of(TransitFactory.link(AttributedTo.HREF, AttributedTo.REL)));

    private final TransitFactory.Format format;

    protected Base(TransitFactory.Format format) {
        this.format = format;
    }

    @Override
    protected TransitView buildObject() {
        return VIEW;
    }

    @Override
    @Benchmark
    public ByteArrayOutputStream write() throws Exception {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        TransitFactory.writer(format, out, WRITERS).write(getObject());
        return out;
    }

    @Override
    @Benchmark
    public TransitView read() throws Exception {
        return TransitFactory.reader(format, new ByteArrayInputStream(getBytes()), READERS).read();
    }

    static final class InstantWriter extends AbstractWriteHandler<Instant, Object> {

        @Override
        public String tag(Instant o) {
            return INSTANT;
        }

        @Override
        public Object rep(Instant o) {
            return map("s", o.getEpochSecond(), "n", o.getNano());
        }
    }

    static final class InstantReader implements ReadHandler<Object, Map<String, Long>> {

        @Override
        public Object fromRep(Map<String, Long> m) {
            return Instant.ofEpochSecond(m.get("s"), m.get("n").intValue());
        }
    }
}
