package urlshortener;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.port;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.tinylog.Logger;

public class Server {
    public static void main(String[] args) {
        port(7070);
        var gson = new Gson();

        Map<String, String> shortToLongMap = new HashMap<>();

        // POST accepts one parameter: the long url
        post("/url", (req, res) -> {
            JsonObject payload = gson.fromJson(req.body(), JsonObject.class);
            Url u = new Url(payload.get("longUrl").getAsString());
            shortToLongMap.put(u.getShortUrl(), u.getLongUrl());
            Logger.info("Created short url {}", u.getShortUrl());
            res.type("application/json");
            return u;
        }, gson::toJson);

        // GET redirects short url to long url
        get("/url/:shortUrl", (req, res) -> {
            String shortUrl = req.params("shortUrl");
            res.type("application/json");
            if (shortToLongMap.containsKey(shortUrl)) {
                Logger.info("Fetch shortUrl={} found=true", shortUrl);
                return shortToLongMap.get(shortUrl);
            } else {
                Logger.warn("Fetch shortUrl={} found=false", shortUrl);
                res.status(404);
                return Map.of("error", "url not found");
            }
        }, gson::toJson);

    }

}
