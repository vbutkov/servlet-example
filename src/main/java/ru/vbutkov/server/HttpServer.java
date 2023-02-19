package ru.vbutkov.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.*;

public class HttpServer {
    private final ExecutorService pool;
    private final int port;
    private boolean stopped;

    public HttpServer(int port, int poolSize) {
        this.pool = Executors.newFixedThreadPool(poolSize);
        this.port = port;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (!stopped) {
                Socket socket = serverSocket.accept();
                System.out.println("Socket accepted");
                pool.submit(() -> processSocket(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }

    }

    private void processSocket(Socket socket) {
        try (socket;
             DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {

            System.out.println("Request:" + new String(dataInputStream.readNBytes(400)));

            sleep(10000);

            byte[] body = Files.readAllBytes(Path.of("/home/melchior/IdeaProjects/HttpEx/src/main/resources/request.json"));

//            String headers = """
//                    HTTP/1.1 200 OK
//                    content-type: text/html
//                    content-length: 24
//                    """;
            //""".formatted("<h1> 404 not Found </h1>".length());

            String sBody = "<h1> 404 not Found </h1>";
            String headers2 = "HTTP/1.1 200 OK" + "\n" +
                              "content-type: text/html" + "\n" +
                              String.format("content-length: %s", sBody.length() + "\n").toString();


            //System.out.println(headers);
            System.out.println(headers2);

            dataOutputStream.write(headers2.getBytes());
            dataOutputStream.write(System.lineSeparator().getBytes());
            dataOutputStream.write(sBody.getBytes());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

}
