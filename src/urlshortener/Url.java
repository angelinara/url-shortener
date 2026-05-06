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

    /**
     * Use base 62 is a way of generating 62 characters for encoding.
     * The mappings are: 0-0, ..., 9-9, 10-a, 11-b, ..., 35-z, 36-A, ..., 61-Z,
     * where 'a' stands for 10, 'Z' stands for 61, etc.
     */
    public void toShortUrl(long id) {
        if (id < Id.MIN_LEN) {
            throw new IllegalArgumentException("small id");
        }
        final String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            int remainder = (int) (id % 62);
            char c = chars.charAt(remainder);
            sb.append(c);
            id /= 62;
        }
        shortUrl = sb.toString();
    }

    @Override
    public String toString() {
        return "Url{" +
                "longUrl='" + longUrl + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                '}';
    }
}
