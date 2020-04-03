import net.webservicex.GeoIPService;
import org.testng.annotations.Test;

public class GeoIpServiceTests {
    @Test
    public void testMyIp() {
        GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("97.103.181.121");
        assertEquals(geoIP.getCountryCode(), "US");
    }
}
