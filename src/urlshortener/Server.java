package urlshortener;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.Map;

import org.tinylog.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Server {
    public static void main(String[] args) {
        port(7070);
        var gson = new Gson();

        Map<String, Long> longToIdMap = new HashMap<>();
        Map<String, String> shortToLongMap = new HashMap<>();

        // POST accepts one parameter: the long url
        post("/url", (req, res) -> {
            JsonObject payload = gson.fromJson(req.body(), JsonObject.class);
            String longUrl = payload.get("longUrl").getAsString();

            boolean created = false;

            // ensure long url is mapped to a unique id
            if (!longToIdMap.containsKey(longUrl)) {
                longToIdMap.put(longUrl, Id.getRandom());
                created = true;
            }

            // convert id to short url
            // id generation is non-deterministic and unrelated to long urls provided by
            // clients so mapping from long url to id is required to connect long url to
            // short url
            long id = longToIdMap.get(longUrl);
            String shortUrl = Url.toShortUrl(id);

            Url u = new Url(longUrl, shortUrl);

            // map short url to long url
            // to return long url when client provides short url
            shortToLongMap.put(u.getShortUrl(), u.getLongUrl());

            if (created) {
                Logger.info("Created short url {}", u.getShortUrl());
            } else {
                Logger.info("Reused short url {}", u.getShortUrl());
            }
            res.type("application/json");
            return u;
        }, gson::toJson);

        // GET returns long url from the short url
        // TODO: redirect to long url instead of returning it
        get("/url/:shortUrl", (req, res) -> {
            String shortUrl = req.params("shortUrl");
            res.type("application/json");
            if (shortToLongMap.containsKey(shortUrl)) {
                Logger.info("Fetch shortUrl={} found=true", shortUrl);
                String longUrl = shortToLongMap.get(shortUrl);
                Url u = new Url(longUrl, shortUrl);
                return u;
            } else {
                Logger.warn("Fetch shortUrl={} found=false", shortUrl);
                res.status(404);
                return Map.of("error", "url not found");
            }
        }, gson::toJson);
    }
}
