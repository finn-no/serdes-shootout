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

package no.finntech.shootout;

public final class Constants {
    public static final String PUBLISHED = "2015-02-10T15:04:55Z";

    public interface Viewer {
        String ID = "123456789";
        String UNIQUE = "3423414";
        String UNIQUE_ID = "353sdf3fdcs23c";
        String SESSION_ID = "AAFEEDE3122FF";
        String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90";
        String CLIENT_DEVICE = "Samsung Galaxy S5";
        String REMOTE_ADDR = "89.123.121.90";
    }

    public interface Ad {
        String ID = "58939768";
        String NAME = "Volkswagen Touran 2010, 97 455 km, kr 96 200,-";
        String CATEGORY = "bil/volkswagen/touran";
        int PRICE = 96200;
    }

    public interface Seller {
        String ID = "2345567";
    }

    public interface AvailableAt {
        String ID = "2003";
    }

    public interface Generator {
        String ID = "finn";
    }

    public interface AttributedTo {
        String HREF = "http://www.finn.no/finn/car/used/result";
        String REL = "referer";
    }
}
