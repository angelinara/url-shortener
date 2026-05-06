package urlshortener;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class UrlTest {
    @Test
    void testMinLength() {
        String expected = "iB2";
        String actual = Url.toShortUrl(10_000L);
        assertEquals(expected, actual);
    }

    @Test
    void failWithShortId() {
        assertThrows(IllegalArgumentException.class, () -> Url.toShortUrl(9999L));
    }

    @Test
    void failEmptyLongUrl() {
        assertThrows(IllegalArgumentException.class, () -> new Url("", "u4UMC4x6Bm3"));
    }

    @Test
    void failEmptyShortUrl() {
        assertThrows(IllegalArgumentException.class, () -> new Url("www.foo.com", ""));
    }
}
