package io.github.mickey.bio;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HTTPServer02 {

    private static final int port = Integer.parseInt(System.getProperty("port", "8001"));

    public static void main(String[] args) throws Exception{

        ServerSocket ss = new ServerSocket(port);
        while (true){
            Socket socket= ss.accept();
            new Thread(() -> {
                process(socket);
            }).start();
        }
    }

    private static void process(Socket socket) {
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println("HTTP/1.1 200 OK");
            pw.println("Content-Type:text/html;charset=utf8");
            String body = "hello,nio";
            pw.println("Content-Length:" + body.getBytes(StandardCharsets.UTF_8).length);
            pw.println();
            pw.write(body);
            pw.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
