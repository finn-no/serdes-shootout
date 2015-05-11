package no.finntech.shootout.transit;

import java.util.Map;

import com.cognitect.transit.ReadHandler;
import com.cognitect.transit.impl.AbstractWriteHandler;

import static no.finntech.shootout.transit.Writers.map;
import static com.google.common.base.Preconditions.checkNotNull;

public final class Place {
    private final String id;

    public Place(String id) {
        this.id = checkNotNull(id);
    }

    public String getId() {
        return id;
    }

    static final class PlaceWriter extends AbstractWriteHandler<Place, Object> {
        @Override
        public String tag(Place o) {
            return "place";
        }

        @Override
        public Object rep(Place o) {
            return map("id", o.getId());
        }
    }

    static final class PlaceReader implements ReadHandler<Object, Map<String, String>> {

        @Override
        public Object fromRep(Map<String, String> map) {
            return new Place(map.get("id"));
        }
    }
}
