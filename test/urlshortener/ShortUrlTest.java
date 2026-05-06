package urlshortener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ShortUrlTest {
    @Test
    void shortUrlNotEmpString() {
        Url url = new Url("www.foo.com");
        url.toShortUrl(10_000L);
        String expected = "Url{longUrl='www.foo.com', shortUrl='iB2'}";
        String actual = url.toString();
        assertEquals(expected, actual);
    }

    @Test
    void failWithShortId() {
        Url url = new Url("www.foo.com");
        assertThrows(IllegalArgumentException.class, () -> url.toShortUrl(9999L));
    }
}
