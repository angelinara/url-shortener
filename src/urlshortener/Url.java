package urlshortener;

public class Url {
    private final String longUrl;

    private String shortUrl;

    public Url(String longUrl) {
        this.longUrl = longUrl;
        if (longUrl.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public String getLongUrl() {
        return longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void toShortUrl(long id) {
        // TODO: remove hardcoded id, implement id to short url conversion
        shortUrl = "T1W";
    }

    @Override
    public String toString() {
        return "Url{" +
                "longUrl:" + longUrl +
                "shortUrl:" + shortUrl +
                '}';
    }
}
