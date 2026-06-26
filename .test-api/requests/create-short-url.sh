#!/bin/bash
# Create a short URL from a long URL
curl -s \
  -X POST \
  -H "Content-Type: application/json" \
  -d '{"longUrl": "https://example.com/some/very/long/path"}' \
  "http://localhost:7070/url" | jq .
