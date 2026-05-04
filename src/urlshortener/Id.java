package urlshortener;
import java.util.concurrent.ThreadLocalRandom;

public class Id {
    // thread-safe
    static long getRandom() {
        return ThreadLocalRandom.current().nextLong(1L, Long.MAX_VALUE);
    }
}
