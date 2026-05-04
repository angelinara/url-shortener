# Install dependencies
[group('setup')]
install:
    # @brew install java ant jdtls jq
    @rm -rf lib/
    @mkdir lib
    @cd lib/ && for path in \
        "org/junit/platform/junit-platform-console-standalone/1.10.1/junit-platform-console-standalone-1.10.1.jar" \
        "com/sparkjava/spark-core/2.9.4/spark-core-2.9.4.jar" \
        "org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar" \
        "org/slf4j/slf4j-simple/1.7.25/slf4j-simple-1.7.25.jar" \
        "javax/servlet/javax.servlet-api/3.1.0/javax.servlet-api-3.1.0.jar" \
        "org/eclipse/jetty/jetty-util/9.4.48.v20220622/jetty-util-9.4.48.v20220622.jar" \
        "org/eclipse/jetty/jetty-io/9.4.48.v20220622/jetty-io-9.4.48.v20220622.jar" \
        "org/eclipse/jetty/jetty-http/9.4.48.v20220622/jetty-http-9.4.48.v20220622.jar" \
        "org/eclipse/jetty/jetty-server/9.4.48.v20220622/jetty-server-9.4.48.v20220622.jar" \
        "org/eclipse/jetty/jetty-security/9.4.48.v20220622/jetty-security-9.4.48.v20220622.jar" \
        "org/eclipse/jetty/jetty-util-ajax/9.4.48.v20220622/jetty-util-ajax-9.4.48.v20220622.jar" \
        "org/eclipse/jetty/jetty-servlet/9.4.48.v20220622/jetty-servlet-9.4.48.v20220622.jar" \
        "org/eclipse/jetty/jetty-xml/9.4.48.v20220622/jetty-xml-9.4.48.v20220622.jar" \
        "org/eclipse/jetty/jetty-webapp/9.4.48.v20220622/jetty-webapp-9.4.48.v20220622.jar" \
        "org/eclipse/jetty/jetty-client/9.4.48.v20220622/jetty-client-9.4.48.v20220622.jar" \
        "org/eclipse/jetty/websocket/websocket-api/9.4.48.v20220622/websocket-api-9.4.48.v20220622.jar" \
        "org/eclipse/jetty/websocket/websocket-common/9.4.48.v20220622/websocket-common-9.4.48.v20220622.jar" \
        "org/eclipse/jetty/websocket/websocket-client/9.4.48.v20220622/websocket-client-9.4.48.v20220622.jar" \
        "org/eclipse/jetty/websocket/websocket-servlet/9.4.48.v20220622/websocket-servlet-9.4.48.v20220622.jar" \
        "org/eclipse/jetty/websocket/websocket-server/9.4.48.v20220622/websocket-server-9.4.48.v20220622.jar" \
        "com/google/code/gson/gson/2.13.1/gson-2.13.1.jar" \
        "org/tinylog/tinylog-api/2.7.0/tinylog-api-2.7.0.jar" \
        "org/tinylog/tinylog-impl/2.7.0/tinylog-impl-2.7.0.jar" \
        "org/sqids/sqids/0.1.0/sqids-0.1.0.jar" \
    ; do \
        curl --fail --remote-name "https://repo1.maven.org/maven2/$path"; \
    done

# Build project
[group('dev')]
build:
    @rm -rf build/
    @mkdir build
    @find src -type f -name "*.java" > sources
    @javac -d build/ -cp .:lib/* @sources -Xlint:unchecked
    @rm sources

[group('dev')]
build-test: build
    @find test -type f -name "*Test.java" > testsources
    @if [ -s testsources ]; then \
        javac -d build/ -cp .:lib/*:build/ @testsources -Xlint:unchecked; \
    fi
    @rm testsources

# Run all tests
[group('dev')]
test: build-test
    @java -ea -cp .:lib/*:build/ org.junit.platform.console.ConsoleLauncher execute \
        --scan-classpath

# Run the server
[group('dev')]
run: build
    @java -ea -cp .:lib/*:build/ urlshortener.Server

# Make requests to server
[group('dev')]
requests:
    #!/usr/bin/env bash
    PINK='\033[0;35m'
    NC='\033[0m'

    echo -e "${PINK}--- creating short url ---${NC}"
    shortUrl=$(curl -sS -X POST http://localhost:7070/url \
        -H "Content-Type: application/json" \
        -d '{"longUrl":"http://www.foo.com/"}' \
        | jq -r '.shortUrl')
    echo -e "${PINK}created short url: $shortUrl${NC}"
    echo

    echo -e "${PINK}--- fetching long url from short url $shortUrl ---${NC}"
    curl -sS -w "\n%{http_code}" http://localhost:7070/url/$shortUrl | jq
    echo
