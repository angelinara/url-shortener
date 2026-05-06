package urlshortener;
import java.util.concurrent.ThreadLocalRandom;

public class Id {
    public static final long MIN_LEN = 10_000L;
    
    // thread-safe
    static long getRandom() {
        // minimum 10000 to avoid small values
        // because they will be converted to excessively small urls
        return ThreadLocalRandom.current().nextLong(MIN_LEN, Long.MAX_VALUE);
    }
}
