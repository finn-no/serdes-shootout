package no.finntech.shootout.transit;

import com.cognitect.transit.TransitFactory;

public class BinaryTransit extends Base {
    public BinaryTransit() {
        super(TransitFactory.Format.MSGPACK);
    }
}
