package no.finntech.shootout.transit;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

import com.cognitect.transit.ReadHandler;
import com.cognitect.transit.impl.AbstractWriteHandler;
import com.google.common.collect.Maps;

import static java.util.Optional.ofNullable;

public final class Offer {
    private final String id;
    private final Optional<String> name;
    private final Optional<String> category;
    private final Optional<Person> seller;
    private final Optional<Place> availableAt;
    private final OptionalInt price;

    public Offer(String id, Optional<String> name, Optional<String> category, Optional<Person> seller, Optional<Place> availableAt,
                 OptionalInt price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.seller = seller;
        this.availableAt = availableAt;
        this.price = price;
    }


    static final class OfferWriter extends AbstractWriteHandler<Offer, Object> {

        @Override
        public String tag(Offer Offer) {
            return "offer";
        }

        @Override
        public Object rep(Offer o) {
            final HashMap<String, Object> m = Maps.newHashMapWithExpectedSize(6);
            m.put("id", o.id);
            o.name.ifPresent(n -> m.put("name", n));
            o.category.ifPresent(c -> m.put("category", c));
            o.seller.ifPresent(s -> m.put("seller", s));
            o.availableAt.ifPresent(aa -> m.put("availableAt", aa));
            o.price.ifPresent(p -> m.put("price", p));
            return m;
        }
    }

    static final class OfferReader implements ReadHandler<Object, Map<String, Object>> {

        @Override
        public Object fromRep(Map<String, Object> m) {
            return new Offer(
                    required(m, "id", String.class),
                    optional(m, "name", String.class),
                    optional(m, "category", String.class),
                    optional(m, "seller", Person.class),
                    optional(m, "availableAt", Place.class),
                    optional(m, "price", Long.class).map(l -> OptionalInt.of(l.intValue())).orElse(OptionalInt.empty()));
        }

        private static <T> Optional<T> optional(Map<String, Object> m, String key, Class<T> aClass) {
            return ofNullable(m.get(key)).map(aClass::cast);
        }

        private static <T> T required(Map<String, Object> m, String key, Class<T> aClass) {
            return optional(m, key, aClass).orElseThrow(() -> new IllegalArgumentException("Missing key: " + key));
        }
    }
}
