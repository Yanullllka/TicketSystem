package monitoring;

import com.sun.net.httpserver.HttpServer;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class MetricsServer {

    public static void startServer() throws IOException {

        PrometheusMeterRegistry registry = MetricsConfig.getRegistry();

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8081),
                0
        );

        server.createContext("/metrics", exchange -> {

            String response = registry.scrape();

            byte[] responseBytes =
                    response.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set(
                    "Content-Type",
                    "text/plain; version=0.0.4; charset=utf-8"
            );

            exchange.sendResponseHeaders(200, responseBytes.length);

            try (OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(responseBytes);
            }
        });

        server.setExecutor(null);
        server.start();

        System.out.println(
                "Сервер метрик запущен: http://localhost:8081/metrics"
        );
    }

    public static void main(String[] args) throws IOException {
        startServer();
    }
}