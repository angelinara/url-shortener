#!/bin/bash
# Get long URL from a short URL
SHORT_URL="${1:-abc123}"
curl -s \
  -X GET \
  "http://localhost:7070/url/$SHORT_URL" | jq .
