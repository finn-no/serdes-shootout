package no.finntech.shootout.transit;

import java.util.HashMap;

public final class Writers {
    public static HashMap<Object, Object> map(Object k, Object v) {
        final HashMap<Object, Object> m = new HashMap<>(2);
        m.put(k, v);
        return m;
    }

    public static HashMap<Object, Object> map(Object k1, Object v1, Object k2, Object v2) {
        final HashMap<Object, Object> m = new HashMap<>(4);
        m.put(k1, v1);
        m.put(k2, v2);
        return m;
    }

    public static HashMap<Object, Object> map(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3) {
        final HashMap<Object, Object> m = new HashMap<>(8);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        return m;
    }
}
