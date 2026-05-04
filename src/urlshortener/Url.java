package urlshortener;

public class Url {
    private String longUrl;
    private String shortUrl;

    public Url(String longUrl) {
        this.longUrl = longUrl;
        if (longUrl.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.shortUrl = "abc123"; // FIXME: remove hardcoded url
    }

    public String getLongUrl() {
        return longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    @Override
    public String toString() {
        return "Url{" +
                "longUrl:" + longUrl +
                "shortUrl:" + shortUrl +
                '}';
    }
}
