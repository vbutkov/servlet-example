package ru.vbutkov.server;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class HttpClientRunner {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest httpRequest = null;
        try {
            httpRequest = HttpRequest.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .uri(URI.create("http://localhost:9000"))
                    .POST(HttpRequest.BodyPublishers.ofFile(Path.of("/home/melchior/IdeaProjects/HttpEx/src/main/resources/request.xml")))
                    .header("content-type", "application/xml")
                    .header("connection", "close")
                    .build();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
        CompletableFuture<HttpResponse<String>> response2 = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
        CompletableFuture<HttpResponse<String>> response3 = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

        //System.out.println(response.headers());
        //System.out.println(response.body());

        System.out.println(response3.get().body());

    }
}
