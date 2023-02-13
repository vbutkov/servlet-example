package ru.vbutkov;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class HttpClientExample {
    public static void main(String[] args) throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder(URI.create("https://google.com"))
                .GET()
                .build();

        HttpRequest requestPOST = HttpRequest.newBuilder(URI.create("https://google.com"))
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("/home/melchior/IdeaProjects/HttpEx/pom.xml")))
                .build();

        HttpResponse<String> responce = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        System.out.println(responce.body());
        System.out.println(responce.headers());


    }
}
