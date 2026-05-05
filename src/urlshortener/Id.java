package urlshortener;
import java.util.concurrent.ThreadLocalRandom;

public class Id {
    // thread-safe
    static long getRandom() {
        // minimum 1000 to avoid small values
        // because they will be converted to excessively small urls
        return ThreadLocalRandom.current().nextLong(1000L, Long.MAX_VALUE);
    }
}
