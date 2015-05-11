package no.finntech.shootout.transit;

import java.util.Map;

import com.cognitect.transit.ReadHandler;
import com.cognitect.transit.impl.AbstractWriteHandler;

import static no.finntech.shootout.transit.Writers.map;
import static com.google.common.base.Preconditions.checkNotNull;

public final class Application {
    private final String id;

    public Application(String id) {
        this.id = checkNotNull(id);
    }

    public String getId() {
        return id;
    }

    static final class AppWriter extends AbstractWriteHandler<Application, Object> {
        @Override
        public String tag(Application o) {
            return "application";
        }

        @Override
        public Object rep(Application o) {
            return map("id", o.getId());
        }

    }

    static final class AppReader implements ReadHandler<Object, Map<String, String>> {

        @Override
        public Object fromRep(Map<String, String> map) {
            return new Application(map.get("id"));
        }
    }
}
