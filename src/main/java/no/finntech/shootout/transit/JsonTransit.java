package no.finntech.shootout.transit;

import com.cognitect.transit.TransitFactory;

public class JsonTransit extends Base {
    public JsonTransit() {
        super(TransitFactory.Format.JSON);
    }

    public static void main(String[] args) throws Exception {
        final Base transit = new JsonTransit();
        transit.prepare();
        System.out.println(transit.write().toString("UTF-8"));
        final TransitView read = transit.read();
        System.out.println(read);
    }
}
