package ru.vbutkov;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class UrlExample {
    public static void main(String[] args) throws IOException {
        URL url = new URL("file:/home/melchior/IdeaProjects/HttpEx/pom.xml");
        URLConnection urlConnection = url.openConnection();

        System.out.println(new String(urlConnection.getInputStream().readAllBytes()));

    }

    private static void checkWebSite() throws IOException {
        URL url = new URL("https://www.google.com");
        URLConnection urlConnection = url.openConnection();
        urlConnection.setDoOutput(true);

        try (var outputStream = urlConnection.getOutputStream()) {
            //outputStream.write();
        }
    }

}